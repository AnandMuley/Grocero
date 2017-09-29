package com.grocero.common

import com.grocero.shared.SharedSpecification
import org.glassfish.jersey.jackson.JacksonFeature
import spock.lang.Subject

class JerseyConfigSpec extends SharedSpecification {

    @Subject
    JerseyConfig jerseyConfig

    def setup() {
        jerseyConfig = new JerseyConfig()
    }

    def "should create config object with required configurations"() {
        expect: "the required configurations are set"
        jerseyConfig.classes.size() == 1
        jerseyConfig.classes[0] == JacksonFeature
    }

}
