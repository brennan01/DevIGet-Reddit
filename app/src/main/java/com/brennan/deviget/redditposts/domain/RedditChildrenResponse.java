package com.brennan.deviget.redditposts.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RedditChildrenResponse implements Serializable {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private RedditNewsDataResponse data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RedditNewsDataResponse getData() {
        return data;
    }

    public void setData(RedditNewsDataResponse data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditChildrenResponse{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}