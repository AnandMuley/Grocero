package com.grocero.services

import com.grocero.beans.GroceryListBean
import com.grocero.builders.GroceryListDtoBuilder
import com.grocero.builders.ProductDtoBuilder
import com.grocero.dtos.GroceryListDto
import com.grocero.repositories.GroceryListRepository
import com.grocero.services.impl.GroceryListServiceImpl
import com.grocero.shared.SharedSpecification
import com.grocero.utils.BeanCreatorUtil
import spock.lang.Subject

class GroceryListServiceSpec extends SharedSpecification {

    @Subject
    GroceryListService groceryListService

    GroceryListRepository mockGroceryListRepository

    BeanCreatorUtil mockBeanCreatorUtil

    def setup() {
        mockBeanCreatorUtil = Mock(BeanCreatorUtil)
        mockGroceryListRepository = Mock(GroceryListRepository)
        groceryListService = new GroceryListServiceImpl(
                beanCreatorUtil: mockBeanCreatorUtil,
                groceryListRepository: mockGroceryListRepository
        )
    }

    def "update - should update the grocery list record"() {
        given: "grocery list dto with details to be updated"
        def listId = UUID.randomUUID().toString()
        GroceryListDto groceryListDto = new GroceryListDtoBuilder().id(listId).name("General Purchase")
                .items([new ProductDtoBuilder().build()]).build()
        1 * mockBeanCreatorUtil.update(groceryListDto) >> new GroceryListBean("General Purchase")

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
