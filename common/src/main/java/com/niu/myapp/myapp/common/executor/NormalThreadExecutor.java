package com.niu.myapp.myapp.common.executor;

import java.util.concurrent.Executor;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class NormalThreadExecutor implements Executor{


    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
