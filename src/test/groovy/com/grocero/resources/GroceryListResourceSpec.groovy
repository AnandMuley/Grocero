package com.grocero.resources

import com.grocero.builders.GroceryListDtoBuilder
import com.grocero.dtos.GroceryListDto
import com.grocero.exceptions.NoDataFoundException
import com.grocero.services.GroceryListService
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo

class GroceryListResourceSpec extends SharedSpecification {

    @Subject
    GroceryListResource groceryListResource

    GroceryListService mockGroceryListService

    UriInfo mockUriInfo

    UriBuilder mockUriBuilder
    URI uri = new URI()
    def customerId = "CID2020"

    def setup() {
        mockUriBuilder = Mock(UriBuilder)
        mockUriInfo = Mock(UriInfo)
        mockGroceryListService = Mock(GroceryListService)
        groceryListResource = new GroceryListResource(customerId,mockUriInfo)
        groceryListResource.groceryListService = mockGroceryListService
    }

    def "create - should create a list"() {
        given: "list to be created"
        def listId = "LID201"
        GroceryListDto dto = new GroceryListDtoBuilder().build()
        1 * mockGroceryListService.create({ GroceryListDto it ->
            assert it.id == null
            it.id = listId
            true
        })

        1 * mockUriInfo.getAbsolutePathBuilder() >> mockUriBuilder
        1 * mockUriBuilder.path(listId) >> mockUriBuilder
        1 * mockUriBuilder.build() >> uri

        when: "create is invoked"
        Response actual = groceryListResource.create(dto)

        then: "response should contain required details"
        actual.status == 201
    }

    def "update - should update a list"() {
        given: "list to be updated"
        def listId = randomId
        def listName = generateRandomId()
        GroceryListDto dto = new GroceryListDtoBuilder().name(listName).build()
        1 * mockGroceryListService.update({ GroceryListDto it ->
            assert it.id == listId
            assert it.items == null
            assert it.name == listName
            true
        })

        when: "update is invoked"
        Response actual = groceryListResource.update(listId, dto)

        then: "response should contain required details"
        actual.status == 200
    }

    def "fetchAll - should fetchAll lists"() {
        given: "lists are fetched"
        def listId = randomId
        def listName = "Sample List"
        1 * mockGroceryListService.fetchAll(customerId) >> [new GroceryListDtoBuilder().id(listId).name(listName).build()]

        when: "fetchAll is invoked"
        Response actualResponse = groceryListResource.fetchAll()

        then: "response should contain required details"
        actualResponse.status == 200
        List<GroceryListDto> actualData = actualResponse.entity
        actualData.size() == 1
        GroceryListDto actual = actualData[0]
        actual.id == listId
        actual.name == listName
        actual.items == null

    }

    def "fetchAll - should return error response if no data found"() {
        given: "lists are fetched"
        def expectedEx = new NoDataFoundException("No lists found")
        1 * mockGroceryListService.fetchAll(customerId) >> { throw expectedEx }

        when: "fetchAll is invoked"
        Response actualResponse = groceryListResource.fetchAll()

        then: "response should contain required details"
        actualResponse.status == 404
        actualResponse.entity == expectedEx.message

    }

    def "getById - should search a list by id"() {
        given: "id of the list to be searched"
        def listId = "LID201"
        def listName = "Sample List"
        Optional<GroceryListDto> expectedDtoOpt = Optional.of(new GroceryListDtoBuilder()
                .id(listId).name(listName)
                .build())
        1 * mockGroceryListService.findById(listId) >> expectedDtoOpt

        when: "getById is invoked"
        Response actualResponse = groceryListResource.getById(listId)

        then: "response should contain required details"
        actualResponse.status == 200
        GroceryListDto actual = actualResponse.entity
        actual.items == null
        actual.id == listId
        actual.name == listName

    }

    def "getById - should return error if not matching record found"() {
        given: "id of the list to be searched"
        def listId = "LID201"
        1 * mockGroceryListService.findById(listId) >> Optional.empty()

        when: "fetchAll is invoked"
        Response actualResponse = groceryListResource.getById(listId)

        then: "response should contain required details"
        actualResponse.status == 404
        actualResponse.hasEntity() == false

    }

    def "deleteList - should delete a list"() {
        given: "id of the list to be deleted"
        def listId = "LID201"
        1 * mockGroceryListService.delete(listId)

        when: "deleteList is invoked"
        Response response = groceryListResource.deleteList(listId)

        then: "the list is deleted"
        response.status == 200
        response.hasEntity() == false

    }

}
