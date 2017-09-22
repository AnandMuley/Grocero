package com.grocero.services

import com.grocero.beans.CustomerBean
import com.grocero.beans.MasterListBean
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
import com.grocero.exceptions.CustomerDoesNotExistException
import com.grocero.exceptions.DuplicateMasterListException
import com.grocero.repositories.CustomerRepository
import com.grocero.repositories.MasterListRepository
import com.grocero.services.impl.CustomerServiceImpl
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

class CustomerServiceSpec extends SharedSpecification {

    @Subject
    CustomerService customerService

    CustomerRepository mockCustomerRepository

    MasterListRepository mockMasterListRepository

    def setup() {
        mockMasterListRepository = Mock(MasterListRepository)
        mockCustomerRepository = Mock(CustomerRepository)
        customerService = new CustomerServiceImpl(
                customerRepository: mockCustomerRepository,
                dtoToBeanMapper: mockDtoToBeanMapper,
                masterListRepository: mockMasterListRepository,
                beanToDtoMapper: mockBeanToDtoMapper
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

    def "save - should save the masterlist in the database"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        MasterListBean masterListBean = new MasterListBean(id: randomId)
        1 * mockMasterListRepository.findOneByCustomerId(masterListDto.customerId) >> null
        1 * mockCustomerRepository.findOne(randomId) >> new CustomerBean(randomId)
        1 * mockDtoToBeanMapper.map(masterListDto) >> masterListBean
        1 * mockMasterListRepository.save({ MasterListBean it ->
            assert it.id == randomId
            assert it.customerId == null
            assert it.name == null
            true
        }) >> masterListBean

        when: "save operation is performed"
        customerService.save(masterListDto)

        then: "details should be saved and the id property should be populated"
        assert masterListDto.id == randomId
    }

    def "save - should throw exception and not save the master list"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        1 * mockCustomerRepository.findOne(randomId) >> null

        when: "save operation is performed"
        customerService.save(masterListDto)

        then: "details should be saved and the id property should be populated"
        def ex = thrown(CustomerDoesNotExistException)
        assert ex.message == "Customer does not exist"
    }

    def "save - should create only one master list per customer"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        1 * mockCustomerRepository.findOne(randomId) >> new CustomerBean(id: masterListDto.customerId)
        1 * mockMasterListRepository.findOneByCustomerId(masterListDto.customerId) >> new MasterListBean(id: randomId,customerId: masterListDto.customerId,name: "Master List")

        when: "save operation is performed"
        customerService.save(masterListDto)

        then: "details should be saved and the id property should be populated"
        def ex = thrown(DuplicateMasterListException)
        assert ex.message == "Master list already exists"
    }

    def "getMasterLists - should return the masterlists for a customerid"() {
        given: "customerid"
        String customerId = randomId
        MasterListDto masterListDto = new MasterListDto(randomId, customerId, MASTER_LIST_NAME)
        MasterListBean masterListBean = new MasterListBean(id: randomId)
        1 * mockBeanToDtoMapper.map(masterListBean) >> masterListDto
        1 * mockMasterListRepository.findByCustomerId(customerId) >> [masterListBean]

        when: "save operation is performed"
        Set<MasterListDto> actual = customerService.getMasterLists(customerId)

        then: "details should be saved and the id property should be populated"
        assert actual.size() == 1
        MasterListDto actualDto = actual[0]
        assert actualDto.customerId == customerId
        assert actualDto.name == MASTER_LIST_NAME
    }

}
