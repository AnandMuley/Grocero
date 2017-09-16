package com.grocero.services

import com.grocero.beans.CustomerBean
import com.grocero.dtos.CustomerDto
import com.grocero.repositories.CustomerRepository
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

class CustomerServiceSpec extends SharedSpecification{

    @Subject
    CustomerService customerService

    CustomerRepository mockCustomerRepository

    def setup(){
        mockCustomerRepository = Mock(CustomerRepository)
        customerService = new CustomerService(
                customerRepository: mockCustomerRepository,
                dtoToBeanMapper: mockDtoToBeanMapper
        )
    }

    def "save - should save the customer in the database"() {
        given: "details to be saved"
        CustomerDto customerDto = new CustomerDto(name: "John")
        CustomerBean customerBean = new CustomerBean(id: randomId)
        1 * mockDtoToBeanMapper.map(customerDto) >> customerBean
        1 * mockCustomerRepository.save({ CustomerBean it ->
            assert it.id == randomId
            assert it.name == null
            true
        }) >> customerBean

        when: "save operation is performed"
        customerService.save(customerDto)

        then: "details should be saved and the id property should be populated"
        assert customerDto.id == randomId
    }

}
