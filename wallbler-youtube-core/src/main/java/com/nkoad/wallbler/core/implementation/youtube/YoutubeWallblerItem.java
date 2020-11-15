package com.nkoad.wallbler.core.implementation.youtube;

import com.nkoad.wallbler.core.WallblerItem;

import java.math.BigInteger;
import java.util.Map;

public class YoutubeWallblerItem extends WallblerItem {
    private BigInteger likedCount;
    private BigInteger viewCount;
    private BigInteger dislikeCount;
    private BigInteger favoriteCount;
    private BigInteger commentsCount;
    private String thumbnailUrl;
    private String channelTitle;
    private String videoId;

    public YoutubeWallblerItem(Map<String, Object> feedProperties) {
        super(feedProperties);
    }

    public BigInteger getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(BigInteger likedCount) {
        this.likedCount = likedCount;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    public BigInteger getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(BigInteger dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public BigInteger getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(BigInteger favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public BigInteger getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(BigInteger commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
