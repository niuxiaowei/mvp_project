package com.niu.myapp.myapp.view;

import android.content.Context;

import com.niu.myapp.myapp.view.activity.H5Activity;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public class Navigator {

    public Navigator() {
        super();
    }

    public   void toH5Activity(Context context,String url){
        H5Activity.toH5Activity(context, url);
    }
}
