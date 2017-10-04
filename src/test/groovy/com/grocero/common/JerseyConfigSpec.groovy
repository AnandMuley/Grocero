package com.grocero.common

import com.grocero.shared.SharedSpecification
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature
import spock.lang.Subject

class JerseyConfigSpec extends SharedSpecification {

    @Subject
    JerseyConfig jerseyConfig

    def setup() {
        jerseyConfig = new JerseyConfig()
    }

    def "should create config object with required configurations"() {
        expect: "the required configurations are set"
        jerseyConfig.classes.size() == 2
        jerseyConfig.classes.contains(JacksonFeature) == true
        jerseyConfig.classes.contains(RolesAllowedDynamicFeature) == true
    }

}
