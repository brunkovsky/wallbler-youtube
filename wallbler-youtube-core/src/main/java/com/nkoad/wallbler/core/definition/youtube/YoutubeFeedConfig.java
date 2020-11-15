package com.nkoad.wallbler.core.definition.youtube;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = "Wallbler YouTube Feed")
public @interface YoutubeFeedConfig {

    @AttributeDefinition(name = "Name", description = "Unique feed name")
    String config_name() default "youtube feed";

    @AttributeDefinition(name = "Account name", description = "Linked account name")
    String config_accountName() default "youtube account";

    @AttributeDefinition(name = "Channel Id", description = "Input Chanel's Id here to get videos from")
    String config_channelId();

    @AttributeDefinition(name = "Search query", description = "Input some string here you want to search for")
    String config_query();

    @AttributeDefinition(name = "Order", options = { @Option(label = "date", value = "date"), @Option(label = "rating", value = "rating"), @Option(label = "relevance", value = "relevance"), @Option(label = "viewCount", value = "viewCount")})
    String config_order();

    @AttributeDefinition(name = "Safe search", description = "Show only safe result")
    boolean config_safeSearch();

    @AttributeDefinition(name = "Is enabled", description = "It's possible to temporary disable the feed")
    boolean config_enabled() default true;

    @AttributeDefinition(name = "Delay", description = "Delay in hours")
    int config_delay() default 6;

    @AttributeDefinition(name = "Accepted by default")
    boolean config_acceptedByDefault() default true;

}
