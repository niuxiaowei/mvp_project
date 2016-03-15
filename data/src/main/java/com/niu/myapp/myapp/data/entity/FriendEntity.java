package com.niu.myapp.myapp.data.entity;

import com.niu.myapp.myapp.data.localdata.db.DBColumn;
import com.niu.myapp.myapp.data.localdata.db.DBTable;
import com.niu.myapp.myapp.data.localdata.db.ORMModel;

/**
 * Created by niuxiaowei on 2016/1/15.
 */
@DBTable(name = FriendEntity.TABLE_NAME)
public class FriendEntity extends ORMModel {

    public static final String TABLE_NAME =  "friend";

    public static final String COL_USER_ID =  "userid";
    public static final String COL_USER_NAME =  "username";
    public static final String COL_LOGIN_USER_ID =  "login_userid";


    @DBColumn(name = COL_USER_ID)
    public String mUserId ;
    @DBColumn(name = COL_USER_NAME)
    public String mUserName;
    @DBColumn(name = COL_LOGIN_USER_ID)
    public String mLoginUserId;
}
