package com.niu.myapp.myapp.view.compnent;

/**
 * Created by niuxiaowei on 2015/11/13.
 */
public interface IView<Data> {
    void bindDataForView(Data data);
    void initView();
}
