package com.niu.myapp.myapp.internal.di.components;

import com.niu.myapp.myapp.base.di.PerActivity;
import com.niu.myapp.myapp.view.fragment.FriendFragment;

import dagger.Component;

/**
 * 该类定义注入只拥有公共依赖的Fragment
 * Created by niuxiaowei on 2016/3/11.
 */
//没有下面的范围注释，会报Error:(11, 1) 错误: com.niu.myapp.myapp.internal.di.components.FragmentInjectComponent (unscoped) cannot depend on scoped components:
//@Singleton com.niu.myapp.myapp.internal.di.components.ApplicationComponent错误
@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface FragmentInjectComponent {
    void injectFriendsFragment(FriendFragment fragment);
}
