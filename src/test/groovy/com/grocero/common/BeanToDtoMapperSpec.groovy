package com.grocero.common

import com.grocero.beans.CustomerBean
import com.grocero.beans.MasterListBean
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
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

    def "map - should map master list bean to dto"() {
        given: "master list bean with required details"
        def customerId = "CID200"
        MasterListBean masterListBean = new MasterListBean(id: randomId, name: MASTER_LIST_NAME, customerId: customerId)
        masterListBean.items.addAll(["Apple", "Tomato"])
        1 * mockApplicationContext.getBean(MasterListDto, randomId, customerId, MASTER_LIST_NAME) >> new MasterListDto(id: randomId, customerId: customerId, name: MASTER_LIST_NAME)

        when: "mapping function is called"
        MasterListDto actual = beanToDtoMapper.map(masterListBean)

        then: "required details should be mapped"
        assert actual.id == masterListBean.id
        assert actual.name == masterListBean.name
        assert actual.customerId == masterListBean.customerId
        assert actual.items == masterListBean.items
    }

}
