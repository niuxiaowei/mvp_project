package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.compnent.IView;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public interface Presenter {
    /**
     * 子类主动调用自己view的{@link IView initView()}方法
     */
    void initView();

    public void onStop();

    public void onResume();

    public void onDestory();
}
