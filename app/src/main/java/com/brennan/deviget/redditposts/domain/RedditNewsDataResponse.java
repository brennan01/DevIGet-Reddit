package com.brennan.deviget.redditposts.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class RedditNewsDataResponse implements Serializable {

    @SerializedName("author_fullname")
    @Expose
    private String authorFullname;
    @SerializedName("title")
    @Expose
    private String title;
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
    private String authorLegibleName;

    public String getAuthorLegibleName() {
        return authorLegibleName;
    }

    public void setAuthorLegibleName(String authorLegibleName) {
        this.authorLegibleName = authorLegibleName;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedditNewsDataResponse that = (RedditNewsDataResponse) o;
        return created == that.created &&
                thumbnailWidth == that.thumbnailWidth &&
                thumbnailHeight == that.thumbnailHeight &&
                numComments == that.numComments &&
                Objects.equals(authorFullname, that.authorFullname) &&
                Objects.equals(title, that.title) &&
                Objects.equals(thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorFullname, title, created, thumbnail, thumbnailWidth, thumbnailHeight, numComments);
    }

    @Override
    public String toString() {
        return "RedditNewsDataResponse{" +
                "authorFullname='" + authorFullname + '\'' +
                ", title='" + title + '\'' +
                ", created=" + created +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailWidth=" + thumbnailWidth +
                ", thumbnailHeight=" + thumbnailHeight +
                ", numComments=" + numComments +
                '}';
    }
}
