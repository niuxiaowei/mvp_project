package com.niu.myapp.myapp.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.niu.myapp.myapp.ui.dialog.BaseDialogFragment;
import com.niu.myapp.myapp.ui.dialog.DialogFactory;


public abstract class BaseActivity extends FragmentActivity implements BaseDialogFragment.DialogListenerAccessor {

    protected DialogFactory mDialogFactory;

    @Override
    public BaseDialogFragment.BaseDialogListener getDialogListener(){
        return mDialogFactory.mListenerHolder.getDialogListener();
    }

    /**
     * 清空DialogListenerHolder中的dialog listener
     */
    @Override
    public void clearDialogListener(){
        mDialogFactory.mListenerHolder.setDialogListener(null);
    }


    /**
     * 为fragment设置functions，具体实现子类来做
     * @param fragmentId
     */
    public void setFunctionsForFragment(int fragmentId){}


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDialogFactory.mListenerHolder.saveDialogListenerKey(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogFactory = new DialogFactory(getSupportFragmentManager(),savedInstanceState);
        mDialogFactory.restoreDialogListener(this);
    }






}
