package com.niu.myapp.myapp.model.datasource;

import com.niu.myapp.myapp.model.localdata.LocalFriendDatastore;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2016/3/10.
 */
public class FriendDatastoreFactory {



    public FriendDatastore createFriendDatastore(String loginUserid){
        //暂时只支持从本地读取信息
        return new LocalFriendDatastore();
    }
}
