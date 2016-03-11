package com.niu.myapp.myapp.model.remotedata;

import com.niu.myapp.myapp.model.datasource.FriendDatastore;
import com.niu.myapp.myapp.model.localdata.FriendModel;

import java.util.List;

import rx.Observable;

/**
 * Created by niuxiaowei on 2016/3/10.
 */
public class RemoteFriendDatastore implements FriendDatastore {
    @Override
    public Observable<List<FriendModel>> getFriendModels(String loginUserId) {
        return null;
    }
}
