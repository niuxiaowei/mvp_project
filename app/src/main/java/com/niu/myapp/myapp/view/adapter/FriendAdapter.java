package com.niu.myapp.myapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.data.Friends;

/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private final Context mContext;
    private Friends mFriends;


    public void setData(Friends datas){
        this.mFriends = datas;
        this.notifyDataSetChanged();;
    }


    public FriendAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        FriendViewHolder re = new FriendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_friend_item,viewGroup,false));
        return re;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder friendViewHolder, int i) {

        Friend f = mFriends.getFriend(i);
        friendViewHolder.nameTv.setText(f.mName);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mFriends != null){
            return mFriends.length();
        }
        return 0;
    }



    public static class FriendViewHolder extends RecyclerView.ViewHolder{
         TextView nameTv;

        public FriendViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView)itemView.findViewById(R.id.name);
        }
    }
}
