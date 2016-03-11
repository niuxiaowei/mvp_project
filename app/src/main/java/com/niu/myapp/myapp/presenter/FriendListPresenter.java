package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.model.interactor.FriendUserCase;
import com.niu.myapp.myapp.model.localdata.FriendModel;
import com.niu.myapp.myapp.model.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.model.remotedata.User;
import com.niu.myapp.myapp.presenter.mapper.FriendModelDataMapper;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public class FriendListPresenter extends BasePresenter implements Presenter {

    private List<Friend> mFriends = new ArrayList<>();
    private FriendUserCase mFriendUserCase;
    //test
    public static final String LOGIN_USERID = "111";

    @Inject
    public FriendListPresenter(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor, FriendUserCase friendUserCase) {
        super(uiThreadExecutor,normalThreadExecutor);
        this.mFriendUserCase = friendUserCase;
    }

    private IFriendListView mFriendListView;

    public void initView(IFriendListView friendListView) {
        this.mFriendListView = friendListView;
        if(mFriendListView != null){
            mFriendListView.initView();
        }
        mFriendUserCase.getFriends(LOGIN_USERID,new Subscriber<List<FriendModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<FriendModel> friendModels) {
                mFriendListView.bindDataForView(mFriends = FriendModelDataMapper.mapFriendModels(friendModels));
            }
        });

        getGitHubUser("niuxiaowei");
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestory() {
        mFriendUserCase.unsubscribe();
    }

    public void saveFriends(final List<Friend> friends){
        if(friends != null && friends.size() > 0){

            List<FriendModel> friendModels = FriendModelDataMapper.mapFriends(friends);
            mFriendUserCase.saveFriends(friendModels, new Subscriber<Integer>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Integer o) {
                    mFriends.addAll(0,friends);
                    mFriendListView.bindDataForView(mFriends);
                }
            });
        }
    }

    private void getGitHubUser(String userName){
        mFriendUserCase.getGitHubUser(userName, new Subscriber<User>() {
            @Override
            public void onCompleted() {
                DLog.i("test","  onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                DLog.i("test","  onError e="+e.getMessage());

            }

            @Override
            public void onNext(User user) {
                DLog.i("test","  user="+user.getLogin());
            }
        });
    }


}
