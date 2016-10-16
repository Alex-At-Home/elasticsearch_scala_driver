package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.ApiModelCommon._
import org.elastic.elasticsearch.scala.driver.common.ApiModelSearch._
import org.elastic.elasticsearch.scala.driver.common.ApiModelIndices._
import org.elastic.elasticsearch.scala.driver.common.ApiModelCluster._
import org.elastic.elasticsearch.scala.driver.common.ApiModelXpack._

/**
  * Contains a hierarchical model of the Elasticsearch API resources
  */
object ApiModelNavigationTree {

  /////////////////////////////////////////////////////////////////////////////////////////

  // 0 Root nodes: indexes and types

  /**
    * The root node of the Elasticsearch resource tree
    * Is a starting point for navigating the hierarchy
    */
  trait `tree:/`
  {
    /** Allows for generic access to the ES client - any URL string, any operation, and any modifier
      * @param uri The resource name (including the leading '/')
      */
    def uri(uri: String) = `/$uri`(uri)
    /**
      * Sub-resources that require the index
      *
      * @param `index` The index
      * @return An intermediate or final step of the API model
      */
    def $(index: String) = `/$index`(index)

    /**
      * Sub-resources that support multiple (>1) indexes
      *
      * @param index The first index
      * @param otherIndexes Subsequent indexes
      * @return An intermediate or final step of the API model
      */
    def $(index: String, otherIndexes: String) = `tree:/$indexes`(index, otherIndexes)

    /**
      * The _all index wildcard of the search resource
      *
      * @return The search resource
      */
    def _all = `tree:/_all`()

    /**
      * The multi-get resource
      *
      * @return The multi-get resource
      */
    def _mget = `/_mget`()

    /**
      * The bulk resource
      *
      * @return The bulk resource
      */
    def _bulk = `/_bulk`()

    /**
      * The reindex resource
      *
      * @return The reindex resource
      */
    def _reindex = `/_reindex`()

    /**
      * The multi term vectors resource
      *
      * @return The multi term vectors resource
      */
    def _mtermvectors = `/_mtermvectors`()

