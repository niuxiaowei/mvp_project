package com.niu.myapp.myapp.view.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.niu.myapp.myapp.BaseApplication;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.components.ApplicationComponent;
import com.niu.myapp.myapp.internal.di.modules.ActivityModule;
import com.niu.myapp.myapp.view.Navigator;
import com.niu.myapp.myapp.view.widget.BaseDialogFragment;
import com.niu.myapp.myapp.view.widget.DialogFactory;

import javax.inject.Inject;

public abstract class BaseActivity extends FragmentActivity  {


    @Inject
    Navigator mNavigator;

    protected DialogFactory mDialogFactory;


    /**
     * 为fragment设置functions，具体实现子类来做
     * @param fragmentId
     */
    public void setFunctionsForFragment(int fragmentId){}

    public BaseDialogFragment.BaseDialogListener getDialogListener(){
        return mDialogFactory.mListenerHolder.getDialogListener();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDialogFactory.mListenerHolder.saveDialogListenerKey(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        mDialogFactory = new DialogFactory(getSupportFragmentManager(),savedInstanceState);
        mDialogFactory.restoreDialogListener(this);
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
