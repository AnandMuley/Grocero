package com.grocero.common

import com.grocero.resources.CustomerResource
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.test.JerseyTest

import javax.ws.rs.core.Application

class JerseySpec extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config  = new ResourceConfig(CustomerResource.class);
        return config;
    }



}
