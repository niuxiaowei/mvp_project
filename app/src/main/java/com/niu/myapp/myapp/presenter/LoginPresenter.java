package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.base.present.BasePresenter;
import com.niu.myapp.myapp.base.present.Presenter;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.view.compnent.ILoginView;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/10/13.
 */
public class LoginPresenter  extends BasePresenter implements Presenter {

    private ILoginView mLoginView;

    @Inject
    public LoginPresenter(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor){
        super(uiThreadExecutor,normalThreadExecutor);
    }

    public void initView(ILoginView loginView) {
        this.mLoginView = loginView;
        mLoginView.initView();
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



    public void login(String userName, String password){
        //模拟网络请求
        if(mLoginView != null){
            mLoginView.showLoginingView();
        }

        executTaskOnNormalThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    executTaskOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mLoginView != null) {
                                mLoginView.loginSuccess();
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
