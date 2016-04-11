package com.example.lw_pc.rrd.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lw_pc.rrd.R;
import com.example.lw_pc.rrd.adapter.NewsListAdapter;
import com.example.lw_pc.rrd.core.Api;
import com.example.lw_pc.rrd.core.ZhihuApi;
import com.example.lw_pc.rrd.model.Extra;
import com.example.lw_pc.rrd.model.TodayNews;
import com.example.lw_pc.rrd.ui.NewActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment {
    private static final String TAG = "request_error";

    public static final int SPAN_COUNT = 1;//列数
    private Toolbar mToolBar;
    private RecyclerView mRcvNewsList;
    private SwipeRefreshLayout mSrlNewsList;
    private NewsListAdapter mNewsListAdapter;

    private TodayNews.Story story;
    public List<Extra> extra;


    public static Fragment newInstance() {
        return new NewsListFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mRcvNewsList = (RecyclerView) v.findViewById(R.id.rcv_news_list);
        mSrlNewsList = (SwipeRefreshLayout) v.findViewById(R.id.srl_news_list);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        LoadData();

    }

    private void init() {
        mToolBar.setTitle(getString(R.string.today_news));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);

        mSrlNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });

        mRcvNewsList.setLayoutManager(new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));

        mNewsListAdapter = new NewsListAdapter(getActivity(), new ArrayList<TodayNews.Story>(), new ArrayList<TodayNews.Story>(), new ArrayList<Extra>(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = mRcvNewsList.getChildAdapterPosition(v);
                if (RecyclerView.NO_POSITION != position) {
                    TodayNews.Story story = mNewsListAdapter.getItemData(position);
                    NewActivity.start(getActivity(), story);
                }
            }
        });
        mRcvNewsList.setAdapter(mNewsListAdapter);
        mRcvNewsList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
    }

    private void LoadData() {
        final List<Long> Ids = new ArrayList<>();
        Api api = ZhihuApi.getZhihuApi();
        api.getTodayNews().enqueue(new Callback<TodayNews>() {
            @Override
            public void onResponse(Response<TodayNews> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    showProgress();
                    TodayNews result = response.body();
                    mNewsListAdapter.setStories(result.getStories(), result.getTopStories());
                    hideProgress();

                  /*  for (int i = 0; i < result.getStories().size(); i++) {
                        Ids.add(result.getStories().get(i).getId());
                    }
                    for(int i = 0; i<Ids.size(); i++) {
                        Api bpi = ZhihuApi.getZhihuApi();
                        bpi.getExtra(Ids.get(i)).enqueue(new Callback<Extra>() {
                            @Override
                            public void onResponse(Response<Extra> response, Retrofit retrofit) {
                                mNewsListAdapter.addExtra(response.body());
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }

                        });
                    }*/



                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "request failed", t);
            }
        });
        mNewsListAdapter.notifyDataSetChanged();


    }


    public void showProgress() {
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                //刷新动画
                mSrlNewsList.setRefreshing(true);
            }
        });
    }

    public void hideProgress() {
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                mSrlNewsList.setRefreshing(false);
            }
        });
    }


}
