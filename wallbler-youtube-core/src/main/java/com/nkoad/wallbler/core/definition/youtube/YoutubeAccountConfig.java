package com.nkoad.wallbler.core.definition.youtube;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Wallbler YouTube Account")
public @interface YoutubeAccountConfig {

    @AttributeDefinition(name = "Name", description = "Unique account name")
    String config_name() default "youtube account";

    @AttributeDefinition(name = "Api key")
    String config_apiKey() default "AIzaSyB8HHQEIaeusJAefu4XkazhrD4K5-IDTGc";

    @AttributeDefinition(name = "Is enabled", description = "It's possible to temporary disable the account")
    boolean config_enabled() default true;

    @AttributeDefinition(name = "Is valid", description = "It indicates if the account valid. You should not set this checkbox. It will set automatically")
    boolean config_valid();

}
