package com.niu.myapp.myapp.data.entity;

import com.niu.myapp.myapp.data.localdata.db.DBColumn;
import com.niu.myapp.myapp.data.localdata.db.DBTable;
import com.niu.myapp.myapp.data.localdata.db.ORMModel;

/**
 * Created by niuxiaowei on 2016/1/15.
 */
@DBTable(name = UserEntity.TABLE_NAME)
public class UserEntity extends ORMModel {

    public static final String TABLE_NAME =  "user";

    public static final String COL_LOGIN_USER_ID =  "login_user_id";
    public static final String COL_USER_NAME =  "username";

    @DBColumn(name = COL_LOGIN_USER_ID)
    public String mLoginUserId;
    @DBColumn(name = COL_USER_NAME)
    public String mUserName;
}
