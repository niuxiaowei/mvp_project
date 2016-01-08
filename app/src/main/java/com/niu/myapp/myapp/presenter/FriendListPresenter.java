package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.view.compnent.IFriendListView;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public class FriendListPresenter extends BasePresenter implements Presenter {

    @Inject
    public FriendListPresenter(IFriendListView friendListView) {
        super();
        this.mFriendListView = friendListView;
    }

    private IFriendListView mFriendListView;

    @Override
    public void initView() {
        if(mFriendListView != null){
            mFriendListView.initView();
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestory() {

    }


}