package com.niu.myapp.myapp.view.executor;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class UIThreadExecutor {
    private static UIThreadExecutor sExecutor = new UIThreadExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private UIThreadExecutor(){

    }

    public static UIThreadExecutor getExecutor(){
        return sExecutor;
    }

    public void executTask(Runnable task){
        mHandler.post(task);
    }
}
