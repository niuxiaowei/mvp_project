package com.niu.myapp.myapp.model.interactor;

import android.text.TextUtils;

import com.niu.myapp.myapp.model.localdata.FriendModel;
import com.niu.myapp.myapp.model.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by niuxiaowei on 2016/1/22.
 */
public class FriendUserCase {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    /**
     * 根据登录用户id获取登录用户的朋友
     */
    public void getFriends(final String loginId, Subscriber getFriendsSubscriber) {
        if (getFriendsSubscriber == null || TextUtils.isEmpty(loginId)) {
            return;
        }
        Subscription sn = rx.Observable.create(new rx.Observable.OnSubscribe<List<FriendModel>>() {


            @Override
            public void call(Subscriber<? super List<FriendModel>> subscriber) {

                List<FriendModel> friends = UserDatabase.getInstance().query("select * from " + FriendModel.TABLE_NAME + " where " + FriendModel.COL_LOGIN_USER_ID + "= " + loginId, FriendModel.class);
                subscriber.onNext(friends);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.from(NormalThreadExecutor.getExecutor()))
                .observeOn(Schedulers.from(UIThreadExecutor.getExecutor())).subscribe(getFriendsSubscriber);
        compositeSubscription.add(sn);
    }

    /**
     * 根据朋友ID获取朋友信息
     *
     * @param friendId
     * @param getFriendSubscriber
     */
    public void getFriend(final String loginId, final String friendId, Subscriber getFriendSubscriber) {
        if (getFriendSubscriber == null || TextUtils.isEmpty(friendId)|| TextUtils.isEmpty(loginId)) {
            return;
        }
        Subscription sn = rx.Observable.create(new rx.Observable.OnSubscribe<FriendModel>() {


            /**
             * @param subscriber
             */
            @Override
            public void call(Subscriber<? super FriendModel> subscriber) {
                String sql = "select * from " + FriendModel.TABLE_NAME + " where " + FriendModel.COL_LOGIN_USER_ID + "= " + loginId + " and " + FriendModel.COL_USER_ID + "=" + friendId;
                List<FriendModel> friends = UserDatabase.getInstance().query(sql, FriendModel.class);
                if (friends != null && friends.size() > 0) {

                    subscriber.onNext(friends.get(0));
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.from(NormalThreadExecutor.getExecutor()))
                .observeOn(Schedulers.from(UIThreadExecutor.getExecutor())).subscribe(getFriendSubscriber);
        compositeSubscription.add(sn);
    }

    /**
     * 删除一个好友

     * @param deleteFriendSubscriber
     */
    public void deleteFriend(final FriendModel friend, Subscriber deleteFriendSubscriber){
        if (deleteFriendSubscriber == null || friend == null) {
            return;
        }
        Subscription sn = rx.Observable.create(new rx.Observable.OnSubscribe<Boolean>() {


            /**
             * @param subscriber
             */
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean result = UserDatabase.getInstance().delete(friend);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.from(NormalThreadExecutor.getExecutor()))
                .observeOn(Schedulers.from(UIThreadExecutor.getExecutor())).subscribe(deleteFriendSubscriber);
        compositeSubscription.add(sn);
    }

    /**
     * 保存一个朋友
     * @param friend
     * @param saveFriendSubscriber
     */
    public void saveFriend(FriendModel friend, Subscriber saveFriendSubscriber){
        if(friend == null || saveFriendSubscriber == null){
            return;
        }
        List<FriendModel> fs = new ArrayList<>(1);
        fs.add(friend);
        saveFriends(fs, saveFriendSubscriber);
    }

    /**
     * 保存多个朋友
     * @param friends
     * @param saveFriendSubscriber
     */
    public void saveFriends(final List<FriendModel> friends, Subscriber saveFriendSubscriber){
        if (saveFriendSubscriber == null || friends == null) {
            return;
        }
        Subscription sn = rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {


            /**
             * @param subscriber
             */
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int rows = UserDatabase.getInstance().insert(friends);
                subscriber.onNext(rows);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.from(NormalThreadExecutor.getExecutor()))
                .observeOn(Schedulers.from(UIThreadExecutor.getExecutor())).subscribe(saveFriendSubscriber);
        compositeSubscription.add(sn);
    }

    public void unsubscribe(){
        if(compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
        }
    }

}
