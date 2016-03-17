package com.niu.myapp.myapp.data.interactor;

import android.text.TextUtils;

import com.niu.myapp.myapp.basedata.interactor.BaseUserCase;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.data.datastore.FriendDatastoreFactory;
import com.niu.myapp.myapp.data.entity.FriendEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by niuxiaowei on 2016/1/22.
 */
public class FriendUserCase extends BaseUserCase {


    private FriendDatastoreFactory mFriendDatastoreFactory;

    @Inject
    public FriendUserCase(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor,
            FriendDatastoreFactory friendDatastoreFactory) {
        super(uiThreadExecutor,normalThreadExecutor);

        this.mFriendDatastoreFactory = friendDatastoreFactory;
    }



    /**
     * 根据登录用户id获取登录用户的朋友
     */
    public void getFriends(final String loginId, Subscriber getFriendsSubscriber) {
        if (getFriendsSubscriber == null || TextUtils.isEmpty(loginId)) {
            return;
        }
        execute(mFriendDatastoreFactory.createFriendDatastore(loginId).getFriendModels(loginId), getFriendsSubscriber);

    }

    /**
     * 根据朋友ID获取朋友信息
     *
     * @param friendId
     * @param getFriendSubscriber
     */
    public void getFriend(final String loginId, final String friendId, Subscriber getFriendSubscriber) {
        if (getFriendSubscriber == null || TextUtils.isEmpty(friendId) || TextUtils.isEmpty(loginId)) {
            return;
        }
        execute(mFriendDatastoreFactory.createFriendDatastore(loginId).getFriend(loginId, friendId),
                getFriendSubscriber);

    }

    /**
     * 删除一个好友
     *
     * @param deleteFriendSubscriber
     */
    public void deleteFriend(final FriendEntity friend, Subscriber deleteFriendSubscriber) {
        if (deleteFriendSubscriber == null || friend == null) {
            return;
        }
        execute(mFriendDatastoreFactory.createFriendDatastore().deleteFriend(friend), deleteFriendSubscriber);

    }

    /**
     * 保存一个朋友
     *
     * @param friend
     * @param saveFriendSubscriber
     */
    public void saveFriend(FriendEntity friend, Subscriber saveFriendSubscriber) {
        if (friend == null || saveFriendSubscriber == null) {
            return;
        }
        List<FriendEntity> fs = new ArrayList<>(1);
        fs.add(friend);
        saveFriends(fs, saveFriendSubscriber);
    }

    /**
     * 保存多个朋友
     *
     * @param friends
     * @param saveFriendSubscriber
     */
    public void saveFriends(final List<FriendEntity> friends, Subscriber saveFriendSubscriber) {
        if (saveFriendSubscriber == null || friends == null) {
            return;
        }
        execute(mFriendDatastoreFactory.createFriendDatastore().saveFriends(friends), saveFriendSubscriber);

    }



    public void getGitHubUser(String userName, Subscriber userSubscriber) {

        execute(mFriendDatastoreFactory.createRemoteFriendDatastore().getGitHubUserEntity(userName), userSubscriber);

    }

}
