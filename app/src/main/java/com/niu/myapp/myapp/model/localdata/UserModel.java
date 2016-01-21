package com.niu.myapp.myapp.model.localdata;

import com.niu.myapp.myapp.model.localdata.db.DBColumn;
import com.niu.myapp.myapp.model.localdata.db.DBTable;
import com.niu.myapp.myapp.model.localdata.db.ORMModel;

/**
 * Created by niuxiaowei on 2016/1/15.
 */
@DBTable(name = UserModel.TABLE_NAME)
public class UserModel extends ORMModel {

    public static final String TABLE_NAME =  "user";

    public static final String COL_USER_ID =  "userid";
    public static final String COL_USER_NAME =  "username";

    @DBColumn(name = COL_USER_ID)
    public String mUserId ;
    @DBColumn(name = COL_USER_NAME)
    public String mUserName;
}
