package com.grocero.builders

import com.grocero.beans.MasterListBean
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy, forClass = MasterListBean)
class MasterListBeanBuilder {
}
