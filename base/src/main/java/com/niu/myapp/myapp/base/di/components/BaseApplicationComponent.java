package com.niu.myapp.myapp.base.di.components;

import android.content.Context;

import com.niu.myapp.myapp.base.di.modules.BaseApplicationModule;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;


import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = BaseApplicationModule.class)
public interface BaseApplicationComponent {

    //以下暴漏的方法主要是给子component爆漏的
    Context context();


    UIThreadExecutor getUIThreadExecutor();
    NormalThreadExecutor getNormalThreadExecutor();

}
