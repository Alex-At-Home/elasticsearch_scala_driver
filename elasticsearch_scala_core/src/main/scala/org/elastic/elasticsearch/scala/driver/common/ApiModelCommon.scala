package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.driver.scala.OperationGroups.{FieldsSimpleReadable, SimpleWithDataReadable, _}
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.EsResource
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._

/**
  * Misc Rerources:
  *  - (0) Root
  *  - (0) Indexes and Types
  *  - (1) Document
  */
trait ApiModelCommon {

  /////////////////////////////////////////////////////////////////////////////////////////

  // 0 Root nodes: indexes and types

  /**
    * The root node of the Elasticsearch resource tree
    * Will return very basic information about a cluster, and
    * is also a starting point for navigating the hierarchy
    */
  case class `/`()
    extends `tree:/`
      with SimpleReadable
      with EsResource

  /**
    * Utility methods on the root Elasticsearch resource
    * TODO: implement RawOperatableResource
    */
  object `/` {
    /**
      * The generic "any URL" method - the string `resource` is applied by the driver
      *
      * @param resource The resource on which to operate (minus the leading /)
      * @return The operatable resource
      */
    def apply(resource: String) = new RawOperatableResource(s"/$resource")
  }

  /**
    * Create/configure/delete an entire index
    * Also an intermediate step that can be used to get to other parts of the API mode
    *
    * @param index The index
    */
  case class `/$index`(index: String)
    extends `tree:/$index`
      with SimpleReadable
      with SimpleDeletable
      with SimpleWritable
      with SimpleCheckable
      with EsResource

  /**
    * Can be checked to see if the indexes/indexes and type/types exist
    *
    * @param indexes The indexes
    * @param types The types
    */
  case class `/$indexes/$types`(indexes: Seq[String], types: Seq[String])
    extends `tree:/$indexes/$types`
      with SimpleCheckable
      with EsResource

  /**
    * Performs activities on the specified index, type, and automatically generated id:
    * - `write`: adds the written object to the index/type
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type`(index: String, `type`: String)
    extends `tree:/$index/$type`
      with FullyModifiableWritable
      with SimpleCheckable
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
    * - `write`: adds the written object to the index/type with the id
    * - `read`: returns the object in the index with that type/id, if it exists
    * - `delete`: returns the object in the index with that type/id, if it exists
    *
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id`(index: String, `type`: String, id: String)
    extends `tree:/$index/$type/$id`
      with FullyModifiableReadable
      with SimpleCheckable
      with FullyModifiableWritable
      with FullyModifiableDeletable
      with EsResource

  /**
    * Performs activities on the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    *
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id/_source`(index: String, `type`: String, id: String)
    extends FullyModifiableReadable
      with EsResource

  // 1.4] Update APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html

  /**
    * Performs update activities on the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    *
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id/_update`(index: String, `type`: String, id: String)
    extends SimpleWritable
      with EsResource

  // 1.5] Update by query
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html

  /**
    * Updates objects in the specified index based on a query body
    *
    * @param index The index
    */
  case class `/$index/_update_by_query`(index: String)
    extends ConflictSimpleWritable
      with EsResource

  // 1.6] Multi get
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html

  /**
    * Multi gets from the entire cluster based on the object written to the resource
    */
  case class `/_mget`()
    extends RoutableSimpleReadable
      with EsResource

  /**
    * Multi gets from the specified index based on the object written to the resource
    *
    * @param index The index
    */
  case class `/$index/_mget`(index: String)
    extends RoutableSimpleReadable
      with EsResource

  /**
    * Multi gets from the specified index and type based on the object written to the resource
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_mget`(index: String, `type`: String)
    extends RoutableSimpleReadable
      with EsResource

  // 1.7] Bulk API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html

  /**
    * Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    */
  case class `/_bulk`()
    extends SimpleWritable
      with EsResource
  //TODO: allow a list of index/create/update/delete case classes to be written

  /**
    * Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    *
    * @param index The index - sub-requests missing an index will use this as default
    */
  case class `/$index/_bulk`(index: String)
    extends SimpleWritable
      with EsResource
  //TODO: allow a list of index/create/update/delete case classes to be written

  /**
    * Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    *
    * @param index The index - sub-requests missing an index will use this as default
    * @param `type` The type - sub-requests missing an index will use this as default
    */
  case class `/$index/$type/_bulk`(index: String, `type`: String)
    extends SimpleWritable
      with EsResource
  //TODO: allow a list of index/create/update/delete case classes to be written

  // 1.7] Reindex API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html

  case class `/_reindex`()
    extends SimpleWritable
      with EsResource

  // 1.8] Term vectors
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html

  /**
    * Gets the term vectors from the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_termvectors`(index: String, `type`: String)
    extends FieldsSimpleReadable
      with EsResource

  /**
    * Gets the term vectors from the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    *
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id/_termvectors`(index: String, `type`: String, id: String)
    extends FieldsSimpleReadable
      with EsResource

  // 1.9] Multi-term vectors
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html

  /**
    * Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint
    */
  case class `/_mtermvectors`()
    extends SimpleWithDataReadable
      with EsResource

  /**
    * Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint, restricted to an index
    *
    * @param index The index
    */
  case class `/$index/_mtermvectors`(index: String)
    extends SimpleWithDataReadable
      with EsResource

  /**
    * Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint, restricted to an index
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_mtermvectors`(index: String, `type`: String)
    extends SimpleWithDataReadable
      with EsResource
}
object ApiModelCommon extends ApiModelCommon

