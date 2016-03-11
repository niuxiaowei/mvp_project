package com.niu.myapp.myapp.presenter.mapper;

import com.niu.myapp.myapp.data.entity.FriendEntity;
import com.niu.myapp.myapp.view.data.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuxiaowei on 2016/2/2.
 */
public class FriendModelDataMapper {

    /**
     * FriendModel映射为Friend
     *
     * @param friendEntity
     * @return
     */
    public static Friend map(FriendEntity friendEntity) {

        if (friendEntity != null) {
            Friend result = new Friend();
            result.mId = friendEntity._id;
            result.mName = friendEntity.mUserName;
            result.mUserId = friendEntity.mUserId;
            result.mLoginId = friendEntity.mLoginUserId;
            return result;
        }
        return null;
    }

    /**
     * List<FriendEntity>映射为List<Friend>
     * @param friendEntities
     * @return
     */
    public static List<Friend> mapFriendModels(List<FriendEntity> friendEntities) {

        if (friendEntities != null && friendEntities.size() > 0) {
            List<Friend> result = new ArrayList<>(friendEntities.size());
            for (int i = 0; i < friendEntities.size(); i++) {

                result.add(map(friendEntities.get(i)));
            }
            return result;
        }
        return null;
    }

    public static List<FriendEntity> mapFriends(List<Friend> friends) {

        if (friends != null && friends.size() > 0) {
            List<FriendEntity> result = new ArrayList<>(friends.size());
            for (int i = 0; i < friends.size(); i++) {

                result.add(map(friends.get(i)));
            }
            return result;
        }
        return null;
    }

    public static FriendEntity map(Friend friend) {

        if (friend != null) {
            FriendEntity result = new FriendEntity();
            result._id = friend.mId;
            result.mUserName = friend.mName;
            result.mUserId = friend.mUserId;
            result.mLoginUserId = friend.mLoginId;
            return result;
        }
        return null;
    }

}
