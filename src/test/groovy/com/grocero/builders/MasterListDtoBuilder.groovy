package com.grocero.builders

import com.grocero.dtos.MasterListDto
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy,forClass = MasterListDto)
class MasterListDtoBuilder {
}
