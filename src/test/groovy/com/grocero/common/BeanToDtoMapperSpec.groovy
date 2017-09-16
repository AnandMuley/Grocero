package com.grocero.common

import com.grocero.beans.CustomerBean
import com.grocero.dtos.CustomerDto
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

class BeanToDtoMapperSpec extends SharedSpecification {

    @Subject
    BeanToDtoMapper beanToDtoMapper

    def setup() {
        beanToDtoMapper = new BeanToDtoMapper(
                context: mockApplicationContext
        )
    }

    def "map - should map customer bean to dto"() {
        given: "customer bean with required details"
        CustomerBean customerBean = new CustomerBean(id: randomId, name: JOHN)
        1 * mockApplicationContext.getBean(CustomerDto, randomId, JOHN) >> new CustomerDto(id: randomId, name: JOHN)

        when: "mapping function is called"
        CustomerDto actual = beanToDtoMapper.map(customerBean)

        then: "required details should be mapped"
        assert actual.id == customerBean.id
        assert actual.name == customerBean.name
    }

}
