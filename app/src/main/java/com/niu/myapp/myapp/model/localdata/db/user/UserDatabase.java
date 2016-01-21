package com.niu.myapp.myapp.model.localdata.db.user;

import com.niu.myapp.myapp.model.localdata.FriendModel;
import com.niu.myapp.myapp.model.localdata.UserModel;
import com.niu.myapp.myapp.model.localdata.db.Database;
import com.niu.myapp.myapp.model.localdata.db.ORMModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuxiaowei on 2016/1/15.
 */
public class UserDatabase extends Database {

    private static UserDatabase mInstance = new UserDatabase();

    private UserDatabase(){

    }

    public static UserDatabase getInstance(){
        return mInstance;
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
        res.add(FriendModel.class);
        res.add(UserModel.class);
        return res;
    }
}
