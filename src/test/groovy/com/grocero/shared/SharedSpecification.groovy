package com.grocero.shared

import spock.lang.Specification

class SharedSpecification extends Specification{

    protected String randomId = UUID.randomUUID().toString()

    def setup(){
        enableStrictMock()
    }

    def enableStrictMock(){
        0 * _
    }

}
