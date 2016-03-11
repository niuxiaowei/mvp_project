package com.niu.myapp.myapp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.components.DaggerFragmentInjectComponent;
import com.niu.myapp.myapp.internal.di.components.DaggerFriendsComponent;
import com.niu.myapp.myapp.internal.di.components.FriendsComponent;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.SubFriendsModule;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.view.adapter.FriendAdapter;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.widget.BaseDialogFragment;
import com.niu.myapp.myapp.view.widget.ConfirmDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import static com.niu.myapp.myapp.view.adapter.FriendAdapter.*;

/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendFragment extends BaseFragment implements IFriendListView,ConfirmDialogFragment.ConfirmDialogListener{
    private RecyclerView mRecyclerView;
    private FriendAdapter mAdapter;
    @Inject
    FriendListPresenter mPresenter;
    private FriendsListener mListener = new FriendsListener(){

        Friend longClickFriend ;
        @Override
        public void onFriendItemLongClick(Friend longClickFriend) {
            this.longClickFriend = longClickFriend;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend,container,false);
    }

    @Override
    protected void onAddPresenters() {

        addPresenter(mPresenter);
    }

    @Override
    protected void onInitPresenters() {
        mPresenter.initView(this);
    }


    @Override
    protected void onInjectFragment() {
//         DaggerFriendsComponent.builder()
//                .applicationComponent(mBaseActivity.getApplicationComponent())
//                .friendsModule(new FriendsModule()).build().inject(this);
        DaggerFragmentInjectComponent.builder().applicationComponent(mBaseActivity.getApplicationComponent()).build().injectFriendsFragment(this);
//        mBaseActivity.getApplicationComponent().getSubFriendsComponent(new SubFriendsModule()).injectFragment(this);
    }

    @Override
    public void bindDataForView(List<Friend> friends) {
        mAdapter.setData(friends);
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mAdapter = new FriendAdapter(mBaseActivity,mDialogFactory,mListener);
        mRecyclerView.setAdapter(mAdapter);

        getView().findViewById(R.id.add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Friend> fs = new ArrayList<Friend>(5);
                Random r = new Random(1000);
                for (int i = 0; i < 5; i++) {
                    Friend f = new Friend();
                    f.mLoginId = FriendListPresenter.LOGIN_USERID;
                    f.mUserId = r.nextLong() + "";
                    f.mName = f.mUserId;
                    f.mAge = 20;
                    fs.add(f);
                }
                mPresenter.saveFriends(fs);
            }
        });

        getView().findViewById(R.id.show_confi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mDialogFactory.showConfirmDialog("对话框","我是确认对话框",false,FriendFragment.this);
            }
        });
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        ToastUtil.showLong(getActivity(),"点击了我 我是which="+which);
    }
}
