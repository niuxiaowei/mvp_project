package com.niu.myapp.myapp;

import android.app.Application;


import com.niu.myapp.myapp.base.BaseApp;
import com.niu.myapp.myapp.base.di.modules.BaseApplicationModule;
import com.niu.myapp.myapp.common.image.ImageLoaderProxy;
import com.niu.myapp.myapp.internal.di.components.ApplicationComponent;
import com.niu.myapp.myapp.internal.di.components.DaggerApplicationComponent;
import com.niu.myapp.myapp.internal.di.modules.ApplicationModule;
import com.niu.myapp.myapp.data.datastore.FriendDatastoreFactory;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/10/13.
 */
public class BaseApplication extends BaseApp<ApplicationComponent> {

    private ApplicationComponent applicationComponent;
    @Inject
    FriendDatastoreFactory mfriend;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化圖片加載器
        ImageLoaderProxy.getInstance().init(this);
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).baseApplicationModule(new BaseApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
