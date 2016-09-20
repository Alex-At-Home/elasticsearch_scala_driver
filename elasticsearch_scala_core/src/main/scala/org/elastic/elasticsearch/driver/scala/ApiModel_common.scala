package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.OperationGroups._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.rest.scala.driver.RestBase._

/**
  * A set of DSLs representing the Elasticsearch resources
  */
object ApiModel_common {


  //TODO 4] cluster API

  //TODO others - eg common plugins like graph?

  //TODO: refactor into different traits and then have a "full object" that inherits from the different versions
  // elasticsearch.scala.driver.common ApiModelCommon, ApiModelSearch, ApiModelIndices, ApiModelCluster
  // elasticsearch.scala.driver.latest etc
  // elasticsearch.scala.driver.v2.3 etc
  // elasticsearch.scala.plugins.driver (for graph)
  // have a latest "pointer" somewhere eg Versions { latest = Common, 2_3_5 = V2_3_5 etc }

  //TODO: enforce >0 params anywhere there's an (eg) (index: String*) type call, currently can call with () which will fail
}
