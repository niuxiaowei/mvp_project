package com.niu.myapp.myapp.view.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuxiaowei on 2015/11/16.
 */
public class Friends {

    private List<Friend> mFriends = new ArrayList<Friend>();

    public Friend getFriend(int index){
        return mFriends.get(index);
    }

    public List<Friend> getAllFriends(){
        return mFriends;
    }
}
