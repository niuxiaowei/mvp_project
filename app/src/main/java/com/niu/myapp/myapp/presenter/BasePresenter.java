package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

/**
 * Created by niuxiaowei on 2015/11/19.
 */
public class BasePresenter {

    protected UIThreadExecutor mUIThreadExecutor;
    protected NormalThreadExecutor mNormalThreadExeutor;

    public BasePresenter(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor){
        this.mNormalThreadExeutor = normalThreadExecutor;
        this.mUIThreadExecutor = uiThreadExecutor;
    }

    protected void executTaskOnUIThread(Runnable task){
        mUIThreadExecutor.execute(task);
    }

    protected void executTaskOnNormalThread(Runnable task){
        mUIThreadExecutor.execute(task);
    }
}
