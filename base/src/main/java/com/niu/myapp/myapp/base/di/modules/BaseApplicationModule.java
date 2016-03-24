package com.niu.myapp.myapp.base.di.modules;

import android.content.Context;

import com.niu.myapp.myapp.base.BaseApp;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class BaseApplicationModule {
    private final BaseApp application;

    public BaseApplicationModule(BaseApp application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return this.application;
    }


    @Provides @Singleton
    NormalThreadExecutor provideNormalThreadExecutor(){
        return new NormalThreadExecutor();
    }

    @Provides @Singleton
    UIThreadExecutor provideUIThreadExecutor(){
        return new UIThreadExecutor();
    }


}
