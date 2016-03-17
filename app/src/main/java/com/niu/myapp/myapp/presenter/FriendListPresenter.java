package com.niu.myapp.myapp.presenter;

import com.niu.myapp.myapp.base.present.BasePresenter;
import com.niu.myapp.myapp.base.present.Presenter;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.common.util.DLog;

import com.niu.myapp.myapp.data.entity.FriendEntity;import com.niu.myapp.myapp.data.entity.GitHubUserEntity;import com.niu.myapp.myapp.data.interactor.FriendUserCase;import com.niu.myapp.myapp.presenter.mapper.FriendModelDataMapper;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.data.Friend;

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
        mFriendUserCase.getFriends(LOGIN_USERID,new Subscriber<List<FriendEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<FriendEntity> friendEntities) {
                mFriendListView.bindDataForView(mFriends = FriendModelDataMapper.mapFriendModels(friendEntities));
            }
        });

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

            List<FriendEntity> friendEntities = FriendModelDataMapper.mapFriends(friends);
            mFriendUserCase.saveFriends(friendEntities, new Subscriber<Integer>() {
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

    public void getGitHubUser(String userName){

        mFriendUserCase.getGitHubUser(userName, new Subscriber<GitHubUserEntity>() {
            @Override
            public void onCompleted() {
                DLog.i("test","  onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                DLog.i("test","  onError e="+e.getMessage());

            }

            @Override
            public void onNext(GitHubUserEntity gitHubUserEntity) {
                mFriendListView.showGitHubUser(gitHubUserEntity.getLogin());
                DLog.i("test","  gitHubUserEntity="+ gitHubUserEntity.getLogin());
            }
        });
    }


}
