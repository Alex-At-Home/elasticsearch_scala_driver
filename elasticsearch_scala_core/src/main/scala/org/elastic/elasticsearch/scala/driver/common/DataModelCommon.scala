package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, CustomTypedToString, EsResource}
import org.elastic.elasticsearch.scala.driver.utils.BulkUtils

/** Common data model types used by the resources
  */
object DataModelCommon {

  /** List of operations to apply as part of the bulk operation
    *
    * @param ops The list of ES operations
    */
  case class BulkIndexOps(ops: List[BaseDriverOp]) extends CustomTypedToString {
    override def fromTyped: String = BulkUtils.buildBulkOps(ops)
  }
}
