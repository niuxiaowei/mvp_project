package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.compnent.IMainView;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public class MainPresenter  extends BasePresenter implements Presenter {

    public MainPresenter(IMainView mainView) {
        super();
        this.mMainView = mainView;
    }

    private IMainView mMainView;

    @Override
    public void initView() {
        if(mMainView != null){
            mMainView.initView();
        }
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
