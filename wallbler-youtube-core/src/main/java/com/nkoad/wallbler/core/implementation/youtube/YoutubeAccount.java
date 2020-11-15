package com.nkoad.wallbler.core.implementation.youtube;

import com.nkoad.wallbler.core.Account;
import com.nkoad.wallbler.core.Validator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import java.util.Map;

@Component
@Designate(ocd = com.nkoad.wallbler.core.definition.youtube.YoutubeAccountConfig.class, factory = true)
public class YoutubeAccount extends Account<Validator> {

    @Override
    public void assignValidator(Map<String, Object> properties) {
        validator = new YoutubeValidator(properties);
    }

    @Activate
    public void activate(Map<String, Object> properties) {
        super.activate(properties);
        setValid(properties);
    }

    @Modified
    public void modified(Map<String, Object> properties) {
        super.modified(properties);
    }

    @Deactivate
    public void deactivate(Map<String, Object> properties) {
        super.deactivate(properties);
    }

}
