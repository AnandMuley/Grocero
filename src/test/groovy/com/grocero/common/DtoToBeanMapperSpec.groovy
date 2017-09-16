package com.grocero.common

import com.grocero.beans.CustomerBean
import com.grocero.beans.MasterListBean
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
import com.grocero.shared.SharedSpecification
import org.springframework.context.ApplicationContext
import spock.lang.Subject

class DtoToBeanMapperSpec extends SharedSpecification {

    public static final String JOHN = "John"

    @Subject
    DtoToBeanMapper dtoToBeanMapper

    ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        dtoToBeanMapper = new DtoToBeanMapper(context: mockApplicationContext)
    }

    def "map - should map details from MasterListDTO to MasterListBean"() {
        given: "dto to map"
        def customerName = JOHN
        MasterListDto expected = new MasterListDto(randomId, customerName)
        1 * mockApplicationContext.getBean(MasterListBean) >> new MasterListBean()

        when: "mapping function is called"
        MasterListBean actual = dtoToBeanMapper.map(expected)

        then: "required details should be mapped"
        assert actual.name == expected.name
        assert actual.customerId == expected.customerId
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
