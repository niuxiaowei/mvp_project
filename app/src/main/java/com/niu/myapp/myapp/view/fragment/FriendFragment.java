package com.niu.myapp.myapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.internal.di.components.DaggerFriendsComponent;
import com.niu.myapp.myapp.internal.di.components.FriendsComponent;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.view.adapter.FriendAdapter;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.data.Friends;

/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendFragment extends BaseFragment implements IFriendListView {
    private FriendsComponent mFriendsComponent;
    private RecyclerView mRecyclerView;
    private FriendAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend,container);
    }

    @Override
    protected void onAddPresenters() {

        addPresenter(mFriendsComponent.getFriendListPresenter());
    }

    @Override
    protected void onInitPresenters() {
        this.mFriendsComponent.getFriendListPresenter().initView();
    }

    @Override
    protected void onInitializeInjector() {

                this.mFriendsComponent = DaggerFriendsComponent.builder()
                        .applicationComponent(mBaseActivity.getApplicationComponent())
                        .friendsModule(new FriendsModule(this)).build();
    }

    @Override
    protected void onInjectFragment() {
        this.mFriendsComponent.inject(this);
    }

    @Override
    public void bindDataForView(Friends friends) {
        mAdapter.setData(friends);
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mAdapter = new FriendAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mAdapter);
    }
}
