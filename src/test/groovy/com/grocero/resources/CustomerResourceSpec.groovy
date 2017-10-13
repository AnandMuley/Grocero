package com.grocero.resources

import com.grocero.builders.CustomerDtoBuilder
import com.grocero.builders.MasterListDtoBuilder
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
import com.grocero.dtos.ResponseDto
import com.grocero.exceptions.CustomerServiceException
import com.grocero.exceptions.NoDataFoundException
import com.grocero.services.CustomerService
import com.grocero.shared.SharedSpecification
import spock.lang.Shared

import javax.ws.rs.core.Response

class CustomerResourceSpec extends SharedSpecification {

    @Shared
    CustomerResource customerResource

    CustomerService mockCustomerService

    def username = "Aron"
    def password = "Johnson"

    def setup() {
        mockCustomerService = Mock(CustomerService)
        customerResource = new CustomerResource(mockCustomerService)
    }

    def "getAll - should return error if no customers found"() {
        given: "customers are not registered"
        CustomerDto expected = new CustomerDtoBuilder().build()
        NoDataFoundException ex = new NoDataFoundException("No customers found")
        1 * mockCustomerService.getAll() >> {throw ex}

        when: "getAll is called"
        Response response = customerResource.getAll()


        then: "customer records are sent in response"
        response.status == 404
        response.hasEntity() == false
    }

    def "getAll - should get all customers"() {
        given: "customers are registered"
        CustomerDto expected = new CustomerDtoBuilder().build()
        1 * mockCustomerService.getAll() >> [expected]

        when: "getAll is called"
        Response response = customerResource.getAll()


        then: "customer records are sent in response"
        response.status == 200
        Set<CustomerDto> customers = response.entity
        customers.size() == 1
        CustomerDto actual = customers[0]
        actual.authToken == expected.authToken
        actual.password == expected.password
        actual.name == expected.name
        actual.id == expected.id
        actual.role == expected.role
        actual.username == expected.username
    }

    def "create - should create a customer"() {
        given: "details of the customer to be added"
        CustomerDto expected = new CustomerDtoBuilder().build()

        1 * mockCustomerService.createOrUpdate({ CustomerDto custDto ->
            custDto.username == expected.username
            custDto.role == expected.role
            custDto.id == expected.id
            custDto.name == expected.name
            custDto.password == expected.password
            custDto.authToken == expected.authToken
            true
        })

        when: "create method is invoked"
        Response response = customerResource.create(expected)

        then: "success response is received"
        response.status == 200
        CustomerDto actual = response.entity
        actual.username == expected.username
        actual.role == expected.role
        actual.id == expected.id
        actual.name == expected.name
        actual.password == expected.password
        actual.authToken == expected.authToken

    }

    def "authenticate - should return the auth token if authentication is successful"() {
        given: "username and password"
        def authToken = UUID.randomUUID().toString()
        CustomerDto customerDto = new CustomerDtoBuilder().username(username).password(password).build()
        1 * mockCustomerService.findByUsernameAndPassword(username, password) >> Optional.of(new CustomerDtoBuilder().authToken(authToken).build())

        when: "authenticate method is called"
        Response actualResponse = customerResource.authenticate(customerDto)

        then: "auth token is received in response"
        ResponseDto actual = (ResponseDto) actualResponse.getEntity()
        actual.message == authToken

    }

    def "authenticate - should return not found error"() {
        given: "username and password"

        CustomerDto customerDto = new CustomerDtoBuilder().username(username).password(password).build()
        1 * mockCustomerService.findByUsernameAndPassword(username, password) >> Optional.empty()

        when: "authenticate method is called"
        Response actualResponse = customerResource.authenticate(customerDto)

        then: "auth token is received in response"
        ResponseDto actual = (ResponseDto) actualResponse.getEntity()
        actualResponse.status == 404
        actual.message == "Record not found"

    }

    def "createMasterList - should return an error if the customer not found"() {
        given: "customerId and masterlist to save"
        def customerId = "CID202"
        def productName = "Apple"
        def listName = "Master List"
        MasterListDto expected = new MasterListDtoBuilder().id(randomId)
                .name(listName).items([productName] as LinkedHashSet).build()
        CustomerServiceException ex = new CustomerServiceException()

        1 * mockCustomerService.createOrUpdate({ MasterListDto dto ->
            assert dto.id == randomId
            assert dto.customerId == customerId
            assert dto.name == listName
            assert dto.items.size() == 1
            assert dto.items[0] == productName
            true
        }) >> { throw ex }

        when: "createMasterList is invoked"
        Response response = customerResource.createMasterList(customerId, expected)

        then: "all details are sent in response"
        response.status == 404
        ResponseDto actual = response.entity
        actual.message == ex.message
    }

    def "createMasterList - should create a masterlist"() {
        given: "customerId and masterlist to save"
        def customerId = "CID202"
        def productName = "Apple"
        def listName = "Master List"
        MasterListDto expected = new MasterListDtoBuilder().id(randomId)
                .name(listName).items([productName] as LinkedHashSet).build()

        1 * mockCustomerService.createOrUpdate({ MasterListDto dto ->
            assert dto.id == randomId
            assert dto.customerId == customerId
            assert dto.name == listName
            assert dto.items.size() == 1
            assert dto.items[0] == productName
            true
        })

        when: "createMasterList is invoked"
        Response response = customerResource.createMasterList(customerId, expected)

        then: "all details are sent in response"
        response.status == 200
        MasterListDto actual = response.entity
        actual.id == expected.id
        actual.name == expected.name
        actual.customerId == customerId
        actual.items.size() == 1
        actual.items[0] == productName
    }

    def "getMasterList - should get a masterlist"() {
        given: "id of the customer"
        def customerId = "CID202"
        def productName = "Apple"
        def listName = "Master List"
        MasterListDto expected = new MasterListDtoBuilder().id(randomId)
                .name(listName).items([productName] as LinkedHashSet).build()

        1 * mockCustomerService.getMasterList(customerId) >> expected

        when: "getMasterList is invoked"
        Response response = customerResource.getMasterList(customerId)

        then: "master list should be returned"
        response.status == 200
        MasterListDto actual = response.entity
        actual.id == expected.id
        actual.name == expected.name
        actual.customerId == expected.customerId
        actual.items.size() == 1
        actual.items[0] == productName
    }

    def "getMasterList - should return an error if no list found"() {
        given: "id of the customer"
        def customerId = "CID202"
        def productName = "Apple"
        def listName = "Master List"
        MasterListDto expected = new MasterListDtoBuilder().id(randomId)
                .name(listName).items([productName] as LinkedHashSet).build()

        1 * mockCustomerService.getMasterList(customerId) >> { throw new NoDataFoundException("No master list found") }

        when: "getMasterList is invoked"
        Response response = customerResource.getMasterList(customerId)

        then: "master list should be returned"
        response.status == 404
        response.hasEntity() == false
    }

}
