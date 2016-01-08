package com.niu.myapp.myapp.view.executor;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class NormalThreadExecutor {

    private static NormalThreadExecutor sExecutor = new NormalThreadExecutor();

    private NormalThreadExecutor(){

    }

    public static NormalThreadExecutor getExecutor(){
        return sExecutor;
    }

    public void executTask(Runnable task){
        new Thread(task).start();
    }
}
