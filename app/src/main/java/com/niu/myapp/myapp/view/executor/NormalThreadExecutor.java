package com.niu.myapp.myapp.view.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class NormalThreadExecutor implements Executor{

    private static NormalThreadExecutor sExecutor = new NormalThreadExecutor();

    private NormalThreadExecutor(){

    }

    public static NormalThreadExecutor getExecutor(){
        return sExecutor;
    }



    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
