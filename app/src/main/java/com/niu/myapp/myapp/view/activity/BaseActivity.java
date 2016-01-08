package com.niu.myapp.myapp.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.niu.myapp.myapp.BaseApplication;
import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.HasComponent;
import com.niu.myapp.myapp.internal.di.components.ApplicationComponent;
import com.niu.myapp.myapp.internal.di.modules.ActivityModule;
import com.niu.myapp.myapp.view.Navigator;
import com.niu.myapp.myapp.view.compnent.IView;
import com.niu.myapp.myapp.view.widget.ConfirmDialogFragment;
import com.niu.myapp.myapp.view.widget.ProgressDialogFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public abstract class BaseActivity extends FragmentActivity  {


    @Inject
    Navigator mNavigator;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.desotry();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return ((BaseApplication)getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule}
     */
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


}
