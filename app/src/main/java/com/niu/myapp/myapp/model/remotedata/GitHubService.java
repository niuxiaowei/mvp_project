package com.niu.myapp.myapp.model.remotedata;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by niuxiaowei on 2016/3/9.
 */
public interface GitHubService {
    @GET("/users/{username}")
    Observable<User> user(@Path("username") String username);

    @GET("/users/{username}")
    Call<User> user1(@Path("username") String username);
}
