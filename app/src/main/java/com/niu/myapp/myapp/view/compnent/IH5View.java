package com.niu.myapp.myapp.view.compnent;

import com.niu.myapp.myapp.base.present.IView;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by niuxiaowei on 2015/11/13.
 */
public interface IH5View extends IView {


        //为界面title设置值
        void setTitleForView(String title);

        //html5页面ok了
        void onHtml5PageIsOK();

        void onCall(String phoneNum);
}
