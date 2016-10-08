package org.elastic.elasticsearch.scala.driver.v2_3

import org.elastic.elasticsearch.scala.driver.common.{ApiModelCluster, ApiModelCommon, ApiModelIndices, ApiModelSearch}

/**
  * Created by Alex on 8/27/2016.
  */
object ApiModel_v2_3 {
  trait Common extends ApiModelCommon {
    //2.3 specific common resource
  }
  trait Cluster extends ApiModelCluster {
    //2.3 specific cluster resource
  }
  trait Indices extends ApiModelIndices {
    //2.3 specific indices resource
  }
  trait Search extends ApiModelSearch {
    //2.3 specific search resource
  }
}
