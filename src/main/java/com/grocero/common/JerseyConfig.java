package com.grocero.common;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        super(
                JacksonFeature.class
        );
        register(RolesAllowedDynamicFeature.class);
    }
}
