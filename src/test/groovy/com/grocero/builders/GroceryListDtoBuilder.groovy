package com.grocero.builders

import com.grocero.dtos.GroceryListDto
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy, forClass = GroceryListDto)
class GroceryListDtoBuilder {
}
