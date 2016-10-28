package org.elastic.elasticsearch.scala.driver.versions

import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree
import org.elastic.elasticsearch.scala.driver.v2_3.ApiModel_v2_3

/**
  * Ties together different resources into versions
  */
object ApiModelVersions {

  /**
    * The latest version of the driver
    */
  val latest = `2.3`

  /**
    * Version 2.3 of the driver
    */
  object `2.3`
    extends ApiModel_v2_3.Common
      with ApiModel_v2_3.Cluster
      with ApiModel_v2_3.Search
      with ApiModel_v2_3.Indices
      with ApiModel_v2_3.Xpack
      with ApiModelNavigationTree.`tree:/`
  {
    /**
      * Obtain the root node of the resource tree
      * @return The root node of the resource tree
      */
    def apply() = `2.3`.`/`()
  }
}
