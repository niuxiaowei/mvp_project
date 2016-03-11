package com.niu.myapp.myapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.rxbus.RXBusManager;
import com.niu.myapp.myapp.common.rxbus.RxBus;
import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.view.activity.BaseActivity;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by niuxiaowei on 2016/3/3.
 */
public class RXBusExampleFragment extends BaseFragment{

    public static class TapEvent {}
    public static class TapEvent1 {}
    @Override
    protected void onAddPresenters() {

    }

    @Override
    protected void onInitPresenters() {

    }


    @Override
    protected void onInjectFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxbus,container,false);
        view.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RXBusManager.getInstance().send(TapEvent.class.getName(),new TapEvent());
            }
        });
        view.findViewById(R.id.click1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RXBusManager.getInstance().send(TapEvent1.class.getName(),new TapEvent1());
            }
        });
        initExa();
        return view;
    }

    private void initExa(){
        //基本的
        RXBusManager.getInstance().toObserverable(TapEvent.class.getName())//
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
//                        try {
//                            Thread.sleep(10000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        DLog.i("test","---1--subscriber--event="+event+" TapEvent类型");
                    }
                });


        Observable<Object> tapEventEmitter = RXBusManager.getInstance().toObserverable(TapEvent.class.getName()).share();

       tapEventEmitter.subscribe(new Action1<Object>() {
           @Override
           public void call(Object event) {
               DLog.i("test", "---2--subscriber--event="+event);
           }
       });


        //
        Observable<Object> debouncedEmitter = tapEventEmitter.debounce(10, TimeUnit.SECONDS);
        Observable<List<Object>> debouncedBufferEmitter = tapEventEmitter.buffer(debouncedEmitter);

        debouncedBufferEmitter//
                        .observeOn(Schedulers.from((mBaseActivity.getApplicationComponent().getUIThreadExecutor())))//
                        .subscribe(new Action1<List<Object>>() {
                            @Override
                            public void call(List<Object> taps) {
                                DLog.i("test", "---3--subscriber--size=" + taps.size() + " " + taps);
                            }
                        });


        //4
        CompositeSubscription _subscriptions = new CompositeSubscription();

        ConnectableObservable<Object> tapEventEmitter1 = RXBusManager.getInstance().toObserverable(TapEvent1.class.getName()).publish();

        _subscriptions//
                .add(tapEventEmitter1.subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                            DLog.i("test", "---4--subscriber--event="+event);
                    }
                }));

        //5
        _subscriptions//
                .add(tapEventEmitter1.publish(new Func1<Observable<Object>, Observable<List<Object>>>() {
                    @Override
                    public Observable<List<Object>> call(Observable<Object> stream) {
                        return stream.buffer(stream.debounce(20, TimeUnit.SECONDS));
                    }
                }).observeOn(Schedulers.from(mBaseActivity.getApplicationComponent().getUIThreadExecutor())).subscribe(new Action1<List<Object>>() {
                    @Override
                    public void call(List<Object> taps) {
                        DLog.i("test", "---5--subscriber--size="+taps.size()+" "+taps);
                    }
                }));

        _subscriptions.add(tapEventEmitter1.connect());
    }
}
