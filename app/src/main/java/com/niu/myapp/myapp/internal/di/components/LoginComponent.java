package com.niu.myapp.myapp.internal.di.components;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.internal.di.modules.ActivityModule;
import com.niu.myapp.myapp.internal.di.modules.LoginModule;
import com.niu.myapp.myapp.internal.di.modules.PresentersModule;
import com.niu.myapp.myapp.presenter.LoginPresenter;
import com.niu.myapp.myapp.view.fragment.MainFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 2015/11/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(MainFragment fragment);
    LoginPresenter getLoginPresenter();
}