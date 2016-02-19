package com.niu.myapp.myapp.view.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class UIThreadExecutor implements Executor {
    private static UIThreadExecutor sExecutor = new UIThreadExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private UIThreadExecutor(){

    }

    public static UIThreadExecutor getExecutor(){
        return sExecutor;
    }



    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}
