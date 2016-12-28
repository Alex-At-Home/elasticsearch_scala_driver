package org.elastic.elasticsearch.scala.driver.circe.versions

import org.elastic.elasticsearch.scala.driver.circe.v2_3.DataModel_v2_3

/** Batches up the CIRCE data model versions
  */
object DataModelVersions {

  /**
    * The latest version of the driver
    */
  val latest = `2.3`

  /**
    * Version 2.3 of the driver
    */
  object `2.3`
    extends DataModel_v2_3.Common
      with DataModel_v2_3.Cluster
      with DataModel_v2_3.Search
      with DataModel_v2_3.Indices
      with DataModel_v2_3.Xpack
}
