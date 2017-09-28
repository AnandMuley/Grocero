package com.grocero.builders

import com.grocero.dtos.CustomerDto
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy,forClass = CustomerDto)
class CustomerDtoBuilder {
}
