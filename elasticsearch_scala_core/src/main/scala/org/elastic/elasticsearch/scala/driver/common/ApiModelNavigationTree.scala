package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.ApiModelCommon._
import org.elastic.elasticsearch.scala.driver.common.ApiModelSearch._
import org.elastic.elasticsearch.scala.driver.common.ApiModelIndices._

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
    def $(index: String) = `/$uri`(index)

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
    def _template = new `tree:/_template`()

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
    def $(fields: String*) = `/_mapping/$types/field/$fields`(types:_*)(fields:_*)
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
    def $(fields: String*) = `/_all/_mapping/$types/field/$fields`(types:_*)(fields:_*)
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
    def $(fields: String*) = `/$indexes/_mapping/field/$fields`(indexes:_*)(fields:_*)
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
    def $(fields: String*) = `/$indexes/_mapping/$types/field/$fields`(indexes:_*)(types:_*)(fields:_*)
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
    def $(aliases: String) = `/_alias/$aliases`(aliases)
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
    def $(alias: String) = `/$indexes/_alias/$alias`(indexes:_*)(alias)
    /**
      * Returns the check/retrieve _alias resource for the specified aliases
      *
      * @param firstAlias The first specified alias name (must have >=1, ie 2+ in total)
      * @param secondAlias The first specified alias name (must have >=1, ie 2+ in total)
      * @param otherAliases Subsequent specified aliases names (must have >0)
      * @return
      */
    def $(firstAlias: String, secondAlias: String, otherAliases: String*) =
      `/$indexes/_alias/$aliases`(indexes:_*)(Seq(firstAlias, secondAlias) ++ otherAliases:_*)

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
    def $(name: String) = `/$indexes/_settings/name=$name`(indexes:_*)(name)
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
}
