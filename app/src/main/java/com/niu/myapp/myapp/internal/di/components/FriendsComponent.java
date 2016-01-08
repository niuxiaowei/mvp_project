package com.niu.myapp.myapp.internal.di.components;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.LoginModule;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.view.fragment.MainFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 2015/11/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {FriendsModule.class})
public interface FriendsComponent {
    void inject(MainFragment fragment);
    FriendListPresenter getFriendListPresenter();
}
