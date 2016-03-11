package com.niu.myapp.myapp.model.datasource;

import com.niu.myapp.myapp.model.localdata.FriendModel;

import java.util.List;

import rx.Observable;

/**
 * 朋友数据接口
 * Created by niuxiaowei on 2016/3/10.
 */
public interface FriendDatastore {
    /**
     * 根据登录用户ID获取朋友信息
     * @param loginUserId
     * @return
     */
    Observable<List<FriendModel>> getFriendModels(String loginUserId);
}
