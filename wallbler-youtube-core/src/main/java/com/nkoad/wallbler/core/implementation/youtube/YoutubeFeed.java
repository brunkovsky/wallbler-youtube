package com.nkoad.wallbler.core.implementation.youtube;

import com.nkoad.wallbler.cache.definition.Cache;
import com.nkoad.wallbler.core.Feed;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;

import java.util.Map;

@Component
@Designate(ocd = com.nkoad.wallbler.core.definition.youtube.YoutubeFeedConfig.class, factory = true)
public class YoutubeFeed extends Feed {
    @Reference
    private Cache cache;

    @Override
    public void assignConnector(Map<String, Object> feedProperties, Map<String, Object> accountProperties) {
        connector = new YoutubeConnector(feedProperties, accountProperties, cache);
    }

    @Activate
    public void activate(Map<String, Object> properties) {
        super.activate(properties);
    }

    @Modified
    public void modified(Map<String, Object> properties) {
        String socialMediaType = ((String) properties.get("service.pid")).split("\\.")[5];
        String feedName = (String) properties.get("config.name");
        cache.deletePostsByFeedName(socialMediaType, feedName);
        super.modified(properties);
    }

    @Deactivate
    public void deactivate(Map<String, Object> properties) {
        super.deactivate(properties);
    }

}
