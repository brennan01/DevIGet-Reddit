package com.brennan.deviget.redditposts.domain;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RedditDataResponse implements Serializable {

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("dist")
    @Expose
    private long dist;
    @SerializedName("children")
    @Expose
    private List<RedditChildrenResponse> children = null;
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public String getAuthorIds(){
        StringBuilder namesStr = new StringBuilder();

        for (RedditChildrenResponse currentChild : children) {
            String authorFullNameId = currentChild.getData().getAuthorFullname();
            namesStr = namesStr.length() > 0
                    ? namesStr.append(",").append(authorFullNameId)
                    : namesStr.append(authorFullNameId);
        }
        return namesStr.toString();
    }

    public void setAuthorLegibleNames(Map<String, Author> authorsMap) {
        for (RedditChildrenResponse currentChild : children) {
            currentChild.getData().setAuthorLegibleName(authorsMap.get(currentChild.getData().getAuthorFullname()).getName());
        }
    }

    public long getDist() {
        return dist;
    }

    public void setDist(long dist) {
        this.dist = dist;
    }

    public List<RedditChildrenResponse> getChildren() {
        return children;
    }

    public void setChildren(List<RedditChildrenResponse> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }


    @Override
    public String toString() {
        return "RedditDataResponse{" +
                "modhash='" + modhash + '\'' +
                ", dist=" + dist +
                ", children=" + children +
                ", after='" + after + '\'' +
                ", before=" + before +
                '}';
    }
}