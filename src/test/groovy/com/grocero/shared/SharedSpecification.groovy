package com.grocero.shared

import spock.lang.Specification

class SharedSpecification extends Specification{

    def setup(){
        enableStrictMock()
    }

    def enableStrictMock(){
        0 * _
    }

}
