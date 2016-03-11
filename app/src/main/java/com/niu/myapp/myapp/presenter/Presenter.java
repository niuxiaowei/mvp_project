package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.compnent.IView;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public interface Presenter {


    public void onStop();

    public void onResume();

    public void onDestory();
}
