package com.grocero.resources

import com.grocero.builders.MasterListDtoBuilder
import com.grocero.dtos.MasterListDto
import com.grocero.exceptions.NoDataFoundException
import com.grocero.services.CustomerService
import com.grocero.shared.SharedSpecification
import spock.lang.Shared

import javax.ws.rs.core.Response

class CustomerResourceSpec extends SharedSpecification {

    @Shared
    CustomerResource customerResource

    CustomerService mockCustomerService

    def setup() {
        mockCustomerService = Mock(CustomerService)
        customerResource = new CustomerResource(mockCustomerService)
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

        1 * mockCustomerService.getMasterList(customerId) >> {throw new NoDataFoundException("No master list found")}

        when: "getMasterList is invoked"
        Response response = customerResource.getMasterList(customerId)

        then: "master list should be returned"
        response.status == 404
        response.hasEntity() == false
    }

}
