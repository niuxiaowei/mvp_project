package com.niu.myapp.myapp.view.compnent;

import com.niu.myapp.myapp.base.present.IView;
import com.niu.myapp.myapp.view.data.Friend;

import java.util.List;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public interface IFriendListView extends IView<List<Friend>> {

    void showGitHubUser(String userName);
}
