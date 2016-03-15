package com.niu.myapp.myapp.data.datastore;

import com.niu.myapp.myapp.data.localdata.LocalFriendDatastore;
import com.niu.myapp.myapp.data.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.data.remotedata.RemoteFriendDatastore;

/**
 * Created by niuxiaowei on 2016/3/10.
 */
public class FriendDatastoreFactory {


    protected UserDatabase mUserDatabase;

    public FriendDatastoreFactory(UserDatabase userDatabase){
        this.mUserDatabase = userDatabase;
    }

    public FriendDatastore createFriendDatastore(String loginUserid){
        //暂时只支持从本地读取信息
        return new LocalFriendDatastore(mUserDatabase);
    }

    public FriendDatastore createFriendDatastore(){
        //暂时只支持从本地读取信息
        return new LocalFriendDatastore(mUserDatabase);
    }

    public FriendDatastore createRemoteFriendDatastore(){
        return new RemoteFriendDatastore();
    }
}
