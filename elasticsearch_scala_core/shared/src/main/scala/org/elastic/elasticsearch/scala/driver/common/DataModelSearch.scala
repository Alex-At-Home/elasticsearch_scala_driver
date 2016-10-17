package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.SearchModifiers.MultiSearchDeclaration
import org.elastic.elasticsearch.scala.driver.utils.BulkUtils
import org.elastic.rest.scala.driver.RestBase.BaseDriverOp
import org.elastic.rest.scala.driver.RestBaseImplicits.CustomTypedToString

/** Data model types used by the search resources
  */
object DataModelSearch {

  /** List of search operations to apply as part of the multi search operation
    *
    * @param ops The list of ES operations
    */
  case class MultiSearchOps(ops: List[BaseDriverOp with MultiSearchDeclaration]) extends CustomTypedToString {
    override def fromTyped: String = BulkUtils.buildBulkSearchOps(ops)
  }
}
