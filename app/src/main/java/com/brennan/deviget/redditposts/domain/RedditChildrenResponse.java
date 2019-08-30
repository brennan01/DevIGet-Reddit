package com.brennan.deviget.redditposts.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedditChildrenResponse that = (RedditChildrenResponse) o;
        return Objects.equals(kind, that.kind) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, data);
    }

    @Override
    public String toString() {
        return "RedditChildrenResponse{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}