package org.elastic.elasticsearch.scala.driver.versions

import org.elastic.elasticsearch.scala.driver.v2_3.ApiModel_v2_3

/**
  * Created by Alex on 8/27/2016.
  */
object Versions {

  val latest = `2.3`

  object `2.3`
    extends ApiModel_v2_3.Common
      with ApiModel_v2_3.Cluster
      with ApiModel_v2_3.Search
      with ApiModel_v2_3.Indices
}
