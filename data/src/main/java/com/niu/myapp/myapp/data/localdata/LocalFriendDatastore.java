package com.niu.myapp.myapp.data.localdata;

import com.niu.myapp.myapp.data.datastore.FriendDatastore;
import com.niu.myapp.myapp.data.entity.FriendEntity;
import com.niu.myapp.myapp.data.entity.GitHubUserEntity;
import com.niu.myapp.myapp.data.localdata.db.user.UserDatabase;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by niuxiaowei on 2016/3/10.
 */
public class LocalFriendDatastore implements FriendDatastore {

    private UserDatabase mUserDatabase;

    public LocalFriendDatastore(UserDatabase userDatabase){
        this.mUserDatabase = userDatabase;
    }

    @Override
    public Observable<List<FriendEntity>> getFriendModels(String loginUserId) {
        String sql = "select * from " + FriendEntity.TABLE_NAME + " where " + FriendEntity.COL_LOGIN_USER_ID + "= " + loginUserId ;
        final List<FriendEntity> friends = mUserDatabase.query(sql, FriendEntity.class);
        Observable<List<FriendEntity>> result = Observable.create(new Observable.OnSubscribe<List<FriendEntity>>() {
            @Override
            public void call(Subscriber<? super List<FriendEntity>> subscriber) {
                subscriber.onNext(friends);
            }
        });
        return result;
    }

    @Override
    public Observable<FriendEntity> getFriend(String loginId, String friendId) {
        String sql = "select * from " + FriendEntity.TABLE_NAME + " where " + FriendEntity.COL_LOGIN_USER_ID + "= " + loginId + " and " + FriendEntity.COL_USER_ID + "=" + friendId;
        List<FriendEntity> friends = mUserDatabase.query(sql, FriendEntity.class);
        if(friends != null && friends.size() > 0){
            Observable<FriendEntity> result = Observable.from(friends.subList(0,1));
            return result;
        }
        return null;
    }

    @Override
    public Observable<Boolean> deleteFriend(FriendEntity friend) {
        boolean isDelete = mUserDatabase.delete(friend);
        Observable<Boolean> result = Observable.from(new Boolean[]{isDelete});
        return result;
    }

    @Override
    public Observable<Integer> saveFriends(List<FriendEntity> friends) {
        int rows = mUserDatabase.insert(friends);
        Observable<Integer> result = Observable.from(new Integer[]{rows});
        return result;
    }

    @Override
    public Observable<GitHubUserEntity> getGitHubUserEntity(String userName) {
        return null;
    }
}
