package com.niu.myapp.myapp.internal.di.components;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.H5Module;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.presenter.H5Presenter;
import com.niu.myapp.myapp.view.fragment.FriendFragment;
import com.niu.myapp.myapp.view.fragment.H5Fragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 2015/11/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {H5Module.class})
public interface H5Component {
    void inject(H5Fragment fragment);
    H5Presenter getH5Presenter();
}
