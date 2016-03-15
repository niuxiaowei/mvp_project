package com.niu.myapp.myapp.base.present;


import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;

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
        mNormalThreadExeutor.execute(task);
    }
}
