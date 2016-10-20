package org.elastic.elasticsearch.scala.driver.v2_3

import org.elastic.elasticsearch.scala.driver.common._

/**
  * The full API model in v2.3
  */
object ApiModel_v2_3 {
  trait Common extends ApiModelCommon {
    //2.3 specific common resources
  }
  trait Cluster extends ApiModelCluster {
    //2.3 specific cluster resources
  }
  trait Indices extends ApiModelIndices {
    //2.3 specific indices resources
  }
  trait Search extends ApiModelSearch {
    //2.3 specific search resources
  }
  trait Xpack extends ApiModelXpack {
    //2.3 specific Xpack resources
  }
}
