package com.brennan.deviget.redditposts.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RedditNewsDataResponse implements Serializable {

    @SerializedName("author_fullname")
    @Expose
    private String authorFullname;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("thumbnail_width")
    @Expose
    private long thumbnailWidth;
    @SerializedName("thumbnail_height")
    @Expose
    private long thumbnailHeight;
    @SerializedName("num_comments")
    @Expose
    private long numComments;

    public String getAuthorFullname() {
        return authorFullname;
    }

    public void setAuthorFullname(String authorFullname) {
        this.authorFullname = authorFullname;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(long thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public long getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(long thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public long getNumComments() {
        return numComments;
    }

    public void setNumComments(long numComments) {
        this.numComments = numComments;
    }


    @Override
    public String toString() {
        return "RedditNewsDataResponse{" +
                "authorFullname='" + authorFullname + '\'' +
                ", created=" + created +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailWidth=" + thumbnailWidth +
                ", thumbnailHeight=" + thumbnailHeight +
                ", numComments=" + numComments +
                '}';
    }
}
