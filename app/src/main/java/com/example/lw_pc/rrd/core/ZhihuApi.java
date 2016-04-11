package com.example.lw_pc.rrd.core;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by LW-PC on 2016/3/25.
 */
public class ZhihuApi {
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    private Retrofit retrofit;
    private static ZhihuApi instance;

    private ZhihuApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ZhihuApi getInstance() {
        if(instance == null) {
            synchronized (ZhihuApi.class) {
                if(instance == null) {
                    instance = new ZhihuApi();
                }
            }
        }
        return instance;
    }

    public static <T> T create(Class<T> tClass) {
        return getInstance().retrofit.create(tClass);
    }

    public static Api getZhihuApi() {
        return create(Api.class);
    }


}
