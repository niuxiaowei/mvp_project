package com.niu.myapp.myapp.model.interactor;

import android.text.TextUtils;

import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.model.datasource.FriendDatastoreFactory;
import com.niu.myapp.myapp.model.localdata.FriendModel;
import com.niu.myapp.myapp.model.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.model.remotedata.GitHubService;
import com.niu.myapp.myapp.model.remotedata.ServiceGenerator;
import com.niu.myapp.myapp.model.remotedata.User;
import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by niuxiaowei on 2016/1/22.
 */
public class FriendUserCase {

    protected UIThreadExecutor mUIThreadExecutor;
    protected NormalThreadExecutor normalThreadExecutor;
    private FriendDatastoreFactory mFriendDatastoreFactory;

    @Inject
    public FriendUserCase(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor,FriendDatastoreFactory friendDatastoreFactory){

        this.mUIThreadExecutor = uiThreadExecutor;
        this.normalThreadExecutor = normalThreadExecutor;
        this.mFriendDatastoreFactory = friendDatastoreFactory;
    }

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    /**
     * 根据登录用户id获取登录用户的朋友
     */
    public void getFriends(final String loginId, Subscriber getFriendsSubscriber) {
        if (getFriendsSubscriber == null || TextUtils.isEmpty(loginId)) {
            return;
        }

        Subscription sn =mFriendDatastoreFactory.createFriendDatastore(loginId).getFriendModels(loginId).subscribeOn(Schedulers.from(normalThreadExecutor))
                .observeOn(Schedulers.from(mUIThreadExecutor)).subscribe(getFriendsSubscriber);
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
        }).subscribeOn(Schedulers.from(normalThreadExecutor))
                .observeOn(Schedulers.from(mUIThreadExecutor)).subscribe(getFriendSubscriber);
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
        }).subscribeOn(Schedulers.from(normalThreadExecutor))
                .observeOn(Schedulers.from(mUIThreadExecutor)).subscribe(deleteFriendSubscriber);
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
        }).subscribeOn(Schedulers.from(normalThreadExecutor))
                .observeOn(Schedulers.from(mUIThreadExecutor)).subscribe(saveFriendSubscriber);
        compositeSubscription.add(sn);
    }

    public void unsubscribe(){
        if(compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
        }
    }

    public void getGitHubUser(String userName, Subscriber userSubscriber){

        GitHubService service = ServiceGenerator.createService(GitHubService.class);

//        Call<User> call = service.user1(userName);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                DLog.i("test", " res=" + response.body() + " er=" + response.errorBody() + " me=" + response.message());
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                DLog.i("test", " failure=" + t.getMessage());
//            }
//        });

        //请求数据操作必须制定执行线程，否则失败
        Subscription sn = service.user(userName).subscribeOn(Schedulers.from(normalThreadExecutor)).observeOn(Schedulers.from(mUIThreadExecutor)).subscribe(userSubscriber);
        compositeSubscription.add(sn);
    }

}
