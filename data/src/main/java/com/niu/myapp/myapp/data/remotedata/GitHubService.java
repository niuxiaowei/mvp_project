package com.niu.myapp.myapp.data.remotedata;


import com.niu.myapp.myapp.data.entity.GitHubUserEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by niuxiaowei on 2016/3/9.
 */
public interface GitHubService {
    @GET("/users/{username}")
    Observable<GitHubUserEntity> user(@Path("username") String username);

    @GET("/users/{username}")
    Call<GitHubUserEntity> user1(@Path("username") String username);
}
