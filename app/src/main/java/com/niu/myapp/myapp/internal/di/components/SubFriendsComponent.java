package com.niu.myapp.myapp.internal.di.components;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.SubFriendsModule;
import com.niu.myapp.myapp.view.fragment.FriendFragment;

import dagger.Subcomponent;

/**
 * 一个subComponent例子
 * Created by niuxiaowei on 2016/3/11.
 */
@PerActivity
@Subcomponent( modules = {SubFriendsModule.class})
public interface SubFriendsComponent {
    void injectFragment(FriendFragment fragment);
}
