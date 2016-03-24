package com.niu.myapp.myapp.base;

import android.app.Application;

import com.niu.myapp.myapp.base.di.components.BaseApplicationComponent;

/**
 * 基础的application，包含依赖注入的ApplicationComponent
 * Created by niuxiaowei on 16/3/24.
 */
public abstract class BaseApp<C> extends Application {

    public abstract C getApplicationComponent();
}
