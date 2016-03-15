package com.niu.myapp.myapp.data.remotedata;


import com.niu.myapp.myapp.common.util.DLog;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by niuxiaowei on 2016/3/9.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.github.com";


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
//        if (authToken != null) {
//            httpClient.interceptors().add(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
//                    Request original = chain.request();
//
//                    // Request customization: add request headers
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Authorization", authToken)
//                            .method(original.method(), original.body());
//
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            });
//        }
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        if (DLog.loggable) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            okBuilder.addInterceptor(interceptor);
        }
        Retrofit retrofit = builder.client(okBuilder.build()).build();
        return retrofit.create(serviceClass);
    }
}
