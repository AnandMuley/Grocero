package com.grocero.common

import com.grocero.beans.CustomerBean
import com.grocero.beans.MasterListBean
import com.grocero.builders.MasterListBeanBuilder
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
import com.grocero.shared.SharedSpecification
import org.springframework.context.ApplicationContext
import spock.lang.Subject

class DtoToBeanMapperSpec extends SharedSpecification {

    @Subject
    DtoToBeanMapper dtoToBeanMapper

    ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        dtoToBeanMapper = new DtoToBeanMapper(context: mockApplicationContext)
    }

    def "map - should map details from MasterList DTO to Bean by creating new"() {
        given: "dto to map"
        MasterListDto expected = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        expected.items.addAll(["Apple", "Tomato"])
        1 * mockApplicationContext.getBean(MasterListBean) >> new MasterListBean()

        when: "mapping function is called"
        MasterListBean actual = dtoToBeanMapper.map(expected, Optional.empty())

        then: "required details should be mapped"
        assert actual.name == expected.name
        assert actual.customerId == expected.customerId
        assert actual.items == expected.items
    }

    def "map - should map details from MasterList DTO to given Bean"() {
        given: "dto to map"
        MasterListDto expected = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        expected.items.addAll(["Apple", "Tomato", "Cabbage"])

        MasterListBean masterListBean = new MasterListBeanBuilder().id(randomId)
                .name(MASTER_LIST_NAME).customerId(randomId).items(["Cabbage"] as LinkedHashSet).build()

        when: "mapping function is called"
        MasterListBean actual = dtoToBeanMapper.map(expected, Optional.of(masterListBean))

        then: "required details should be mapped"
        assert actual.name == expected.name
        assert actual.customerId == expected.customerId
        assert actual.items == expected.items
    }

    def "map - should map details from customer dto to bean"() {
        given: "dto to map"
        CustomerDto customerDto = new CustomerDto(name: JOHN)
        1 * mockApplicationContext.getBean(CustomerBean, ['John']) >> new CustomerBean(JOHN)

        when: "mapping function is called"
        CustomerBean actual = dtoToBeanMapper.map(customerDto)

        then: "details should be mapped"
        assert actual.name == customerDto.name
        assert actual.id == customerDto.id
    }

}
