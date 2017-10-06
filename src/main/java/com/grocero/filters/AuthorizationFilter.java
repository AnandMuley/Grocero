package com.grocero.filters;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements DynamicFeature {

    @Autowired
    private RolesAllowedDynamicFeature rolesAllowedDynamicFeature;

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext configuration) {
        rolesAllowedDynamicFeature.configure(resourceInfo, configuration);
    }
}
