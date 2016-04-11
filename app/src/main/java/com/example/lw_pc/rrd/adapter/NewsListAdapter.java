package com.example.lw_pc.rrd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.lw_pc.rrd.R;
import com.example.lw_pc.rrd.model.Extra;
import com.example.lw_pc.rrd.model.TodayNews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by LW-PC on 2016/3/24.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TodayNews.Story> mStories;
    private List<TodayNews.Story> mHeaderStories;
    private List<Extra> mExtra;
    private Context context;
    private View.OnClickListener mListener;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public NewsListAdapter(Context context, List<TodayNews.Story> stories, List<TodayNews.Story> headerStories, List<Extra> extra, View.OnClickListener listener) {
        this.context = context;
        mStories = stories;
        mListener = listener;
        mHeaderStories = headerStories;
        mExtra = extra;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_header, parent, false);
            return new HeaderViewHolder(itemView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final TodayNews.Story story = mStories.get(position - (mHeaderStories == null || mHeaderStories.isEmpty() ? 0 : 1));
            itemViewHolder.mTvTitle.setText(story.getTitle());
            Picasso.with(context)
                    .load(story.getImageUrls().get(0))
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemViewHolder.mIvNewsThumbnail);

            itemViewHolder.itemView.setOnClickListener(mListener);

        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.mSlHeader.removeAllSliders();
            for (int i = 0; i < mHeaderStories.size(); i++) {
                final TodayNews.Story story = mHeaderStories.get(i);
                BaseSliderView baseSliderView = new BaseSliderView(context) {
                    @Override
                    public View getView() {
                        View v = LayoutInflater.from(getContext()).inflate(R.layout.slider_item, null);
                        ImageView target = (ImageView) v.findViewById(R.id.iv_slider);
                        TextView title = (TextView) v.findViewById(R.id.tv_title);
                        title.setText(getDescription());
                        bindEventAndShow(v, target);
                        return v;
                    }
                };
                baseSliderView.description(story.getTitle())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .image(story.getImageUrl());
                headerViewHolder.mSlHeader.addSlider(baseSliderView);
            }

        }
    }

    public TodayNews.Story getItemData(int position) {
        position = getItemCount() == mHeaderStories.size() ? position : position - 1;
        return getStories().get(position);
    }

    @Override
    public int getItemCount() {
        return mStories.size() + (mHeaderStories == null || mHeaderStories.isEmpty() ? 0 : 1);
    }

    public void setStories(List<TodayNews.Story> stories, List<TodayNews.Story> topStories) {
        mStories = stories;
        mHeaderStories = topStories;
        notifyDataSetChanged();
    }

    public List<TodayNews.Story> getStories() {
        return mStories;
    }

    public List<TodayNews.Story> getHeaderStories() {
        return mHeaderStories;
    }

    public void setmExtra(List<Extra> extra) {
        mExtra = extra;
    }

    public void addExtra(Extra extra) {
        mExtra.add(extra);
        notifyDataSetChanged();
    }




    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView mIvNewsThumbnail;
        public TextView mTvTitle;
        public TextView popularityNum;
        public TextView commentNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mIvNewsThumbnail = (CircleImageView) itemView.findViewById(R.id.iv_story_thumbnail);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            popularityNum = (TextView) itemView.findViewById(R.id.popularity_count);
            commentNum = (TextView) itemView.findViewById(R.id.comment_count);
        }

    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public SliderLayout mSlHeader;

        public PagerIndicator mPagerIndicator;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mSlHeader = (SliderLayout) itemView.findViewById(R.id.sl_header);
            mPagerIndicator = (PagerIndicator) itemView.findViewById(R.id.pi_header);
            mSlHeader.setCustomIndicator(mPagerIndicator);
        }

    }
}
