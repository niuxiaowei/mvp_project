package com.niu.myapp.myapp.internal.di.components;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.LoginModule;
import com.niu.myapp.myapp.internal.di.modules.MainModule;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.presenter.LoginPresenter;
import com.niu.myapp.myapp.presenter.MainPresenter;
import com.niu.myapp.myapp.view.fragment.MainFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 2015/11/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainModule.class,LoginModule.class, FriendsModule.class})
public interface MainComponent {
    void inject(MainFragment fragment);

    MainPresenter getMainPresenter();
    LoginPresenter getLoginPresenter();
    FriendListPresenter getFriendListPresenter();
}
