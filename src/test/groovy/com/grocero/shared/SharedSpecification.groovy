package com.grocero.shared

import com.grocero.common.BeanToDtoMapper
import com.grocero.common.DtoToBeanMapper
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class SharedSpecification extends Specification {

    protected String randomId = UUID.randomUUID().toString()

    protected DtoToBeanMapper mockDtoToBeanMapper

    protected BeanToDtoMapper mockBeanToDtoMapper

    protected final String JOHN = "John"

    protected static final String MASTER_LIST_NAME = "Vegetables Master List"

    protected ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        mockDtoToBeanMapper = Mock(DtoToBeanMapper)
        mockBeanToDtoMapper = Mock(BeanToDtoMapper)
        enableStrictMock()
    }

    def enableStrictMock() {
        0 * _
    }

}
