package com.niu.myapp.myapp.internal.di.modules;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.presenter.LoginPresenter;
import com.niu.myapp.myapp.view.compnent.ILoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
@Module
public class LoginModule {

    private ILoginView mLoginView;

    public LoginModule(ILoginView loginView) {
        super();
        mLoginView = loginView;
    }

    @Provides @PerActivity
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(mLoginView);
    }
}
