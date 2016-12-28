package org.elastic.elasticsearch.scala.driver.circe.v2_3

import org.elastic.elasticsearch.scala.driver.circe.common._
import org.elastic.elasticsearch.scala.driver.common

/**
  * The full v2.3 data model using CIRCE
  */
object DataModel_v2_3 {

  trait Common extends DataModelCommon with common.DataModelCommon {
    //2.3 specific common data model
  }
  trait Cluster extends DataModelCluster with common.DataModelCluster {
    //2.3 specific cluster data model
  }
  trait Indices extends DataModelIndices with common.DataModelIndices {
    //2.3 specific indices data model
  }
  trait Search extends DataModelSearch with common.DataModelSearch {
    //2.3 specific search data model
  }
  trait Xpack extends DataModelXpack with common.DataModelXpack {
    //2.3 specific Xpack data model
  }
}
