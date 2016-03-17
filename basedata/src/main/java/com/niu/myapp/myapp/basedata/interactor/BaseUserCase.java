package com.niu.myapp.myapp.basedata.interactor;

import android.support.annotation.NonNull;

import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 所有UserCase的基础类，封装一些公用方法
 * Created by niuxiaowei on 2016/3/16.
 */
public abstract class BaseUserCase {

    protected UIThreadExecutor mUIThreadExecutor;
    protected NormalThreadExecutor mNormalThreadExecutor;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BaseUserCase(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor){
        this.mUIThreadExecutor = uiThreadExecutor;
        this.mNormalThreadExecutor = normalThreadExecutor;
    }

    @NonNull
    protected void execute(Observable observable, Subscriber subscriber){
        if(observable != null && subscriber != null){

            Subscription sn = observable.subscribeOn(Schedulers.from(mNormalThreadExecutor)).observeOn(Schedulers.from(mUIThreadExecutor)).subscribe
                    (subscriber);
            compositeSubscription.add(sn);
        }
    }

    public void unsubscribe(){
        if(!compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
        }
    }

}
