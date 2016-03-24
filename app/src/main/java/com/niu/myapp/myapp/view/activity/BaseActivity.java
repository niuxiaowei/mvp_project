package com.niu.myapp.myapp.view.activity;

import android.os.Bundle;

import com.niu.myapp.myapp.BaseApplication;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.components.ApplicationComponent;
import com.niu.myapp.myapp.internal.di.modules.ActivityModule;
import com.niu.myapp.myapp.view.Navigator;


import javax.inject.Inject;

public abstract class BaseActivity extends com.niu.myapp.myapp.base.BaseActivity <ApplicationComponent>{


    @Inject
    Navigator mNavigator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.desotry();

    }


    /**
     * Get the Main Application component for dependency injection.
     *
     */
    public ApplicationComponent getApplicationComponent() {
        return ((BaseApplication)getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     */
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


}
