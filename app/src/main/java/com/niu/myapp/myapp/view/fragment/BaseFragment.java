package com.niu.myapp.myapp.view.fragment;
import android.app.Activity;

import com.niu.myapp.myapp.view.activity.BaseActivity;


/**

 */
public abstract class BaseFragment extends com.niu.myapp.myapp.base.BaseFragment {

    protected BaseActivity mBaseActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity)activity;
        }

    }

}
