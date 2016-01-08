package com.niu.myapp.myapp.view.compnent;

import com.niu.myapp.myapp.view.data.User;

/**
 * Created by niuxiaowei on 2015/11/13.
 */
public interface ILoginView extends IView<User>{
    void showLoginingView();

    void loginSuccess();

    void loginFailed(String failedReason);

}
