package com.nkoad.wallbler.core.definition.youtube;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Wallbler YouTube Feed")
public @interface YoutubeFeedConfig {

    @AttributeDefinition(name = "Name", description = "Unique feed name")
    String config_name() default "youtube feed";

    @AttributeDefinition(name = "Account name", description = "Linked account name")
    String config_accountName() default "youtube account";

    @AttributeDefinition(name = "Channel Id")
    String config_channelId();

    @AttributeDefinition(name = "Search query")
    String config_query();

    @AttributeDefinition(name = "Safe search", description = "Enable safe search")
    boolean config_safeSearch();

    @AttributeDefinition(name = "Is enabled", description = "It's possible to temporary disable the feed")
    boolean config_enabled() default true;

    @AttributeDefinition(name = "Delay", description = "Delay in hours")
    int config_delay() default 6;

    @AttributeDefinition(name = "Accepted by default")
    boolean config_acceptedByDefault() default true;

}
