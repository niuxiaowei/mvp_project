package com.niu.myapp.myapp.view;

import android.content.Context;
import android.content.Intent;

import com.niu.myapp.myapp.view.activity.FriendActivity;
import com.niu.myapp.myapp.view.activity.H5Activity;
import com.niu.myapp.myapp.view.activity.RXBusExampleActivity;

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

    public void toFriendsActivity(Context context){
        Intent toF = new Intent(context,FriendActivity.class);
        context.startActivity(toF);
    }

    public void toRXBusExa(Context context){
        Intent toF = new Intent(context,RXBusExampleActivity.class);
        context.startActivity(toF);
    }
}
