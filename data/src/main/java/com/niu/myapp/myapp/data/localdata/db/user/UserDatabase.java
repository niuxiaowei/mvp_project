package com.niu.myapp.myapp.data.localdata.db.user;

import android.content.Context;

import com.niu.myapp.myapp.data.entity.FriendEntity;
import com.niu.myapp.myapp.data.entity.UserEntity;
import com.niu.myapp.myapp.data.localdata.db.Database;
import com.niu.myapp.myapp.data.localdata.db.ORMModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuxiaowei on 2016/1/15.
 */
public class UserDatabase extends Database {


    public UserDatabase(Context context){
        super(context);
    }

    @Override
    protected String getDBName() {
        return "user";
    }

    @Override
    protected int getDBVersion() {
        return 1;
    }

    @Override
    protected List<Class<? extends ORMModel>> getDBTables() {
        List res = new ArrayList();
        res.add(FriendEntity.class);
        res.add(UserEntity.class);
        return res;
    }
}
