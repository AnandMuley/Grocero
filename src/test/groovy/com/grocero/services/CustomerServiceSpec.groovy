package com.grocero.services

import com.grocero.beans.CustomerBean
import com.grocero.beans.MasterListBean
import com.grocero.builders.CustomerBeanBuilder
import com.grocero.builders.CustomerDtoBuilder
import com.grocero.builders.MasterListBeanBuilder
import com.grocero.builders.MasterListDtoBuilder
import com.grocero.common.UserRoles
import com.grocero.dtos.AuthenticationDto
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
import com.grocero.exceptions.CustomerDoesNotExistException
import com.grocero.exceptions.NoDataFoundException
import com.grocero.repositories.CustomerRepository
import com.grocero.repositories.MasterListRepository
import com.grocero.services.impl.CustomerServiceImpl
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

import java.time.LocalDateTime

class CustomerServiceSpec extends SharedSpecification {

    @Subject
    CustomerServiceImpl customerService

    CustomerRepository mockCustomerRepository

    MasterListRepository mockMasterListRepository

    def customerId = "CID201"
    def customerName = "John"
    def username = "Aron"
    def password = "Password123"

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

    def "authenticate - should return empty optional if session is not valid"() {
        given: "username and authToken"
        def authToken = UUID.randomUUID().toString()
        LocalDateTime validTill = LocalDateTime.now().plusMinutes(-1)
        CustomerBean customerBean = new CustomerBeanBuilder().authTokenValidTill(validTill).build()

        1 * mockCustomerRepository.findOneByUsernameAndAuthToken(username, authToken) >> customerBean

        when: "authenticate is called"
        Optional<AuthenticationDto> authenticationDtoOpt = customerService.authenticate(username, authToken)

        then: "auth details are sent in response"
        authenticationDtoOpt.isPresent() == false

    }

    def "authenticate - should authenticate if the user session is valid"() {
        given: "username and authToken"
        def authToken = UUID.randomUUID().toString()
        LocalDateTime validTill = LocalDateTime.now().plusMinutes(2)
        CustomerBean customerBean = new CustomerBeanBuilder().authTokenValidTill(validTill).build()

        1 * mockCustomerRepository.findOneByUsernameAndAuthToken(username, authToken) >> customerBean
        1 * mockBeanToDtoMapper.map({ CustomerBean bean ->
            bean.authToken == customerBean.authToken
            bean.username == customerBean.username
            bean.id == customerBean.id
            bean.name == customerBean.name
            bean.password == customerBean.password
            bean.role == customerBean.role
            true
        }) >> new CustomerDtoBuilder().username(username).authToken(authToken).role(UserRoles.ADMIN).build()

        when: "authenticate is called"
        Optional<AuthenticationDto> authenticationDtoOpt = customerService.authenticate(username, authToken)

        then: "auth details are sent in response"
        AuthenticationDto actual = authenticationDtoOpt.get()
        actual.username == username
        actual.authToken == authToken
        actual.role == UserRoles.ADMIN

    }

    def "findByUsernameAndPassword - return empty optional if the customer not found"() {
        given: "username and password of the customer"
        1 * mockCustomerRepository.findOneByUsernameAndPassword(username, password) >> null

        when: "findByUsernameAndPassword is called"
        Optional<CustomerDto> actualOpt = customerService.findByUsernameAndPassword(username, password)

        then: "customer is returned"
        actualOpt.isPresent() == false
    }

    def "findByUsernameAndPassword - should find a customer by username and password"() {
        given: "username and password of the customer"
        CustomerBean expected = new CustomerBeanBuilder().build()

        1 * mockCustomerRepository.findOneByUsernameAndPassword(username, password) >> expected
        1 * mockCustomerRepository.save({ CustomerBean toBeSaved ->
            assert toBeSaved.authToken != null
            assert toBeSaved.authTokenValidTill != null
            true
        }) >> expected
        CustomerDto expectedCustDto = new CustomerDtoBuilder().build()
        1 * mockBeanToDtoMapper.map({ CustomerBean bean ->
            assert bean.id == expected.id
            assert bean.name == expected.name
            assert bean.password == expected.password
            assert bean.role == expected.role
            assert bean.username == expected.username
            assert bean.authToken != null
            true
        }) >> expectedCustDto

        when: "findByUsernameAndPassword is called"
        Optional<CustomerDto> actualOpt = customerService.findByUsernameAndPassword(username, password)

        then: "customer is returned"
        CustomerDto actual = actualOpt.get()
        actual.id == expectedCustDto.id
        actual.username == expectedCustDto.username
        actual.role == expectedCustDto.role
        actual.authToken == expectedCustDto.authToken
        actual.password == expectedCustDto.password

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

        when: "createOrUpdate operation is performed"
        customerService.createOrUpdate(customerDto)

        then: "details should be saved and the id property should be populated"
        assert customerDto.id == randomId
    }

