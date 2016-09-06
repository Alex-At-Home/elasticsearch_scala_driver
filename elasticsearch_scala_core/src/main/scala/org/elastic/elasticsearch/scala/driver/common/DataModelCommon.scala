package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, CustomTypedToString}
import org.elastic.elasticsearch.scala.driver.utils.BulkUtils

/** Common data model types used by the resources
  */
object DataModelCommon {

  /** The return type from '/', information about Elasticsearch
    *
    * @param status The status code of the request (200 is success)
    * @param name The name of the replying node within the cluster
    * @param cluster_name The name of the cluster
    * @param version Version info about the cluster build
    * @param tag_line "You know, for search"
    */
  case class ElasticsearchInfo
    (status: Int,
     name: String,
     cluster_name: String,
     version: ElasticsearchInfo.VersionInfo,
     tag_line: String
    )

  /** Version within ElasticsearchInfo */
  object ElasticsearchInfo {

    /** Version info within the elasticsearch cluster
      *
      * @param number The elasticsearch version
      * @param build_hash Build hash
      * @param build_timestamp Build timestamp
      * @param build_snapshot Build snapshot info
      * @param lucene_version Underying Lucene version
      */
    case class VersionInfo
    (
      number: String,
      build_hash: String,
      build_timestamp: String,
      build_snapshot: Boolean,
      lucene_version: String
    )
  }

  /** List of operations to apply as part of the bulk operation
    *
    * @param ops The list of ES operations
    */
  case class BulkIndexOps(ops: List[BaseDriverOp]) extends CustomTypedToString {
    override def fromTyped: String = BulkUtils.buildBulkOps(ops)
  }
}
