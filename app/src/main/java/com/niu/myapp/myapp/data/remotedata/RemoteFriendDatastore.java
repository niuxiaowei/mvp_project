package com.niu.myapp.myapp.data.remotedata;

import com.niu.myapp.myapp.data.datastore.FriendDatastore;
import com.niu.myapp.myapp.data.entity.FriendEntity;
import com.niu.myapp.myapp.data.entity.GitHubUserEntity;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by niuxiaowei on 2016/3/10.
 */
public class RemoteFriendDatastore implements FriendDatastore {
    @Override
    public Observable<List<FriendEntity>> getFriendModels(String loginUserId) {
        return null;
    }

    @Override
    public Observable<FriendEntity> getFriend(String loginId, String friendId) {
        return null;
    }

    @Override
    public Observable<Boolean> deleteFriend(FriendEntity friend) {
        return null;
    }

    @Override
    public Observable<Integer> saveFriends(List<FriendEntity> friends) {
        return null;
    }

    @Override
    public Observable<GitHubUserEntity> getGitHubUserEntity(String userName) {
        GitHubService service = ServiceGenerator.createService(GitHubService.class);
        return service.user("niuxiaowei");
    }
}
