package com.niu.myapp.myapp.model.localdata;

import com.niu.myapp.myapp.model.localdata.db.DBColumn;
import com.niu.myapp.myapp.model.localdata.db.DBTable;
import com.niu.myapp.myapp.model.localdata.db.ORMModel;

/**
 * Created by niuxiaowei on 2016/1/15.
 */
@DBTable(name = FriendModel.TABLE_NAME)
public class FriendModel extends ORMModel {

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
