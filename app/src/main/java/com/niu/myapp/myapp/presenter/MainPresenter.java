package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.compnent.IMainView;
import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public class MainPresenter  extends BasePresenter implements Presenter {

    @Inject
    public MainPresenter(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor) {
        super(uiThreadExecutor,normalThreadExecutor);
    }

    private IMainView mMainView;

    public void initView(IMainView mainView) {
        this.mMainView = mainView;
        mMainView.initView();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestory() {

    }




}