    def "save - should save the masterlist in the database"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        MasterListBean masterListBean = new MasterListBean(id: randomId)
        1 * mockMasterListRepository.findOneByCustomerId(masterListDto.customerId) >> null
        1 * mockCustomerRepository.findOne(randomId) >> new CustomerBean(randomId)
        1 * mockDtoToBeanMapper.map(masterListDto, Optional.empty()) >> masterListBean
        1 * mockMasterListRepository.save({ MasterListBean it ->
            assert it.id == randomId
            assert it.customerId == null
            assert it.name == null
            true
        }) >> masterListBean

        when: "createOrUpdate operation is performed"
        customerService.createOrUpdate(masterListDto)

        then: "details should be saved and the id property should be populated"
        assert masterListDto.id == randomId
    }

    def "save - should not save the master list if the customer does not exist"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        1 * mockCustomerRepository.findOne(randomId) >> null

        when: "createOrUpdate operation is performed"
        customerService.createOrUpdate(masterListDto)

        then: "exception should be thrown"
        def ex = thrown(CustomerDoesNotExistException)
        assert ex.message == "Customer does not exist"
    }

    def "save - should update the master list if exists"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDtoBuilder()
                .id(randomId).customerId(randomId).name(MASTER_LIST_NAME)
                .items(["Apple", "Mango"] as LinkedHashSet).build()
        MasterListBean masterListBeanInDB = new MasterListBeanBuilder().id(randomId).customerId(randomId)
                .name("Master List Name from DB").items(["Apple", "Mango"] as LinkedHashSet).build()

        1 * mockCustomerRepository.findOne(randomId) >> new CustomerBean(id: masterListDto.customerId)
        1 * mockMasterListRepository.findOneByCustomerId(masterListDto.customerId) >> masterListBeanInDB
        1 * mockDtoToBeanMapper.map(masterListDto, { Optional opt ->
            assert opt.isPresent()
            true
        }) >> masterListBeanInDB

        when: "createOrUpdate operation is performed"
        customerService.createOrUpdate(masterListDto)

        then: "details should be updated"
        1 * mockMasterListRepository.save({ MasterListBean bean ->
            assert bean.customerId == masterListBeanInDB.customerId
            assert bean.id == masterListBeanInDB.id
            assert bean.name == "Master List Name from DB"
            assert bean.items.size() == 2
            true
        }) >> masterListBeanInDB

    }

    def "getMasterList - should return the masterlist for a customerid"() {
        given: "customerid"
        String customerId = randomId
        MasterListDto masterListDto = new MasterListDto(randomId, customerId, MASTER_LIST_NAME)
        MasterListBean masterListBean = new MasterListBeanBuilder().id(randomId)
                .customerId(customerId).name("Master List").build()
        1 * mockBeanToDtoMapper.map(masterListBean) >> masterListDto
        1 * mockMasterListRepository.findOneByCustomerId(customerId) >> masterListBean

        when: "createOrUpdate operation is performed"
        MasterListDto actual = customerService.getMasterList(customerId)

        then: "details should be saved and the id property should be populated"
        actual.id == masterListBean.id
        actual.name == MASTER_LIST_NAME
        actual.customerId == customerId
        actual.items.size() == 0
    }

    def "getMasterList - should throw exception if no data found"() {
        given: "customerid"
        String customerId = randomId
        1 * mockMasterListRepository.findOneByCustomerId(customerId) >> null

        when: "createOrUpdate operation is performed"
        customerService.getMasterList(customerId)

        then: "details should be saved and the id property should be populated"
        def ex = thrown(NoDataFoundException)
        ex.message == "No master lists found"
    }

    def "getAll - should fetch all customers"() {
        given: "customers are registered"

        List<CustomerBean> customersFromDB = [new CustomerBeanBuilder()
                                                      .id(customerId).name(customerName)
                                                      .build()]
        CustomerDto customerDto = new CustomerDtoBuilder().id(customerId).name(customerName).build()
        1 * mockCustomerRepository.findAll() >> customersFromDB
        1 * mockBeanToDtoMapper.map({ CustomerBean bean ->
            assert customersFromDB.find { it.id == bean.id } != null
            true
        }) >> customerDto

        when: "getAll is called"
        Set<CustomerDto> customers = customerService.getAll()

        then: "all details are fetched"
        customers.size() == 1
    }

    def "getAll - should throw no data found exception if none found"() {
        given: "customers are registered"
        CustomerDto customerDto = new CustomerDtoBuilder().id(customerId).name(customerName).build()
        1 * mockCustomerRepository.findAll() >> customersFromDB

        when: "getAll is called"
        customerService.getAll()

        then: "all details are fetched"
        def ex = thrown(NoDataFoundException)
        ex.message == "No customers found"

        where: "possible scenarios"
        customersFromDB | _
        []              | _
        null            | _
    }

}
