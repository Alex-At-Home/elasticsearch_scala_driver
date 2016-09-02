package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.EsResource
import org.elastic.elasticsearch.scala.driver.ResourceOperations._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._

/**
  * Misc Resources:
  *  - (0) Root
  *  - (0) Indexes and Types
  *  - (1) Document
  */
trait ApiModelCommon {

  /////////////////////////////////////////////////////////////////////////////////////////

  // 0 Root nodes: indexes and types

  /** The root node of the Elasticsearch resource tree
    * Will return very basic information about a cluster (via `EsReadable.read()`), and
    * is also a starting point for navigating the hierarchy
    */
  case class `/`()
    extends `tree:/`
      with EsReadable[StandardParams]
      with EsResource

  /** Allows for generic access to the ES client - any URI string, any operation, and any modifier
    *
    * @param uri The resource name (including the leading '/')
    */
  case class `/$uri`(uri: String) extends EsResource
    with EsReadable[NoParams] with EsWithDataReadable[NoParams] with EsCheckable[NoParams]
    with EsWritable[NoParams] with EsNoDataWritable[NoParams]
    with EsDeletable[NoParams] with EsWithDataDeletable[NoParams]
  {
    override lazy val location = uri
  }

  /** Create/configure/delete an entire index
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html Read and Check Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html Write Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete.html Delete Docs]]
    * Also an intermediate step that can be used to get to other parts of the API mode
    *
    * @param index The index to read/transform
    */
  case class `/$index`(index: String)
    extends `tree:/$index`
      with EsReadable[StandardParams]
      with EsWritable[StandardParams]
      with EsDeletable[StandardParams]
      with EsCheckable[NoParams]
      with EsResource

  /** Can be checked to see if the indexes/indexes and type/types exist
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html Check Docs]]
    *
    * @param indexes The indexes to check
    * @param types The types to check
    */
  case class `/$indexes/$types`(indexes: Seq[String], types: Seq[String])
    extends `tree:/$indexes/$types`
      with EsCheckable[NoParams]
      with EsResource

  /** Performs activities on the specified index, type, and automatically generated id
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html Check Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html Write Docs]]
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type`(index: String, `type`: String)
    extends `tree:/$index/$type`
      with EsWritable[IndexWriteParams]
      with EsCheckable[NoParams]
      with EsResource

  /////////////////////////////////////////////////////////////////////////////////////////

  // 1] Document APIs

  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs.html

  // 1.1] Index APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html

  // 1.2] Get APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html

  // 1.3] Delete APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete.html

  /**
    * Performs activities on the specified index, type, and id:
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html Read and Check Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html Write Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete.html Delete Docs]]
    *
    * @param index The index
    * @param `type` The type
    * @param id The document id
    */
  case class `/$index/$type/$id`(index: String, `type`: String, id: String)
    extends `tree:/$index/$type/$id`
      with EsReadable[DocumentReadParams]
      with EsCheckable[NoParams]
      with EsWritable[IndexWriteParams]
      with EsDeletable[IndexDeleteParams]
      with EsResource

  /** Performs activities on the specified index, type, and id:
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html Read Docs]]
    *
    * @param index The index
    * @param `type` The type
    * @param id The document id
    */
  case class `/$index/$type/$id/_source`(index: String, `type`: String, id: String)
    extends EsReadable[DocumentReadParams]
      with EsResource

  // 1.4] Update APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html

  /** Performs update activities on the specified index, type, and id:
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html Write Docs]]
    *
    * @param index The index
    * @param `type` The type
    * @param id The document id
    */
  case class `/$index/$type/$id/_update`(index: String, `type`: String, id: String)
    extends EsWritable[StandardParams]
      with EsResource

  // 1.5] Update by query
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html

  /** Updates objects in the specified index based on a query body
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html Docs]]
    *
    * @param index The index
    */
  case class `/$index/_update_by_query`(index: String)
    extends EsWritable[UpdateByQueryParams]
      with EsResource

  // 1.6] Multi get
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html

  /** Multi GET API allows to get multiple documents based on an index, type (optional) and id (and possibly routing).
    * The response includes a docs array with all the fetched documents, each element similar in structure to a document
    * provided by the get API.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html Docs]]
    */
  case class `/_mget`()
    extends EsReadable[RoutingStandardParams]
      with EsResource

  /** Multi GET API allows to get multiple documents based on an index, type (optional) and id (and possibly routing).
    * The response includes a docs array with all the fetched documents, each element similar in structure to a document
    * provided by the get API.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html Docs]]
    *
    * @param index The index
    */
  case class `/$index/_mget`(index: String)
    extends EsReadable[RoutingStandardParams]
      with EsResource

  /** Multi GET API allows to get multiple documents based on an index, type (optional) and id (and possibly routing).
    * The response includes a docs array with all the fetched documents, each element similar in structure to a document
    * provided by the get API.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html Docs]]
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_mget`(index: String, `type`: String)
    extends EsReadable[RoutingStandardParams]
      with EsResource

  // 1.7] Bulk API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html

  /** Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html Docs]]
    */
  case class `/_bulk`()
    extends EsWritable[StandardParams]
      with EsResource
  //TODO: allow a list of index/create/update/delete case classes to be written

  /** Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html Docs]]
    *
    * @param index The index - sub-requests missing an index will use this as default
    */
  case class `/$index/_bulk`(index: String)
    extends EsWritable[StandardParams]
      with EsResource
  //TODO: allow a list of index/create/update/delete case classes to be written

  /** Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html Docs]]
    *
    * @param index The index - sub-requests missing an index will use this as default
    * @param `type` The type - sub-requests missing an index will use this as default
    */
  case class `/$index/$type/_bulk`(index: String, `type`: String)
    extends EsWritable[StandardParams]
      with EsResource
  //TODO: allow a list of index/create/update/delete case classes to be written

  // 1.7] Reindex API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html

  /** Re-indexes an index following eg changes to mapping
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html Docs]]
    */
  case class `/_reindex`()
    extends EsWritable[StandardParams]
      with EsResource

  // 1.8] Term vectors
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html

  /** Gets the term vectors from the specified index, type, and id:
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html Docs]]
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_termvectors`(index: String, `type`: String)
    extends EsReadable[FieldStandardParams]
      with EsResource

  /** Gets the term vectors from the specified index, type, and id:
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html Docs]]
    *
    * @param index The index
    * @param `type` The type
    * @param id The document id
    */
  case class `/$index/$type/$id/_termvectors`(index: String, `type`: String, id: String)
    extends EsReadable[FieldStandardParams]
      with EsResource

  // 1.9] Multi-term vectors
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html

  /** Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html Docs]]
    */
  case class `/_mtermvectors`()
    extends EsWithDataReadable[StandardParams]
      with EsResource

  /** Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html Docs]]
    *
    * @param index The index
    */
  case class `/$index/_mtermvectors`(index: String)
    extends EsWithDataReadable[StandardParams]
      with EsResource

  /** Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html Docs]]
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_mtermvectors`(index: String, `type`: String)
    extends EsWithDataReadable[StandardParams]
      with EsResource
}
object ApiModelCommon extends ApiModelCommon

