package com.example.lw_pc.rrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LW-PC on 2016/3/27.
 */
public class StartImage implements Serializable{

    /**
     * text : © Fido Dido
     * img : http://p2.zhimg.com/10/7b/107bb4894b46d75a892da6fa80ef504a.jpg
     */
    @SerializedName("text")
    private String text;

    @SerializedName("img")
    private String img;

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }


    @Override
    public String toString() {
        return "StartImage{" +
                "mText='" + text + '\'' +
                ", url='" + img + '\'' +
                '}';
    }
}
