package com.grocero.shared

import com.grocero.common.DtoToBeanMapper
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class SharedSpecification extends Specification {

    protected String randomId = UUID.randomUUID().toString()

    protected DtoToBeanMapper mockDtoToBeanMapper

    protected final String JOHN = "John"

    protected ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        mockDtoToBeanMapper = Mock(DtoToBeanMapper)
        enableStrictMock()
    }

    def enableStrictMock() {
        0 * _
    }

}
