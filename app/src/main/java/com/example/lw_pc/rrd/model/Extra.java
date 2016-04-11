package com.example.lw_pc.rrd.model;

import java.io.Serializable;

/**
 * Created by LW-PC on 2016/3/29.
 */
public class Extra implements Serializable {

    /**
     * long_comments : 0
     * popularity : 161
     * short_comments : 19
     * comments : 19
     */

    private int long_comments;
    /** 点赞总数 */
    private int popularity;
    private int short_comments;
    /** 总评论数 */
    private int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Extra{" +
                "comments='" + comments + '\'' +

                '}';
    }
}
