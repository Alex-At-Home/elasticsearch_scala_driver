package org.elastic.elasticsearch.scala.driver.versions

import org.elastic.elasticsearch.scala.driver.v2_3.DataModel_v2_3

/**
  * Ties together different resource types into versions
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
