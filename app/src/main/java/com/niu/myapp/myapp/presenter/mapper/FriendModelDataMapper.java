package com.niu.myapp.myapp.presenter.mapper;

import com.niu.myapp.myapp.model.localdata.FriendModel;
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
     * @param friendModel
     * @return
     */
    public static Friend map(FriendModel friendModel) {

        if (friendModel != null) {
            Friend result = new Friend();
            result.mId = friendModel._id;
            result.mName = friendModel.mUserName;
            result.mUserId = friendModel.mUserId;
            result.mLoginId = friendModel.mLoginUserId;
            return result;
        }
        return null;
    }

    /**
     * List<FriendModel>映射为List<Friend>
     * @param friendModels
     * @return
     */
    public static List<Friend> mapFriendModels(List<FriendModel> friendModels) {

        if (friendModels != null && friendModels.size() > 0) {
            List<Friend> result = new ArrayList<>(friendModels.size());
            for (int i = 0; i < friendModels.size(); i++) {

                result.add(map(friendModels.get(i)));
            }
            return result;
        }
        return null;
    }

    public static List<FriendModel> mapFriends(List<Friend> friends) {

        if (friends != null && friends.size() > 0) {
            List<FriendModel> result = new ArrayList<>(friends.size());
            for (int i = 0; i < friends.size(); i++) {

                result.add(map(friends.get(i)));
            }
            return result;
        }
        return null;
    }

    public static FriendModel map(Friend friend) {

        if (friend != null) {
            FriendModel result = new FriendModel();
            result._id = friend.mId;
            result.mUserName = friend.mName;
            result.mUserId = friend.mUserId;
            result.mLoginUserId = friend.mLoginId;
            return result;
        }
        return null;
    }

}
