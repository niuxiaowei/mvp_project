package com.niu.myapp.myapp.internal.di.modules;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.model.interactor.FriendUserCase;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
@Module
public class FriendsModule {


    public FriendsModule() {
        super();
    }

}
