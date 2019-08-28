package com.brennan.deviget.redditposts.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RedditNewsResponse implements Serializable {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private RedditDataResponse data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RedditDataResponse getData() {
        return data;
    }

    public void setData(RedditDataResponse data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditNewsResponse{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}
