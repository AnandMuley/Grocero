package com.grocero.shared

import com.grocero.common.DtoToBeanMapper
import spock.lang.Specification

class SharedSpecification extends Specification{

    protected String randomId = UUID.randomUUID().toString()

    protected DtoToBeanMapper mockDtoToBeanMapper

    def setup(){
        mockDtoToBeanMapper = Mock(DtoToBeanMapper)
        enableStrictMock()
    }

    def enableStrictMock(){
        0 * _
    }

}
