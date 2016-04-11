package com.example.lw_pc.rrd.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lw_pc.rrd.R;
import com.example.lw_pc.rrd.core.Api;
import com.example.lw_pc.rrd.core.ZhihuApi;
import com.example.lw_pc.rrd.model.StartImage;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Url;

public class StartActivity extends AppCompatActivity {
    private ImageView startImage;
    private static final float SCALE_END = 1.13F;
    private static final int ANIMATION_DURATION = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startImage = (ImageView) findViewById(R.id.start_image);

        Api api = ZhihuApi.getZhihuApi();
        api.getStartImage().enqueue(new Callback<StartImage>() {
            @Override
            public void onResponse(Response<StartImage> response, Retrofit retrofit) {
                StartImage result = response.body();
                Picasso.with(StartActivity.this)
                        .load(result.getImg())
                        .into(startImage);
                animateImage();
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });

    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(startImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(startImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        });
    }
}
