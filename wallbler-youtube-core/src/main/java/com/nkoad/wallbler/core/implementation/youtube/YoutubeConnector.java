package com.nkoad.wallbler.core.implementation.youtube;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.nkoad.wallbler.cache.definition.Cache;
import com.nkoad.wallbler.core.Connector;
import com.nkoad.wallbler.core.WallblerItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class YoutubeConnector extends Connector<YoutubeValidator> {
    private static transient Logger LOGGER = LoggerFactory.getLogger(YoutubeConnector.class);
    private static final String PART_PARAM = "id,snippet";
    private static final String YT_PUBLIC_URL = "https://www.youtube.com";
    private static final String YT_CHANNEL = "/channel/";
    private static final String YT_VIDEO = "/watch?v=";

    public YoutubeConnector(Map<String, Object> feedProperties, Map<String, Object> accountProperties, Cache cache) {
        super(feedProperties, accountProperties, cache);
        validator = new YoutubeValidator(accountProperties);
    }

    @Override
    public void loadData() {
        try {
            List<SearchResult> searchResultList = validator.youtube.search()
                    .list(PART_PARAM)
                    .setKey(getAccountPropertyAsString("config.apiKey")) // Set your developer key from the {{ Google Cloud Console }} for non-authenticated requests. See:{{ https://cloud.google.com/console }}
                    .setQ(getFeedPropertyAsString("config.query"))// Prompt the user to enter a query term.
                    .setChannelId((getFeedPropertyAsString("config.channelId")).isEmpty() ? null : getFeedPropertyAsString("config.channelId"))
                    .setType("video")// Restrict the search results to only include videos. See: https://developers.google.com/youtube/v3/docs/search/list#type
                    .setVideoType("any")//Allowed values: [any, episode, movie]
                    .setSafeSearch((Boolean) (feedProperties.get("config.safeSearch")) ? "strict" : "none")
                    .setOrder(getFeedPropertyAsString("config.order"))
                    .setFields("items(id/videoId,snippet/title,snippet/channelTitle,snippet/description,snippet/thumbnails/medium/url,snippet/thumbnails/high/url,snippet/channelId,snippet/publishedAt)")
                    //.setForMine(true);// To increase efficiency, only retrieve the fields that the application uses.
                    .setMaxResults(30L)
                    .execute()// Call the API and print results.
                    .getItems();

            Set<WallblerItem> dataMediaItemList = new HashSet<>();
            Map<String, YoutubeWallblerItem> videoIdMap = new HashMap<>();

            searchResultList.forEach(item -> {
                YoutubeWallblerItem youtubeMediaItem = new YoutubeWallblerItem(feedProperties);
                youtubeMediaItem.setTitle(item.getSnippet().getTitle());
                youtubeMediaItem.setDescription(item.getSnippet().getDescription());
                youtubeMediaItem.setLinkToSMPage(YT_PUBLIC_URL + YT_VIDEO + item.getId().getVideoId());
                youtubeMediaItem.setVideoId(item.getId().getVideoId());
                youtubeMediaItem.setThumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl());
                youtubeMediaItem.setUrl(YT_PUBLIC_URL + YT_CHANNEL + item.getSnippet().getChannelId());
                youtubeMediaItem.setChannelTitle(item.getSnippet().getChannelTitle());
                youtubeMediaItem.setDate(new Date(item.getSnippet().getPublishedAt().getValue()));
                dataMediaItemList.add(youtubeMediaItem);
                videoIdMap.put(item.getId().getVideoId(), youtubeMediaItem);
            });

            List<Video> videoInfoList = validator.youtube.videos()
                    .list(PART_PARAM + ",statistics")
                    .setKey(getAccountPropertyAsString("config.apiKey"))
                    .setId(videoIdMap.keySet().toString().substring(1, videoIdMap.keySet().toString().length() - 1).replace(" ", ""))
                    .setMaxResults(30L)
                    .setFields("items(id,statistics/viewCount,statistics/likeCount,statistics/dislikeCount,statistics/commentCount)")
                    .execute()// Call the API and print results.
                    .getItems();

            videoInfoList.forEach(item -> {
                YoutubeWallblerItem youtubeMediaItem = videoIdMap.get(item.getId());
                youtubeMediaItem.setViewCount(item.getStatistics().getViewCount());
                BigInteger likeCount = item.getStatistics().getLikeCount();
                if (null != likeCount)
                    youtubeMediaItem.setLikedCount(likeCount);
                youtubeMediaItem.setDislikeCount(item.getStatistics().getDislikeCount());
                BigInteger commentCount = item.getStatistics().getCommentCount();
                if (null != commentCount)
                    youtubeMediaItem.setCommentsCount(commentCount);
            });
            cache.add(dataMediaItemList);
        } catch (GoogleJsonResponseException e) {
            LOGGER.error("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (IOException e) {
            LOGGER.error("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
        }
    }

}
