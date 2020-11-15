package com.nkoad.wallbler.core.implementation.youtube;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.nkoad.wallbler.core.Validator;

import java.util.Map;

public class YoutubeValidator extends Validator {
    protected YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
            .setApplicationName("youtube").build();

    public YoutubeValidator(Map<String, Object> properties) {
        super(properties);
    }

    @Override
    public boolean isAccountValid() {
        try {
            SearchListResponse searchResponse = youtube.search()
                    .list("id,snippet")
                    .setKey((String) accountProperties.get("config.apiKey"))
                    .setMaxResults(2L)
                    .execute();
            if (searchResponse == null || searchResponse.getItems().size() == 0) {
                LOGGER.warn("youtube account is not valid1. account name: " + accountProperties.get("config.name"));
                return false;
            }
        } catch (Exception e) {
            LOGGER.warn("youtube account is not valid2. account name: " + accountProperties.get("config.name"));
            return false;
        }
        LOGGER.info("youtube account is valid. account name: " + accountProperties.get("config.name"));
        return true;
    }

}
