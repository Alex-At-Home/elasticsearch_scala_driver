package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.OperationGroups._
import org.elastic.elasticsearch.driver.scala.ElasticsearchBase._

/**
  * A set of DSLs representing the Elasticsearch resources
  */
object ApiModel_common {

  // 0] Intermediate steps

  /**
    * The root node of the Elasticsearch resource tree
    * Will return very basic information about a cluster, and
    * is also a starting point for navigating the hierarchy
    */
  case class `/`()
    extends SimpleWritable
    with EsResource
  {
    /**
      * Sub-resources that require the index
      * @param `index` The index
      * @return An intermediate or final step of the API model
      */
    def $(index: String) = `/$index`(index)

    /**
      * Sub-resources that support multiple (>1) indexes
      * @param index The first index
      * @param otherIndexes Subsequent indexes
      * @return An intermediate or final step of the API model
      */
    def $(index: String, otherIndexes: String) = `/$indexes`(index, otherIndexes)

    /**
      * The multi-get resource
      * @return The multi-get resource
      */
    def _mget = `/_mget`()

    /**
      * The bulk resource
      * @return The bulk resource
      */
    def _bulk = `/_bulk`()

    /**
      * The reindex resource
      * @return The reindex resource
      */
    def _reindex = `/_reindex`()

    /**
      * The multi term vectors resource
      * @return The multi term vectors resource
      */
    def _mtermvectors = `/_mtermvectors`()

    /**
      * The search resource
      * @return The search resource
      */
    def _search = `/_search`()

    /**
      * The search template rendering resource
      * @return The search template rendering resource
      */
    def _render = `/_render`()

    /**
      * The search suggest resource
      * @return The search suggest resource
      */
    def _suggest = `/_suggest`()
  }

  /**
    * Utility methods on the root Elasticsearch resource
    */
  object `/` {
    /**
      * Builds
      * @param resource The resource on which to operate (minus the leading /)
      * @return The operatable resource
      */
    def apply(resource: String) = new RawOperatableResource(s"/$resource")
  }

  /**
    * An intermediate step to resources that support multiple (>1) indexes
    * @param index The first index
    * @param otherIndexes Subsequent indexes
    */
  case class `/$indexes`(index: String, otherIndexes: String*) {

    /**
      * An intermediate step to resources that support multiple (>1) indexes and (>0) types
      * @param types The types
      */
    def $(types: String*) = `/$indexes/$types`(Seq(index) ++ otherIndexes, types)

    /**
      * A search over specified indexes (all types)
      */
    def _search = `/$indexes/_search`(Seq(index) ++ otherIndexes:_*)

    /**
      * Identifies the shard to be searched over the specified indexes
      * @return The node/shard information
      */
    def _search_shards = `/$indexes/_search_shards`(Seq(index) ++ otherIndexes:_*)

    /**
      * Suggests search terms over the specified indexes
      * @return The suggest resource
      */
    def _suggest = `/$indexes/_suggest`(Seq(index) ++ otherIndexes:_*)
  }

  /**
    * An intermediate step to resources that support multiple (>1) indexes and (>0) types
    * @param indexes The indexes
    * @param types The types
    */
  case class `/$indexes/$types`(indexes: Seq[String], types: Seq[String]) {
    /**
      * A search over specified indexes and types
      * @return The search resource
      */
    def _search = `/$indexes/$types/_search`(indexes, types)

    /**
      * Suggests search terms over the specified indexes and types
      * @return The suggest resource
      */
    def _suggest = `/$indexes/$types/_suggest`(indexes, types)
  }
  //TODO: object version (String*)(String*)

  /**
    * An intermediate step to search all indexes
    */
  case class `/_all`() {
    /**
      * Restricts the search to a list of types
      * @param types The list of types
      * @return An intermediate search step
      */
    def $(types: String*) = `/_all/$types`(types:_*)
  }

  /**
    * An intermediate step to search all indexes
    * but a subset of types
    */
  case class `/_all/$types`(types: String*) {
    /**
      * A search over specified types
      * @return The search resource
      */
    def _search = `/_all/$types/_search`(types)

    /**
      * Suggests search terms over the specified types
      * @return The suggest resource
      */
    def _suggest = `/_all/$types/_suggest`(types)
  }

  /**
    * An intermediate step that can be used to get to other parts of the API mode
    * @param index The index
    */
  case class `/$index`(index: String) {
    /**
      * Sub-resources that require the type
      * @param `type` The type
      * @return An intermediate or final step of the API model
      */
    def $(`type`: String) = `/$index/$type`(index, `type`)

    /**
      * The multi-get resource
      * @return The multi-get resource
      */
    def _mget = `/$index/_mget`(index)

    /**
      * The bulk resource
      * @return The bulk resource
      */
    def _bulk = `/$index/_bulk`(index)

    /**
      * The _update_by_query resource
      * @return The bulk resource
      */
    def _update_by_query = `/$index/_update_by_query`(index)

    /**
      * The multi term vectors resource
      * @return The multi term vectors resource
      */
    def _mtermvectors = `/$index/_mtermvectors`(index)

    /**
      * The search resource
      * @return The search resource
      */
    def _search = `/$indexes/_search`(index)

    /**
      * The _all index wildcard of the search resource
      * @return The search resource
      */
    def _all = `/_all`()

    /**
      * Identifies the shard to be searched over the specified index
      * @return The node/shard information
      */
    def _search_shards = `/$indexes/_search_shards`(index)

    /**
      * The search suggest resource
      * @return The search suggest resource
      */
    def _suggest = `/$indexes/_suggest`(index)
  }

