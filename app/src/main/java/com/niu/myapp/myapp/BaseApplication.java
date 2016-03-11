package com.niu.myapp.myapp;

import android.app.Application;

import com.niu.myapp.myapp.common.http.image.ImageLoaderProxy;
import com.niu.myapp.myapp.internal.di.components.ApplicationComponent;
import com.niu.myapp.myapp.internal.di.components.DaggerApplicationComponent;
import com.niu.myapp.myapp.internal.di.modules.ApplicationModule;
import com.niu.myapp.myapp.data.datastore.FriendDatastoreFactory;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/10/13.
 */
public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;
    @Inject
    FriendDatastoreFactory mfriend;

    @Override
    public void onCreate() {
        super.onCreate();

        Envi.appContext = this;
        //初始化圖片加載器
        ImageLoaderProxy.getInstance().init(this);
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
