package com.example.lw_pc.rrd.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lw_pc.rrd.R;
import com.example.lw_pc.rrd.core.Api;
import com.example.lw_pc.rrd.core.ZhihuApi;
import com.example.lw_pc.rrd.model.News;
import com.example.lw_pc.rrd.model.TodayNews;
import com.example.lw_pc.rrd.util.HtmlUtil;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment {
    public static final String STORY = "story";

    private WebView mWebView;
    private ImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolLayout;
    private Toolbar mToolBar;
    private NestedScrollView mNestedScrollView;

    private TodayNews.Story mStory;
    private News mNews;



    public static Fragment newInstance(TodayNews.Story story) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(STORY, story);
        Fragment Fragment = new NewsDetailFragment();
        Fragment.setArguments(bundle);
        return Fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);
        mWebView = (WebView) v.findViewById(R.id.wv_news);
        mImageView = (ImageView) v.findViewById(R.id.iv_header);
        mCollapsingToolLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsingToolbarLayout);
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mNestedScrollView = (NestedScrollView) v.findViewById(R.id.nestedScrollView);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStory = (TodayNews.Story) getArguments().getSerializable(STORY);
        init();
        LoadData();

    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            // 给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true);
        mNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mCollapsingToolLayout.setTitle(getString(R.string.app_name));
    }

    private void LoadData() {
        Api api = ZhihuApi.getZhihuApi();
        api.getNews(mStory.getId()).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Response<News> response, Retrofit retrofit) {
                News result = response.body();
                Picasso.with(getActivity())
                        .load(result.getImage())
                        .into(mImageView);

                String htmlData = HtmlUtil.createHtmlData(result);
                mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
