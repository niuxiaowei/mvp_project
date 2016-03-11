package com.niu.myapp.myapp.model.localdata;

import com.niu.myapp.myapp.model.datasource.FriendDatastore;
import com.niu.myapp.myapp.model.localdata.FriendModel;
import com.niu.myapp.myapp.model.localdata.db.user.UserDatabase;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by niuxiaowei on 2016/3/10.
 */
public class LocalFriendDatastore implements FriendDatastore {
    @Override
    public Observable<List<FriendModel>> getFriendModels(String loginUserId) {
        String sql = "select * from " + FriendModel.TABLE_NAME + " where " + FriendModel.COL_LOGIN_USER_ID + "= " + loginUserId ;
        final List<FriendModel> friends = UserDatabase.getInstance().query(sql, FriendModel.class);
        Observable<List<FriendModel>> result = Observable.create(new Observable.OnSubscribe<List<FriendModel>>() {
            @Override
            public void call(Subscriber<? super List<FriendModel>> subscriber) {
                subscriber.onNext(friends);
            }
        });
        return result;
    }
}
