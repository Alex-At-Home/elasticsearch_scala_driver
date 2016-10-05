package org.elastic.elasticsearch.scala.driver.common

import io.circe.jawn._
import io.circe._
import io.circe.generic.JsonCodec
import org.elastic.elasticsearch.scala.driver.utils.BulkUtils
import org.elastic.rest.scala.driver.RestBase._

/** Common data model types used by the resources
  */
object DataModelCommon {

  /** The return type from '/', information about Elasticsearch
    *
    * @param name The name of the replying node within the cluster
    * @param cluster_name The name of the cluster
    * @param version Version info about the cluster build
    * @param tagline "You know, for search"
    */
  @JsonCodec case class ElasticsearchInfo
    (name: String,
     cluster_name: String,
     version: ElasticsearchInfo.VersionInfo,
     tagline: String
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
    @JsonCodec case class VersionInfo
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