    /**
      * The search resource
      *
      * @return The search resource
      */
    def _search = `/_search`()

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/_msearch`()

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/_count`()

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/_validate`()

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _explain = `/_explain`()

    /**
      * The search template rendering resource
      *
      * @return The search template rendering resource
      */
    def _render = `tree:/_render`()

    /**
      * The search suggest resource
      *
      * @return The search suggest resource
      */
    def _suggest = `/_suggest`()

    /**
      * A resource to configure a mapping across all indexes that contain it
      *
      * @return A resource to configure a mapping across all indexes that contain it
      */
    def _mapping = `/_mapping`()

    /**
      * An intermediate resource to configure aliases
      *
      * @return An intermediate resource to configure aliases
      */
    def _alias = `tree:/_alias`()

    /**
      * A resource to perform complex configuration on aliases
      *
      * @return A resource to perform complex configuration on aliases
      */
    def _aliases = `/_aliases`()

    /**
      * A resource to update the index settings
      *
      * @return A resource to update the index settings
      */
    def _settings = `/_settings`()

    /**
      * A resource to test the installed analyzers
      *
      * @return A resource to tests the installed analyzers
      */
    def _analyze = `/_analyze`()

    /**
      * A resource to manipulate index templates
      *
      * @return A resource to manipulate index templates
      */
    def _template = `/_template`()

    /**
      * A resource to retrieve cluster statistics
      *
      * @return A resource to retrieve cluster statistics
      */
    def _stats = `/_stats`()

    /**
      * A resource to retrieve index segment information
      *
      * @return A resource to retrieve index segment information
      */
    def _segments = `/_segments`()

    /**
      * A resource to retrieve index recovery information
      *
      * @return A resource to retrieve index recovery information
      */
    def _recovery = `/_recovery`()

    /**
      * A resource to retrieve shard information
      *
      * @return A resource to retrieve shard information
      */
    def _shard_stores = `/_shard_stores`()

    /**
      * An intermediate step to retrieve cache resources
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _cache = `tree:/_cache`()

    /**
      * A resource to flush one or more indices
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _flush = `/_flush`()

    /**
      * A resource to refresh one or more indices
      *
      * @return A resource to refresh one or more indices
      */
    def _refresh = `/_refresh`()

    /**
      * A resource to force the merging of segments within indexes
      *
      * @return A resource to force the merging of segments within indexes
      */
    def _forcemerge = `/_forcemerge`()

    /**
      * A resource to upgrade indices to higher versions of Lucene
      *
      * @return A resource to upgrade indices to higher versions of Lucene
      */
    def _upgrade = `/_upgrade`()

    /**
      * An intermediate resource to obtain cluster-related metadata
      *
      * @return An intermediate resource to obtain cluster-related metadata
      */
    def _cluster = `tree:/_cluster`()

    /**
      * An intermediate resource to obtain cluster-node-related metadata
      *
      * @return An intermediate resource to obtain cluster-node-related metadata
      */
    def _nodes = `/_nodes`()

    /**
      * An intermediate resource to obtain task management resources
      *
      * @return An intermediate resource to obtain task management resources
      */
    def _tasks = `/_tasks`()

    /**
      * An intermediate resource to obtain security-related resources
      *
      * @return An intermediate resource to obtain security-related resources
      */
    def _shield = `/_shield`()

    /**
      * An intermediate resource to obtain license-related resources
      *
      * @return An intermediate resource to obtain license-related resources
      */
    def _license = `/_license`()

    /**
      * An intermediate resource to obtain snapshot-related resources
      *
      * @return An intermediate resource to obtain snapshot-related resources
      */
    def _snapshot = `/_snapshot`()

    /**
      * An intermediate resource to obtain alerting (watcher)-related resources
      *
      * @return An intermediate resource to obtain alerting (watcher)-related resources
      */
    def _watcher = `/_watcher`()
  }

  /**
    * An intermediate step to resources that support multiple (>1) indexes
    */
  case class `tree:/$indexes`(index: String, otherIndexes: String*) {
    /**
      * An intermediate step to resources that support multiple (>1) indexes and (>0) types
      *
      * @param types The types
      */
    def $(types: String*) = `/$indexes/$types`(Seq(index) ++ otherIndexes, types)

    /**
      * A search over specified indexes (all types)
      */
    def _search = `/$indexes/_search`(Seq(index) ++ otherIndexes:_*)

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/$indexes/_msearch`(Seq(index) ++ otherIndexes:_*)

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/$indexes/_count`(Seq(index) ++ otherIndexes:_*)

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/$indexes/_validate`(Seq(index) ++ otherIndexes:_*)

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/$indexes/_explain`(Seq(index) ++ otherIndexes:_*)

    /**
      * Identifies the shard to be searched over the specified indexes
      *
      * @return The node/shard information
      */
    def _search_shards = `/$indexes/_search_shards`(Seq(index) ++ otherIndexes:_*)

    /**
      * Suggests search terms over the specified indexes
      *
      * @return The suggest resource
      */
    def _suggest = `/$indexes/_suggest`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to open these indexes
      *
      * @return A resource to open this index
      */
    def _open = `/$indexes/_open`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to close these indexes
      *
      * @return A resource to close this index
      */
    def _close = `/$indexes/_close`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to get the mappings for these indexes
      *
      * @return A resource to get the mappings for these indexes
      */
    def _mapping = `/$indexes/_mapping`(Seq(index) ++ otherIndexes:_*)


    /**
      * An intermediate resource to configure aliases
      *
      * @return An intermediate resource to configure aliases
      */
    def _alias = `tree:/$indexes/_alias`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to update the index settings
      *
      * @return A resource to update the index settings
      */
    def _settings = `/$indexes/_settings`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to retrieve cluster statistics
      *
      * @return A resource to retrieve cluster statistics
      */
    def _stats = `/$indexes/_stats`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to retrieve index segment information
      *
      * @return A resource to retrieve index segment information
      */
    def _segments = `/$indexes/_segments`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to retrieve index recovery information
      *
      * @return A resource to retrieve index recovery information
      */
    def _recovery = `/$indexes/_recovery`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to retrieve shard information
      *
      * @return A resource to retrieve shard information
      */
    def _shard_stores = `/$indexes/_shard_stores`(Seq(index) ++ otherIndexes:_*)

    /**
      * An intermediate step to retrieve cache resources
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _cache = `tree:/$indexes/_cache`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to flush one or more indices
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _flush = `/$indexes/_flush`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to refresh one or more indices
      *
      * @return A resource to refresh one or more indices
      */
    def _refresh = `/$indexes/_refresh`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to force the merging of segments within indexes
      *
      * @return A resource to force the merging of segments within indexes
      */
    def _forcemerge = `/$indexes/_forcemerge`(Seq(index) ++ otherIndexes:_*)

    /**
      * A resource to upgrade indices to higher versions of Lucene
      *
      * @return A resource to upgrade indices to higher versions of Lucene
      */
    def _upgrade = `/$indexes/_upgrade`(Seq(index) ++ otherIndexes:_*)
  }

  /**
    * Create/configure/delete an entire index
    * Also an intermediate step that can be used to get to other parts of the API mode
    */
  trait `tree:/$index`
  {
    def index: String
    /**
      * Sub-resources that require the type
      *
      * @param `type` The type
      * @return An intermediate or final step of the API model
      */
    def $(`type`: String) = `/$index/$type`(index, `type`)

    /**
      * Sub-resources that work on  multiple types
      *
      * @param firstType The first type (must be at least 2)
      * @param otherType The second type  (must be at least 2)
      * @param moreTypes Optional 3rd+ types
      * @return An intermediate or final step of the API model
      */
    def $(firstType: String, otherType: String, moreTypes: String*) =
      `/$indexes/$types`(Seq(index), Seq(firstType, otherType) ++ moreTypes)

    /**
      * The multi-get resource
      *
      * @return The multi-get resource
      */
    def _mget = `/$index/_mget`(index)

    /**
      * The bulk resource
      *
      * @return The bulk resource
      */
    def _bulk = `/$index/_bulk`(index)

    /**
      * The _update_by_query resource
      *
      * @return The bulk resource
      */
    def _update_by_query = `/$index/_update_by_query`(index)

    /**
      * The multi term vectors resource
      *
      * @return The multi term vectors resource
      */
    def _mtermvectors = `/$index/_mtermvectors`(index)

    /**
      * The search resource
      *
      * @return The search resource
      */
    def _search = `/$indexes/_search`(index)

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/$indexes/_msearch`(index)

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/$indexes/_count`(index)

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/$indexes/_validate`(index)

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/$indexes/_explain`(index)

    /**
      * Identifies the shard to be searched over the specified index
      *
      * @return The node/shard information
      */
    def _search_shards = `/$indexes/_search_shards`(index)

    /**
      * The search suggest resource
      *
      * @return The search suggest resource
      */
    def _suggest = `/$indexes/_suggest`(index)

    /**
      * A resource to open this index
      *
      * @return A resource to open this index
      */
    def _open = `/$indexes/_open`(index)

    /**
      * A resource to close this index
      *
      * @return A resource to close this index
      */
    def _close = `/$indexes/_close`(index)

    /**
      * A resource to configure the mappings for this index
      *
      * @return A resource to configure the mappings for this index
      */
    def _mapping = `/$indexes/_mapping`(index)

    /**
      * An intermediate resource to configure aliases
      *
      * @return An intermediate resource to configure aliases
      */
    def _alias = `tree:/$indexes/_alias`(index)

    /**
      * A resource to update the index settings
      *
      * @return A resource to update the index settings
      */
    def _settings = `/$indexes/_settings`(index)

    /**
      * A resource to test the installed analyzers
      *
      * @return A resource to tests the installed analyzers
      */
    def _analyze = `/$index/_analyze`(index)

    /**
      * A resource to retrieve cluster statistics
      *
      * @return A resource to retrieve cluster statistics
      */
    def _stats = `/$indexes/_stats`(index)

    /**
      * A resource to retrieve index segment information
      *
      * @return A resource to retrieve index segment information
      */
    def _segments = `/$indexes/_segments`(index)

    /**
      * A resource to retrieve index recovery information
      *
      * @return A resource to retrieve index recovery information
      */
    def _recovery = `/$indexes/_recovery`(index)

    /**
      * A resource to retrieve shard information
      *
      * @return A resource to retrieve shard information
      */
    def _shard_stores = `/$indexes/_shard_stores`(index)

    /**
      * An intermediate step to retrieve cache resources
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _cache = `tree:/$indexes/_cache`(index)

    /**
      * A resource to flush one or more indices
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _flush = `/$indexes/_flush`(index)

    /**
      * A resource to refresh one or more indices
      *
      * @return A resource to refresh one or more indices
      */
    def _refresh = `/$indexes/_refresh`(index)

    /**
      * A resource to force the merging of segments within indexes
      *
      * @return A resource to force the merging of segments within indexes
      */
    def _forcemerge = `/$indexes/_forcemerge`(index)

    /**
      * A resource to upgrade indices to higher versions of Lucene
      *
      * @return A resource to upgrade indices to higher versions of Lucene
      */
    def _upgrade = `/$indexes/_upgrade`(index)
  }

  /**
    * An intermediate step to search all indexes
    */
  case class `tree:/_all`() {
    /**
      * Restricts the search to a list of types
      *
      * @param types The list of types
      * @return An intermediate search step
      */
    def $(types: String*) = `tree:/_all/$types`(types:_*)

    /**
      * A resource to open all indexes in the cluster
      *
      * @return A resource to open all indexes in the cluster
      */
    def _open = `/_all/_open`()

    /**
      * A resource to close all open indexes in the cluster
      *
      * @return A resource to close all open indexes in the cluster
      */
    def _close = `/_all/_close`()

    /**
      * A resource to configure a mapping across all indexes that contain it
      *
      * @return A resource to configure a mapping across all indexes that contain it
      */
    def _mapping = `/_all/_mapping`()

    /**
      * An intermediate resource to configure aliases
      *
      * @return An intermediate resource to configure aliases
      */
    def _alias = `tree:/_all/_alias`()

    /**
      * A resource to update the index settings
      *
      * @return A resource to update the index settings
      */
    def _settings = `/_all/_settings`()

    //TODO: think everything that's in indexes needs to ne here also

    /**
      * A resource to retrieve index segment information
      *
      * @return A resource to retrieve index segment information
      */
    def _segments = `/_all/_segments`()

    /**
      * A resource to retrieve index recovery information
      *
      * @return A resource to retrieve index recovery information
      */
    def _recovery = `/_all/_recovery`()

    /**
      * A resource to retrieve shard information
      *
      * @return A resource to retrieve shard information
      */
    def _shard_stores = `/_all/_shard_stores`()

    /**
      * An intermediate step to retrieve cache resources
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _cache = `tree:/_all/_cache`()

    /**
      * A resource to flush one or more indices
      *
      * @return An intermediate step to retrieve cache resources
      */
    def _flush = `/_all/_flush`()

    /**
      * A resource to refresh one or more indices
      *
      * @return A resource to refresh one or more indices
      */
    def _refresh = `/_all/_refresh`()

    /**
      * A resource to force the merging of segments within indexes
      *
      * @return A resource to force the merging of segments within indexes
      */
    def _forcemerge = `/_all/_forcemerge`()

    /**
      * A resource to upgrade indices to higher versions of Lucene
      *
      * @return A resource to upgrade indices to higher versions of Lucene
      */
    def _upgrade = `/_all/_upgrade`()
  }

  /**
    * An intermediate step to resources that support multiple (>1) indexes and (>0) types
    */
  trait `tree:/$indexes/$types`
  {
    def indexes: Seq[String]
    def types: Seq[String]
    /**
      * A search over specified indexes and types
      *
      * @return The search resource
      */
    def _search = `/$indexes/$types/_search`(indexes, types)

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/$indexes/$types/_msearch`(indexes, types)

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/$indexes/$types/_count`(indexes, types)

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/$indexes/$types/_validate`(indexes, types)

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/$indexes/$types/_explain`(indexes, types)

    /**
      * Suggests search terms over the specified indexes and types
      *
      * @return The suggest resource
      */
    def _suggest = `/$indexes/$types/_suggest`(indexes, types)
  }

  trait `tree:/$index/$type`
  {
    def index: String
    def `type`: String

    // Navigation

    /**
      * A resource for indexing new documents
      *
      * @param id The id of the document
      * @return The resource for indexing new documents
      */
    def $(id: String) = `/$index/$type/$id`(index, `type`, id)

    /**
      * The multi-get resource
      *
      * @return The multi-get resource
      */
    def _mget = `/$index/$type/_mget`(index, `type`)

    /**
      * The bulk resource
      *
      * @return The bulk resource
      */
    def _bulk = `/$index/$type/_bulk`(index, `type`)

    /**
      * The term vectors resource
      *
      * @return The term vectors resource
      */
    def _termvectors = `/$index/$type/_termvectors`(index, `type`)

    /**
      * The multi term vectors resource
      *
      * @return The multi term vectors resource
      */
    def _mtermvectors = `/$index/$type/_mtermvectors`(index, `type`)

    /**
      * The search resource
      *
      * @return The search resource
      */
    def _search = `/$indexes/$types/_search`(Seq(index), Seq(`type`))

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/$indexes/$types/_msearch`(Seq(index), Seq(`type`))

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/$indexes/$types/_count`(Seq(index), Seq(`type`))

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/$indexes/$types/_validate`(Seq(index), Seq(`type`))

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/$indexes/$types/_explain`(Seq(index), Seq(`type`))
    /**
      * The search suggest resource
      *
      * @return The search suggest resource
      */
    def _suggest = `/$indexes/$types/_suggest`(Seq(index), Seq(`type`))
  }

  /**
    * An intermediate step to search all indexes
    * but a subset of types
    */
  case class `tree:/_all/$types`(types: String*) {
    /**
      * A search over specified types
      *
      * @return The search resource
      */
    def _search = `/_all/$types/_search`(types:_*)

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/_all/$types/_msearch`(types:_*)

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/_all/$types/_count`(types:_*)

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/_all/$types/_validate`(types:_*)

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/_all/$types/_explain`(types:_*)

    /**
      * Suggests search terms over the specified types
      *
      * @return The suggest resource
      */
    def _suggest = `/_all/$types/_suggest`(types:_*)
  }

  // 0.3.1 Search navigation

  class `tree:/_search`() {
    /**
      * An intermediate result to use templates to search
      *
      * @return Templated search resource
      */
    def template = `/_search/template`()
  }

  // 0.3.2 Search Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html

  /**
    * Intermediate class to render search templates
    */
  case class `tree:/_render`() {
    /**
      * An intermediate result to use templates to search
      *
      * @return Templated search resource
      */
    def template = `/_render/template`()
  }

  // 0.3.3 Field mapping intermediate steps
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html

  /**
    * Gets the mapping for all indexes and all types - intermediate step
    */
  trait `tree:/_mapping`
  {
    /**
      * Returns the mapping control resource for all indexes and specified types
      *
      * @param types The types over which to restrict the mapping operations
      * @return Returns the mapping control resource for all indexes and specified types
      */
    def $(types: String*) = `/_mapping/$types`(types:_*)

    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `tree:/_mapping/field`()
  }

  /**
    * Gets the mapping for all indexes and 1+ types - intermediate step
    */
  trait `tree:/_mapping/$types`
  {
    def types: Seq[String]
    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `tree:/_mapping/$types/field`(types:_*)
  }
  /**
    * Gets the mapping for all indexes and all types - intermediate step
    */
  trait `tree:/_all/_mapping`
  {
    /**
      * Returns the mapping control resource for all indexes and specified types
      *
      * @param types The types over which to restrict the mapping operations
      * @return Returns the mapping control resource for all indexes and specified types
      */
    def $(types: String*) = `/_all/_mapping/$types`(types:_*)

    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `tree:/_all/_mapping/field`()
  }

  /**
    * Gets the mapping for all indexes and 1+ types - intermediate step
    */
  trait `tree:/_all/_mapping/$types`
  {
    def types: Seq[String]
    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `tree:/_all/_mapping/$types/field`(types:_*)
  }
  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `tree:/_mapping/field`() {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/_mapping/field/$fields`(fields: _*)
  }
  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `tree:/_mapping/$types/field`(types: String*) {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/_mapping/$types/field/$fields`(types, fields)
  }
  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `tree:/_all/_mapping/field`() {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/_all/_mapping/field/$fields`(fields:_*)
  }
  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `tree:/_all/_mapping/$types/field`(types: String*) {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/_all/_mapping/$types/field/$fields`(types, fields)
  }

  /**
    * Get the mapping for 1+ indexes and all types - intermediate step
    */
  trait `tree:/$indexes/_mapping`
  {
    def indexes: Seq[String]

    /**
      * Returns the mapping control resource for specified indexes and types
      *
      * @param types The types over which to restrict the mapping operations
      * @return Returns the mapping control resource for specified indexes and types
      */
    def $(types: String*) = `/$indexes/_mapping/$types`(indexes, types)

    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `tree:/$indexes/_mapping/field`(indexes:_*)
  }

  /**
    * Gets the mapping for 1+ indexes and 1+ types - intermediate step
    */
  trait `tree:/$indexes/_mapping/$types`
  {
    def indexes: Seq[String]
    def types: Seq[String]
    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `tree:/$indexes/_mapping/$types/field`(indexes, types)
  }

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param indexes The indexes over which the field mappings are retrieved
    */
  case class `tree:/$indexes/_mapping/field`(indexes: String*) {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/$indexes/_mapping/field/$fields`(indexes, fields)
  }
  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param indexes The indexes over which the field mappings are retrieved
    * @param types The types over which the field mappings are retrieved
    */
  case class `tree:/$indexes/_mapping/$types/field`(indexes: Seq[String], types: Seq[String]) {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/$indexes/_mapping/$types/field/$fields`(indexes, types, fields)
  }

  // 0.3.4 Index aliases
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html

  /**
    * An intermediate object for navigating to alias related resources
    */
  case class `tree:/_alias`() {
    /**
      * Returns the check/retrieve _alias resource for the specified aliases
      *
      * @param aliases The specified alias names
      * @return The check/retrieve _alias resource for the specified aliases
      */
    def $(aliases: String*) = `/_alias/$aliases`(aliases:_*)
    /**
      * Returns the check/retrieve _alias resource for all aliases
      *
      * @return The check/retrieve _alias resource for all aliases
      */
    def * =`/_alias/*`()
  }

  /**
    * An intermediate object for navigating to alias related resources
    *
    * @param indexes The indexes over which to restrict these alias operations
    */
  case class `tree:/$indexes/_alias`(indexes: String*) {
    /**
      * Returns the check/retrieve/delete/write _alias resource for the specified aliases
      *
      * @param alias The specified single alias name
      * @return The check/retrieve/delete/write _alias resource for the specified aliases
      */
    def $(alias: String) = `/$indexes/_alias/$alias`(indexes, alias)
    /**
      * Returns the check/retrieve _alias resource for the specified aliases
      *
      * @param firstAlias The first specified alias name (must have >=1, ie 2+ in total)
      * @param secondAlias The first specified alias name (must have >=1, ie 2+ in total)
      * @param otherAliases Subsequent specified aliases names (must have >0)
      * @return
      */
    def $(firstAlias: String, secondAlias: String, otherAliases: String*) =
      `/$indexes/_alias/$aliases`(indexes, Seq(firstAlias, secondAlias) ++ otherAliases)

    /**
      * Returns the check/retrieve _alias resource for all aliases
      *
      * @return The check/retrieve _alias resource for all aliases
      */
    def * =`/$indexes/_alias/*`(indexes:_*)
  }

  /**
    * An intermediate object for navigating to alias related resources
    */
  case class `tree:/_all/_alias`() {
    /**
      * Returns the check/retrieve/delete/write _alias resource for the specified aliases
      *
      * @param alias The specified single alias name
      * @return The check/retrieve/delete/write _alias resource for the specified aliases
      */
    def $(alias: String) = `/_all/_alias/$alias`(alias)
    /**
      * Returns the check/retrieve _alias resource for the specified aliases
      *
      * @param firstAlias The first specified alias name (must have >=1, ie 2+ in total)
      * @param secondAlias The first specified alias name (must have >=1, ie 2+ in total)
      * @param otherAliases Subsequent specified aliases names (must have >0)
      * @return
      */
    def $(firstAlias: String, secondAlias: String, otherAliases: String*) =
      `/_all/_alias/$aliases`(Seq(firstAlias, secondAlias) ++ otherAliases:_*)

    /**
      * Returns the check/retrieve _alias resource for all aliases
      *
      * @return The check/retrieve _alias resource for all aliases
      */
    def * =`/_all/_alias/*`()
  }

  // 0.3.5 Index settings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html

  // 3.5 Get and Update index settings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html

  /**
    * Read or Update the settings for the entire cluster - intermediate step
    */
  trait `tree:/_settings`
  {
    /**
      * Returns an intermediate class that provides navigation to the filter index settings resource
      *
      * @return An intermediate class that provides navigation to the filter index settings resource
      */
    def name =  `tree:/_settings/name=`()
  }

  /**
    * Read or Update the settings for the entire cluster - intermediate step
    */
  trait `tree:/_all/_settings`
  {
    /**
      * Returns an intermediate class that provides navigation to the filter index settings resource
      *
      * @return An intermediate class that provides navigation to the filter index settings resource
      */
    def name =  `tree:/_all/_settings/name=`()
  }

  /**
    * An intermediate class that provides navigation to the filter index settings resource
    */
  case class `tree:/_settings/name=`() {
    /**
      * Returns the filtered resource for getting the index settings
      *
      * @param name The filter glob string vs index names
      * @return The filtered resource for getting the index settings
      */
    def $(name: String) = `/_settings/name=$name`(name)
  }

  /**
    * An intermediate class that provides navigation to the filter index settings resource
    */
  case class `tree:/_all/_settings/name=`() {
    /**
      * Returns the filtered resource for getting the index settings
      *
      * @param name The filter glob string vs index names
      * @return The filtered resource for getting the index settings
      */
    def $(name: String) = `/_all/_settings/name=$name`(name)
  }

  /**
    * Read or Update the settings for one or more clusters - intermediate step
    */
  trait `tree:/$indexes/_settings`
  {
    def indexes: Seq[String]
    /**
      * Returns an intermediate class that provides navigation to the filter index settings resource
      *
      * @return An intermediate class that provides navigation to the filter index settings resource
      */
    def name =  `tree:/$indexes/_settings/name=`(indexes:_*)
  }

  /**
    * An intermediate class that provides navigation to the filter index settings resource
    *
    * @param indexes The indexes to read
    */
  case class `tree:/$indexes/_settings/name=`(indexes: String*) {
    /**
      * Returns the filtered resource for getting the index settings
      *
      * @param name The filter glob string vs index names
      * @return The filtered resource for getting the index settings
      */
    def $(name: String) = `/$indexes/_settings/name=$name`(indexes, name)
  }

  // 0.2.12 Clear cache
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * (Intermediate step)
    */
  case class `tree:/_cache`() {
    /**
      * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
      *
      * @return The clear cache resource
      */
    def clear = `/_cache/clear`()
  }

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * (Intermediate step)
    */
  case class `tree:/_all/_cache`() {
    /**
      * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
      *
      * @return The clear cache resource
      */
    def clear = `/_all/_cache/clear`()
  }

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * (Intermediate step)
    */
  case class `tree:/$indexes/_cache`(indexes: String*) {
    /**
      * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
      *
      * @return The clear cache resource
      */
    def clear = `/$indexes/_cache/clear`(indexes:_*)
  }

  /**
    * Intermediate step to various document operations
    */
  trait `tree:/$index/$type/$id`
  {
    def index: String
    def `type`: String
    def id: String

    /**
      * The _update resource
      *
      * @return The _update resource
      */
    def _update = `/$index/$type/$id/_update`(index, `type`, id)

    /**
      * The _update resource
      *
      * @return The _update resource
      */
    def _source = `/$index/$type/$id/_source`(index, `type`, id)

    /**
      * The term vectors resource
      *
      * @return The term vectors resource
      */
    def _termvectors = `/$index/$type/$id/_termvectors`(index, `type`, id)
  }

  /**
    * Intermediate step to various template operations
    */
  class `tree:/_template`
  {
    /**
      * Returns the index template resource for this template
      *
      * @param template The template name
      * @return The index template resource for this template
      */
    def $(template: String) = `/_template/$template`(template: String)

    /**
      * Returns the read index template resource for multiple indexes
      *
      * @param firstTemplate The 1st template name (must be 2+ across all params)
      * @param secondTemplate The 2nd template name (must be 2+ across all params)
      * @param otherTemplates The other templates name (must be 2+ across all params, ie 0+ here)
      * @return The index template resource for this template
      */
    def $(firstTemplate: String, secondTemplate: String, otherTemplates: String*) =
    `/_template/$templates`(Seq(firstTemplate, secondTemplate) ++ otherTemplates:_*)
  }

  // 0.3.8 Indices stats
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html

  /**
    * Intermediate step for navigating index statistics resources
    */
  trait `tree:/_stats`
  {
    /**
      * Restricts the list to the specified statistics
      *
      * @param statsGroups The list of statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(statsGroups: String*) = `/_stats/$statsGroups`(statsGroups:_*)
  }

  /**
    * Intermediate step for navigating index statistics resources
    */
  trait `tree:/_stats/$statsGroups`
  {
    def statsGroups: Seq[String]

    /**
      * Restricts the list to the specified fields
      *
      * @param fieldGroups The list of fields for these statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(fieldGroups: String*) = `/_stats/$statsGroups/$fieldGroups`(statsGroups, fieldGroups)
  }

  /**
    * Intermediate step for navigating index statistics resources
    */
  trait `tree:/$indexes/_stats`
  {
    def indexes: Seq[String]

    /**
      * Restricts the list to the specified statistics
      *
      * @param statsGroups The list of statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(statsGroups: String*) = `/$indexes/_stats/$statsGroups`(indexes, statsGroups)
  }

  /**
    * Intermediate step for navigating index statistics resources
    */
  trait `tree:/$indexes/_stats/$statsGroups`
  {
    def indexes: Seq[String]
    def statsGroups: Seq[String]

    /**
      * Restricts the list to the specified fields
      *
      * @param fieldGroups The list of fields for these statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(fieldGroups: String*) =
    `/$indexes/_stats/$statsGroups/$fieldGroups`(indexes, statsGroups, fieldGroups)
  }

  // 0.4 Cluster navigation

  /**
    * An intermediate resource to obtain cluster-related metadata
    */
  case class `tree:/_cluster`() {

    /**
      * Obtain a cluster health resource
      * @return a cluster health resource
      */
    def health = `/_cluster/health`()

    /**
      * Obtain a cluster state resource
      * @return a cluster state resource
      */
    def state = `/_cluster/state`()

    /**
      * Obtain a cluster stats resource
      * @return a cluster stats resource
      */
    def stats = `/_cluster/stats`()

    /**
      * Obtain a cluster pending tasks resource
      * @return a cluster pending tasks resource
      */
    def pending_tasks = `/_cluster/pending_tasks`()

    /**
      * Obtain a cluster reroute resource
      * @return a cluster reroute resource
      */
    def reroute = `/_cluster/reroute`()

    /**
      * Obtain a cluster settings resource
      * @return a cluster settings resource
      */
    def settings = `/_cluster/settings`()
  }

  /**
    * An intermediate resource to obtain cluster-related metadata
    */
  trait `tree:/_cluster/health` {

    /**
      * Obtain a cluster health resource for the specified indexes
      * @param indexes The specified indexes over which to obtain cluster health information
      * @return a cluster health resource for the specified indexes
      */
    def $(indexes: String*) = `/_cluster/health/$indexes`(indexes:_*)
  }

  /**
    * An intermediate resource to obtain cluster-related metadata
    */
  trait `tree:/_cluster/settings` {
    /**
      * Returns a (typed only) view for writing Marvel config via `/_cluster/settings`
      * @return A (typed only) view for writing Marvel config via `/_cluster/settings`
      */
    def `#marvel.agent` = `/_cluster/settings#marvel.agent`()
  }

  /**
    * An intermediate resource to obtain cluster-related metadata
    */
  trait `tree:/_cluster/state` {

    /**
      * Obtain an intermediate resource to get cluster state related information
      * @param metrics A comma separated list of `"version"`, `"master_node"`, `"nodes"`, `"routing_table"`,
      *                `"metadata"`, `"blocks"`
      * @return an intermediate cluster state resource for the specified metrics
      */
    def $(metrics: String*) = `/_cluster/state/$metrics`(metrics:_*)

    /**
      * Obtain an intermediate resource to get cluster state related information
      * @return an intermediate cluster state resource for all metrics
      */
    def _all = `tree:/_cluster/state/_all`()
  }

  /**
    * An intermediate resource to obtain cluster-related metadata
    */
  case class `tree:/_cluster/state/_all`() {

    /**
      * Obtain a resource to get cluster state related information
      * @param indexes The indexes over which to restrict the state request
      * @return a cluster health resource for the specified indexes, all metrics
      */
    def $(indexes: String*) = `/_cluster/state/_all/$indexes`(indexes:_*)
  }

  /**
    * An intermediate resource to obtain cluster-related metadata
    */
  trait `tree:/_cluster/state/$metrics` {
    def metrics: Seq[String]

    /**
      * Obtain a resource to get cluster state related information
      * @param indexes The indexes over which to restrict the state request
      * @return a cluster health resource for the specified metrics and indexes
      */
    def $(indexes: String*) = `/_cluster/state/$metrics/$indexes`(metrics, indexes)
  }

  // 0.5 Node navigation

  /**
    * An intermediate resource to obtain cluster-node-related metadata
    */
  trait `tree:/_nodes` {

    /**
      * Get a resource to retrieve node statistics
      * @return A resource to retrieve node statistics
      */
    def stats = `/_nodes/stats`()

    /**
      * Get an intermediate step to get a resource to retrieve node info and statistics for specified nodes
      * @param nodes The list of nodes to which to restrict the stats request
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster.html (Node formats)]]
      * @return An intermediate step to get a resource to retrieve node statistics or info for specified nodes
      */
    def $(nodes: String*) = `/_nodes/$nodes`(nodes:_*)

    /**
      * Get an intermediate step to get a resource to retrieve node info over all nodes (specified info groups)
      * @return intermediate step to get a resource to retrieve node info over all nodes (specified info groups)
      */
    def _all = `tree:/_nodes/_all`()

    /**
      * Get a resource for analyzing hot threads in nodes
      * @return A resource for analyzing hot threads in nodes
      */
    def hot_threads = `/_nodes/hot_threads`()
  }

  /**
    * An intermediate resource to obtain cluster-node-related metadata
    */
  case class `tree:/_nodes/_all`() {

    /**
      * Get a resource to retrieve node info over the all nodes, specified info groups
      * @param infoGroups The info groups to obtain
      *                    Groups: `settings`, `os`, `process`, `jvm`, `thread_pool`, `transport`,
      *                    `http` and `plugins`
      * @return A resource to retrieve node statistics over the specified nodes
      */
    def $(infoGroups: String*) = `/_nodes/_all/$infoGroups`(infoGroups:_*)
  }

  /**
    * An intermediate resource to obtain cluster-node-related metadata
    */
  trait `tree:/_nodes/stats` {

    /**
      * Returns a resource to get the specified node statistics only
      * @param statsGroups The statistics groups to obtain
      *                    Groups: `indices`, `os`, `process`, `jvm`, `transport`, `http`,
      *                    `fs`, `breaker` and `thread_pool`
      * @return The resource to obtain the node statistics
      */
    def $(statsGroups: String*) = `/_nodes/stats/$statsGroups`(statsGroups:_*)
  }

  /**
    * An intermediate resource to obtain cluster-node-related metadata
    */
  trait `tree:/_nodes/$nodes` {
    def nodes: Seq[String]
    /**
      * Get a resource to retrieve node statistics over the specified nodes
      * @return A resource to retrieve node statistics over the specified nodes
      */
    def stats = `/_nodes/$nodes/stats`(nodes:_*)

    /**
      * Get a resource to retrieve node info over the specified nodes and info groups
      * @param infoGroups The info groups to obtain
      *                    Groups: `settings`, `os`, `process`, `jvm`, `thread_pool`, `transport`,
      *                    `http` and `plugins`
      * @return A resource to retrieve node statistics over the specified nodes
      */
    def $(infoGroups: String*) = `/_nodes/$nodes/$infoGroups`(nodes, infoGroups)

    /**
      * Get a resource for analyzing hot threads in nodes
      * @return A resource for analyzing hot threads in nodes
      */
    def hot_threads = `/_nodes/$nodes/hot_threads`(nodes:_*)
  }

  /**
    * An intermediate resource to obtain cluster-node-related metadata
    */
  trait `tree:/_nodes/$nodes/stats` {
    def nodes: Seq[String]

    /**
      * Returns a resource to get the specified node statistics only
      * @param statsGroups The statistics groups to obtain
      *                    Groups: `indices`, `os`, `process`, `jvm`, `transport`, `http`,
      *                    `fs`, `breaker` and `thread_pool`
      * @return The resource to obtain the node statistics
      */
    def $(statsGroups: String*) = `/_nodes/$nodes/stats/$statsGroups`(nodes, statsGroups)
  }

  // 0.5.4 Task management API

  /**
    * An intermediate resource to obtain task info
    */
  trait `tree:/_tasks` {

    /**
      * Obtains a resource for task management info (/navigation to task cancellation)
      * @param taskId The task id for which to request info/cancellation
      * @return A resource for task management info (/navigation to task cancellation)
      */
    def $(taskId: String) = `/_tasks/$taskId`(taskId)

    /**
      * Obtains a resource to cancel multiple tasks
      * @return A resource to cancel multiple tasks
      */
    def _cancel = `/_tasks/_cancel`()
  }

  /**
    * An intermediate resource to obtain a task cancellation resource
    */
  trait `tree:/_tasks/$taskId` {
    def taskId: String

    /**
      * Obtains a resource to cancel a single task
      * @return A resource to cancel a single task
      */
    def _cancel = `/_tasks/$taskId/_cancel`(taskId)
  }

  /**
    * An intermediate resource to perform a search given a template
    */
  trait `tree:/_search/template` {

    /**
      * A resource to perform a search given a template
      * @param template The name of the template
      * @return A resource to perform a search given a template
      */
    def $(template: String) = `/_search/template/$template`(template)
  }

  // 0.6 X-pack navigation tree

  // 0.6.1 Monitoring

  // 0.6.2 Security

  /**
    * An intermediate resource to obtain security related settings
    */
  trait `tree:/_shield` {

    /**
      * The Authenticate API enables you to submit a request with a basic auth header to authenticate a user and
      * retrieve information about the authenticated user. Returns a 401 status code if the user cannot be
      * authenticated.
      * @return The authenticate resource
      */
    def authenticate = `/_shield/authenticate`()

    /**
      * An intermediate step to clear a realm's cache
      * @return An intermediate step to clear a realm's cache
      */
    def realm = `tree:/_shield/realm`()

    /**
      * An intermediate step to a set of resources for managing users in elasticsearch
      * @return An intermediate step to a set of resources for managing users in elasticsearch
      */
    def user = `/_shield/user`()

    /**
      * An intermediate step to a set of resources for managing roles in elasticsearch
      * @return An intermediate step to a set of resources for managing roles in elasticsearch
      */
    def role = `/_shield/role`()
  }

  /**
    * An intermediate step to clear a realm's cache
    */
  case class `tree:/_shield/realm`() {

    def $(realms: String*) = `tree:/_shield/realm/$realms`(realms)
  }

  /**
    * An intermediate step to clear a realm's cache
    */
  case class `tree:/_shield/realm/$realms`(realms: String*) {
    /**
      * The Clear Cache API evicts users from the user cache. You can completely clear the cache or evict specific
      * users.
      * @return
      */
    def _clear_cache = `/_shield/realm/$realms/_clear_cache`(realms)
  }

  /**
    * An intermediate resource to obtain security related settings
    */
  case class `tree:/_shield/user`() {

    /**
      * The Users API enables you to create, read, update, and delete users from the native realm. These users are
      * commonly referred to as native users. To use this API, you must have at least the manage_security cluster
      * privilege.
      * @param username The user to manage
      * @return The user management resource
      */
    def $(username: String) = `/_shield/user/$username`(username)

    /**
      * Get information about elasticsearch users (>1 user)
      * @param user1 The first user about which to retrieve info
      * @param user2 The second user about which to retrieve info
      * @param otherUsers The third+ user about which to retrieve info
      * @return
      */
    def $(user1: String, user2: String, otherUsers: String*) =
      `/_shield/user/$usernames`(Seq(user1, user2) ++ otherUsers:_*)
  }

  /**
    * An intermediate resource to obtain security related settings
    */
  case class `tree:/_shield/role`() {

    /**
      * The Roles API enables you to add, remove, and retrieve roles in the native Shield realm. To use this API,
      * you must have at least the manage_security cluster privilege.
      * @param role The role to manage
      * @return The user management resource
      */
    def $(role: String) = `/_shield/role/$role`(role)

    /**
      * Get information about elasticsearch roles (>1 role)
      * @param role1 The first role about which to retrieve info
      * @param role2 The second role about which to retrieve info
      * @param otherRoles The third+ role about which to retrieve info
      * @return
      */
    def $(role1: String, role2: String, otherRoles: String*) =
     `/_shield/role/$roles`(Seq(role1, role2) ++ otherRoles:_*)
  }

  // 0.6.3 Licenses

  // 0.6.4 Snapshots

  //TODO: pretty sure I have the snapshot API wrong:
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html

  /**
    * An intermediate resource to obtain snapshot related settings
    */
  trait `tree:/_snapshot` {
    /**
      * A resource to return information about all snapshots
      * @return A resource to return information about all snapshots
      */
    def _all = `/_snapshot/_all`()

    /**
      * A resource to manage the specified snapshot
      * @param snapshotRepo The name of the snapshot to manage
      * @return
      */
    def $(snapshotRepo: String) = `/_snapshot/$snapshotRepo`(snapshotRepo)

    /**
      * A resource to return information about specified snapshot repos
      * @param snapshotRepo1 The first snapshot repo about which to retrieve info
      * @param snapshotRepo2 The second snapshot repo about which to retrieve info
      * @param otherSnapshotRepos The third+ snapshot repos about which to retrieve info
      * @return A resource to return information about specified snapshots
      */
    def $(snapshotRepo1: String, snapshotRepo2: String, otherSnapshotRepos: String*) =
      `/_snapshot/$snapshotRepos`(Seq(snapshotRepo1, snapshotRepo2) ++ otherSnapshotRepos:_*)
  }

  /**
    * An intermediate resource to obtain snapshot repo related settings
    */
  trait `tree:/_snapshot/$snapshotRepo` {
    val snapshotRepo: String

    /**
      * When a repository is registered, it’s immediately verified on all master and data nodes to make sure that it is
      * functional on all nodes currently present in the cluster. This manual verification returns a list of nodes
      * where repository was successfully verified or an error message if verification process failed.
      * @return The snapshot repo verification resource
      */
    def _verify = `/_snapshot/$snapshotRepo/_verify`(snapshotRepo)
  }

  // 0.6.5 Watcher (alerting)

  /**
    * An intermediate resource to obtain watcher related settings
    */
  trait `tree:/_watcher` {

    /**
      * An intermediate resource to obtain watcher related settings
      * @return An intermediate resource to obtain watcher related settings
      */
    def watch = `tree:/_watcher/watch`()

    /**
      * A resource to obtain watcher related statistics
      * @return A resource to obtain watcher related statistics
      */
    def stats = `/_watcher/stats`()

    /**
      * A resource to start a stopped watcher
      * @return A resource to start a stopped watcher
      */
    def _start = `/_watcher/_start`()

    /**
      * A resource to stop a started watcher
      * @return A resource to stop a started watcher
      */
    def _stop = `/_watcher/_stop`()

    /**
      * A resource to restart a started watcher
      * @return A resource to restart a started watcher
      */
    def _restart = `/_watcher/_restart`()
  }

  /**
    * An intermediate resource to obtain watcher related settings
    */
  case class `tree:/_watcher/watch`() {
    /**
      * Returns an intermediate to manage a specific watch in watcher
      * @param watchName The name of the watch to manage
      * @return Returns an intermediate to manage a specific watch in watcher
      */
    def $(watchName: String) = `/_watcher/watch/$watchName`(watchName)
  }

  /**
    * An intermediate resource to obtain watcher related settings
    */
  trait `tree:/_watcher/watch/$watchName` {
    val watchName: String

    /** The execute watch API forces the execution of a stored watch. It can be used to force execution of the watch
      * outside of its triggering logic, or to test the watch for debugging purposes.
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-execute-watch Docs]]
      * @return A resource to execute a watch
      */
    def _execute = `/_watcher/watch/$watchName/_execute`(watchName)

    /** Acknowledging a watch enables you to manually throttle execution of the watch’s actions.
      * An action’s acknowledgement state is stored in the `_status.actions.id.ack.state structure`.
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-ack-watch Docs]]
      * @return A resource to ack a watch
      */
    def _ack = `/_watcher/watch/$watchName/_ack`(watchName)

    /** A watch can be either active or inactive. This API enables you to activate a currently inactive watch.
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-activate-watch Docs]]
      * @return A resource to activate a watch
      */
    def _activate = `/_watcher/watch/$watchName/_activate`(watchName)

    /** A watch can be either active or inactive. This API enables you to deactivate a currently active watch.
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-activate-watch Docs]]
      * @return A resource to deactivate a watch
      */
    def _deactivate = `/_watcher/watch/$watchName/_deactivate`(watchName)
  }

  /**
    * An intermediate resource to obtain watcher related statistics settings
    */
  trait `tree:/_watcher/stats` {

    /** The watcher stats API returns information on the aspects of watcher on your cluster.
      * This resource returns against one of the following metrics:
      * "queued_watches", "current_watches", "executing_watches", "_all"
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#_current_executing_watches_metric Docs]]
      * @param metric The metric stats to achieve
      * @return A metric-specific watcher statistics resource
      */
    def $(metric: String) = `/_watcher/stats/$metric`(metric: String)
  }
}