  /**
    * Intermediate class to render search templates
    */
  case class `/_render`() {
    def template = `/_render/template`()
  }

  // 1] Document APIs

  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs.html

  // 1.1] Index APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html

  // 1.2] Get APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html

  // 1.3] Delete APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete.html

  /**
    * Performs activities on the specified index, type, and automaticcally generated id:
    * - `write`: adds the written object to the index/type
    * Also acts as an intermediate step to navigate to other resources
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type`(index: String, `type`: String)
    extends FullyModifiableWritable
      with EsResource
  {
    // Navigation

    /**
      * The multi-get resource
      * @return The multi-get resource
      */
    def _mget = `/$index/$type/_mget`(index, `type`)

    /**
      * The bulk resource
      * @return The bulk resource
      */
    def _bulk = `/$index/$type/_bulk`(index, `type`)

    /**
      * The term vectors resource
      * @return The term vectors resource
      */
    def _termvectors = `/$index/$type/_termvectors`(index, `type`)

    /**
      * The multi term vectors resource
      * @return The multi term vectors resource
      */
    def _mtermvectors = `/$index/$type/_mtermvectors`(index, `type`)

    /**
      * The search resource
      * @return The search resource
      */
    def _search = `/$indexes/$types/_search`(List(index), List(`type`))

