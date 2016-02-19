package com.niu.myapp.myapp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.widget.DialogFactory;
import com.niu.myapp.myapp.view.widget.ListDialogFragment;

import java.util.List;

/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private List<Friend> mFriends;

    private DialogFactory mDialogFactory;

    private Context mContext;

    private FriendsListener mListener;

    public ListDialogFragment.ListDialogListener listDialogListener = new ListDialogFragment.ListDialogListener() {
        @Override
        public void onItemClick(int position) {
            ToastUtil.showLong(mContext,"点击了 po="+position);

        }
    };

    public static interface FriendsListener{
        void onFriendItemLongClick(Friend longClickFriend);
    }

    public void setData(List<Friend> datas) {
        this.mFriends = datas;
        this.notifyDataSetChanged();
    }


    public FriendAdapter(Context context, DialogFactory dialogFactory,FriendsListener listener) {
        this.mContext = context;
        this.mDialogFactory = dialogFactory;
        dialogFactory.restoreDialogListener(this);
        this.mListener = listener;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        FriendViewHolder re = new FriendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_friend_item, viewGroup, false));
        return re;
    }



    @Override
    public void onBindViewHolder(FriendViewHolder friendViewHolder, int i) {

        final Friend f = mFriends.get(i);
        friendViewHolder.nameTv.setText(f.mName);

        if (i % 2 == 0) {
            friendViewHolder.nameTv.setBackgroundColor(Color.RED);
        } else {
            friendViewHolder.nameTv.setBackgroundColor(Color.WHITE);
        }
        friendViewHolder.nameTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (mDialogFactory != null) {

                    mDialogFactory.showListDialog(new String[]{"delete","copy"}, false, listDialogListener);
                }

                if (mListener != null) {
                    mListener.onFriendItemLongClick(f);
                }
                return true;
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mFriends != null) {
            return mFriends.size();
        }
        return 0;
    }


    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;

        public FriendViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
