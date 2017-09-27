package com.grocero.builders

import com.grocero.dtos.ProductDto
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy,forClass = ProductDto)
class ProductDtoBuilder {

    ProductDtoBuilder() {
        name("General Purchase")
    }
}
