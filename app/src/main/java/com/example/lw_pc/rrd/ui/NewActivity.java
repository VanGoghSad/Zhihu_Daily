package com.example.lw_pc.rrd.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lw_pc.rrd.R;
import com.example.lw_pc.rrd.model.TodayNews;
import com.example.lw_pc.rrd.ui.fragment.NewsDetailFragment;

public class NewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        TodayNews.Story story = (TodayNews.Story) getIntent().getSerializableExtra(NewsDetailFragment.STORY);
        getSupportFragmentManager().beginTransaction().add(R.id.news_Container, NewsDetailFragment.newInstance(story)).commit();
    }

    public static void start(Context context, TodayNews.Story story) {
        Intent intent = new Intent(context, NewActivity.class);
        intent.putExtra(NewsDetailFragment.STORY, story);
        context.startActivity(intent);
    }


    /** 返回图标点击事件 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
