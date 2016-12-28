package org.elastic.elasticsearch.scala.driver.v2_3

import org.elastic.elasticsearch.scala.driver.common._

/**
  * The full v2.3 data model (no JSON-specific types)
  */
object DataModel_v2_3 {
  trait Common extends DataModelCommon {
    //2.3 specific common data model
  }
  trait Cluster extends DataModelCluster {
    //2.3 specific cluster data model
  }
  trait Indices extends DataModelIndices {
    //2.3 specific indices data model
  }
  trait Search extends DataModelSearch {
    //2.3 specific search data model
  }
  trait Xpack extends DataModelXpack {
    //2.3 specific Xpack data model
  }
}
