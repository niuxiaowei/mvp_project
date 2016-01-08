package com.niu.myapp.myapp.internal.di.modules;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.view.compnent.IFriendListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
@Module
public class FriendsModule {

    private  IFriendListView mView;

    public FriendsModule(IFriendListView view) {
        super();
        this.mView = view;
    }

    @Provides
    @PerActivity
    FriendListPresenter provideFriendListPresenter(){
        return new FriendListPresenter(mView);
    }
}
