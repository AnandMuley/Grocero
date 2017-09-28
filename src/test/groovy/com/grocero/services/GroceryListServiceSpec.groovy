package com.grocero.services

import com.grocero.beans.GroceryListBean
import com.grocero.builders.GroceryListDtoBuilder
import com.grocero.builders.ProductDtoBuilder
import com.grocero.common.DtoToBeanMapper
import com.grocero.dtos.GroceryListDto
import com.grocero.repositories.GroceryListRepository
import com.grocero.services.impl.GroceryListServiceImpl
import com.grocero.shared.SharedSpecification

import spock.lang.Subject

class GroceryListServiceSpec extends SharedSpecification {

    @Subject
    GroceryListService groceryListService

    GroceryListRepository mockGroceryListRepository

    DtoToBeanMapper mockDtoToBeanMapper

    def setup() {
        mockDtoToBeanMapper = Mock(DtoToBeanMapper)
        mockGroceryListRepository = Mock(GroceryListRepository)
        groceryListService = new GroceryListServiceImpl(
                dtoToBeanMapper: mockDtoToBeanMapper,
                groceryListRepository: mockGroceryListRepository
        )
    }

    def "update - should update the grocery list record"() {
        given: "grocery list dto with details to be updated"
        def listId = UUID.randomUUID().toString()
        GroceryListDto groceryListDto = new GroceryListDtoBuilder().id(listId).name("General Purchase")
                .items([new ProductDtoBuilder().build()]).build()
        1 * mockDtoToBeanMapper.map(groceryListDto) >> new GroceryListBean("General Purchase")

        when: "update is called"
        groceryListService.update(groceryListDto)

        then: "details should be updated"
        1 * mockGroceryListRepository.save({ GroceryListBean bean ->
            assert bean.name == groceryListDto.name
            assert bean.id == listId
            assert bean.items.size() == 0
            true
        })
    }

}
