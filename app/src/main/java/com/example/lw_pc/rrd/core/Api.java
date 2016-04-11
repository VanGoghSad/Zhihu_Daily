package com.example.lw_pc.rrd.core;



import com.example.lw_pc.rrd.model.Extra;
import com.example.lw_pc.rrd.model.News;
import com.example.lw_pc.rrd.model.StartImage;
import com.example.lw_pc.rrd.model.TodayNews;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by LW-PC on 2016/3/25.
 */
public interface Api {
    /** 获取最新日报 */
    @GET("news/latest")
    Call<TodayNews> getTodayNews();

    /** 获取启动图片 */
    @GET("start-image/1080*1776")
    Call<StartImage> getStartImage();

    /** 获取日报的详细内容 */
    @GET("news/{newsId}")
    Call<News> getNews(@Path("newsId") long newsId);

    /** 获取新闻的额外信息 */
    @GET("story-extra/{id}")
    Call<Extra> getExtra(@Path("id") long id);
}
