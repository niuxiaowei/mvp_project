package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

/**
 * Created by niuxiaowei on 2015/11/19.
 */
public class BasePresenter {

    protected void executTaskOnUIThread(Runnable task){
        UIThreadExecutor.getExecutor().executTask(task);
    }

    protected void executTaskOnNormalThread(Runnable task){
        NormalThreadExecutor.getExecutor().executTask(task);
    }
}