    /**
      * The search suggest resource
      * @return The search suggest resource
      */
    def _suggest = `/$indexes/$types/_suggest`(List(index), List(`type`))
  }

  /**
    * Performs activities on the specified index, type, and id:
    * - `write`: adds the written object to the index/type with the id
    * - `read`: returns the object in the index with that type/id, if it exists
    * - `delete`: returns the object in the index with that type/id, if it exists
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id`(index: String, `type`: String, id: String)
    extends FullyModifiableReadable
      with FullyModifiableWritable
      with FullyModifiableDeletable
      with EsResource
  {
    //TODO: HEAD to confirm existence

    /**
      * The _update resource
      * @return The _update resource
      */
    def _update = `/$index/$type/$id/_update`(index, `type`, id)

    /**
      * The _update resource
      * @return The _update resource
      */
    def _source = `/$index/$type/$id/_source`(index, `type`, id)

    /**
      * The term vectors resource
      * @return The term vectors resource
      */
    def _termvectors = `/$index/$type/$id/_termvectors`(index, `type`, id)
  }

  /**
    * Performs activities on the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id/_source`(index: String, `type`: String, id: String)
    extends FullyModifiableReadable
    with EsResource
  {
  }

  // 1.4] Update APIs
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html

  /**
    * Performs update activities on the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id/_update`(index: String, `type`: String, id: String)
    extends SimpleWritable
    with EsResource
  {
  }

  // 1.5] Update by query
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html

  /**
    * Updates objects in the specified index based on a query body
    * @param index The index
    */
  case class `/$index/_update_by_query`(index: String)
    extends ConflictSimpleWritable
    with EsResource
  {
  }
  // 1.6] Multi get
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html

  /**
    * Multi gets from the entire cluster based on the object written to the resource
    */
  case class `/_mget`()
    extends RoutableSimpleReadable
      with EsResource
  {
  }

  /**
    * Multi gets from the specified index based on the object written to the resource
    * @param index The index
    */
  case class `/$index/_mget`(index: String)
    extends RoutableSimpleReadable
      with EsResource
  {
  }

  /**
    * Multi gets from the specified index and type based on the object written to the resource
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_mget`(index: String, `type`: String)
    extends RoutableSimpleReadable
      with EsResource
  {
  }

  // 1.7] Bulk API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html

  /**
    * Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    */
  case class `/_bulk`()
    extends SimpleWritable
    with EsResource
  {
    //TODO: allow a list of index/create/update/delete case classes to be written
  }

  /**
    * Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    * @param index The index - sub-requests missing an index will use this as default
    */
  case class `/$index/_bulk`(index: String)
    extends SimpleWritable
      with EsResource
  {
    //TODO: allow a list of index/create/update/delete case classes to be written
  }

  /**
    * Executes a bulk request of index/create/update/delete, based on the
    * object written to the resource
    * @param index The index - sub-requests missing an index will use this as default
    * @param `type` The type - sub-requests missing an index will use this as default
    */
  case class `/$index/$type/_bulk`(index: String, `type`: String)
    extends SimpleWritable
      with EsResource
  {
    //TODO: allow a list of index/create/update/delete case classes to be written
  }

  // 1.7] Reindex API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html

  case class `/_reindex`()
    extends SimpleWritable
      with EsResource
  {
  }

  // 1.8] Term vectors
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html

  /**
    * Gets the term vectors from the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_termvectors`(index: String, `type`: String)
    extends FieldsSimpleReadable
    with EsResource
  {
  }

  /**
    * Gets the term vectors from the specified index, type, and id:
    * - `read`: returns only the source of the object in the index with that type/id, if it exists
    * @param index The index
    * @param `type` The type
    * @param id The id
    */
  case class `/$index/$type/$id/_termvectors`(index: String, `type`: String, id: String)
    extends FieldsSimpleReadable
      with EsResource
  {
  }

  // 1.9] Multi-term vectors
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html

  /**
    * Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint
    */
  case class `/_mtermvectors`()
    extends SimpleWithDataReadable
    with EsResource
  {
  }

  /**
    * Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint, restricted to an index
    * @param index The index
    */
  case class `/$index/_mtermvectors`(index: String)
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  /**
    * Allows to get multiple term vectors at once, depending on the object
    * written to the endpoint, restricted to an index
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type/_mtermvectors`(index: String, `type`: String)
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  // 2] Search API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search.html

  // 2.1] Basic search
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-search.html

  /**
    * Search all indexes and all types, based on the query object written to the
    * resource
    */
  case class `/_search`()
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO modifiers, list here: https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html

    def template = `/_search/template`()
  }

  /**
    * Search all indexes and the specified types, based on the query object written to the
    * resource
    * @param types The types over which to search
    */
  case class `/_all/$types/_search`(types: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO modifiers, list here: https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html
  }

  /**
    * Search the specified indexes and all types, based on the query object written to the
    * resource
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_search`(indexes: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO modifiers, list here: https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html
  }

  /**
    * Search the specified indexes and =types, based on the query object written to the
    * resource
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_search`(indexes: Seq[String], types: Seq[String])
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO modifiers:
    // no data version: https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html
    // with data version: https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html#_parameters_4
  }

  // 2.2] Search Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html

  /**
    * Performs a search using the provided template together
    * with a set of parameters
    */
  case class `/_search/template`()
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  /**
    * Retrieves/stores/deletes templates from/to/from the .scripts index
    */
  case class `/_search/template/$template`(template: String)
    extends SimpleReadable
      with SimpleWritable
      with SimpleDeletable
      with EsResource
  {
  }

  /**
    * Used to test search templates (eg before executing them)
    */
  case class `/_render/template`()
    extends SimpleWithDataReadable
    with EsResource
  {
  }

  // 2.3 Search Shards
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-shards.html

  /**
    * The search shards api returns the indices and shards that a search request
    * would be executed against. This can give useful feedback for working out issues
    * or planning optimizations with routing and shard preferences.
    * @param indexes The index or indexes to query
    */
  case class `/$indexes/_search_shards`(indexes: String*)
    extends SimpleReadable
    with EsResource
  {
    //TODO modifiers
  }

  // 2.4 Suggesters
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    */
  case class `/_suggest`()
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * @param indexes The indexes over which to query
    */
  case class `/$indexes/_suggest`(indexes: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * @param types The types over which to query
    */
  case class `/_all/$types/_suggest`(types: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * @param indexes The indexes over which to query
    * @param types The types over which to query
    */
  case class `/$indexes/$types/_suggest`(indexes: Seq[String], types: Seq[String])
    extends SimpleWithDataReadable
      with EsResource
  {
  }

  /**
    * A nicer constructor for the suggest resource
    */
  object `/$indexes/$types/_suggest` {
    /**
      * Specify the indexes for the _suggest resource
      * @param indexes The indexes for the _suggest resource
      * @return An intermediate object which is converted to a _suggest resource via types
      */
    def apply(indexes: String*) = new Object {
      /**
        * Specify the types for the _suggest resource
        * @param types The types for the _suggest resource
        * @return The _suggest resource
        */
      def apply(types: String*) = `/$indexes/$types/_suggest`(indexes, types)
    }
  }

  // 2.5 Multi search
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html

  //TODO: for each _search element also have an _msearch
  //TODO: this should have a nice typed API

  // 2.6 Count API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html

  //TODO for each _search element also have a _count
  //has (mostly?) the same modifiers as the URI version of _search

  // 2.7 Validate API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html

  //TODO for each _search element also have a _validate
  //has (mostly?) the same modifiers as the URI version of _search
  // (with data version no params)

  // 2.8 Explain API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html

  //TODO for each _search element also have a _explain
  //TODO modifiers

  // 2.9 Percolation API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-percolate.html

  //TODO

  // 2.10 Field stats API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-field-stats.html

  //TODO

  ///////////////////////

  // Resource operations
  //TODO: typed reads
//  trait TypedReadable[T] extends Readable { self =>
//    override def read() = new TypedDriverOperation(None, op, self)
//  }
//  trait Readable { self =>
//    val op: String = GET
//    def read() = new DriverOperation(None, op, self)
//  }

}
