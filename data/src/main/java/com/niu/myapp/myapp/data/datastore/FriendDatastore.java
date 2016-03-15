package com.niu.myapp.myapp.data.datastore;

import com.niu.myapp.myapp.data.entity.FriendEntity;
import com.niu.myapp.myapp.data.entity.GitHubUserEntity;

import java.util.List;

import rx.Observable;

/**
 * 朋友数据接口
 * Created by niuxiaowei on 2016/3/10.
 */
public interface FriendDatastore {
    /**
     * 根据登录用户ID获取朋友信息
     * @param loginUserId
     * @return
     */
    Observable<List<FriendEntity>> getFriendModels(String loginUserId);

    /**
     * 根据朋友id获取朋友信息
     * @param loginId
     * @param friendId
     * @return
     */
    Observable<FriendEntity> getFriend(String loginId, final String friendId);

    Observable<Boolean> deleteFriend(FriendEntity friend);

    Observable<Integer> saveFriends(List<FriendEntity> friends);

    Observable<GitHubUserEntity> getGitHubUserEntity(String userName);
}
