package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.OperationGroups._
import org.elastic.elasticsearch.driver.scala.ElasticsearchBase._

/**
  * A set of DSLs representing the Elasticsearch resources
  */
object ApiModel_common {

  // 0 Intermediate steps

  // 0.0 Root, indexes, and types (common across much of the resource set)

  /**
    * The root node of the Elasticsearch resource tree
    * Will return very basic information about a cluster, and
    * is also a starting point for navigating the hierarchy
    */
  case class `/`()
    extends SimpleReadable
    with EsResource
  {
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
    def $(index: String, otherIndexes: String) = `/$indexes`(index, otherIndexes)

    /**
      * The _all index wildcard of the search resource
      *
      * @return The search resource
      */
    def _all = `/_all`()

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
    def _render = `/_render`()

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
    def _alias = `/_alias`()

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
    def _cache = `/_cache`()

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
    * Utility methods on the root Elasticsearch resource
    */
  object `/` {
    /**
      * Builds
      *
      * @param resource The resource on which to operate (minus the leading /)
      * @return The operatable resource
      */
    def apply(resource: String) = new RawOperatableResource(s"/$resource")
  }

  /**
    * An intermediate step to resources that support multiple (>1) indexes
    *
    * @param index The first index
    * @param otherIndexes Subsequent indexes
    */
  case class `/$indexes`(index: String, otherIndexes: String*) {

    /**
      * An intermediate step to resources that support multiple (>1) indexes and (>0) types
      *
      * @param types The types
      */
    def $(types: String*) = `/$indexes/$types`(index)(types:_*)

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
    def _alias = `/$indexes/_alias`(Seq(index) ++ otherIndexes:_*)

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
    def _cache = `/$indexes/_cache`(Seq(index) ++ otherIndexes:_*)

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
    *
    * @param index The index
    */
  case class `/$index`(index: String)
    extends SimpleReadable with SimpleDeletable with SimpleWritable with SimpleCheckable
      with EsResource
  {
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
    def _alias = `/$indexes/_alias`(index)

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
    def _cache = `/$indexes/_cache`(index)

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
  case class `/_all`() {
    /**
      * Restricts the search to a list of types
      *
      * @param types The list of types
      * @return An intermediate search step
      */
    def $(types: String*) = `/_all/$types`(types:_*)

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
    def _alias = `/_all/_alias`()

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
    def _cache = `/_all/_cache`()

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
    * Can also be checked to see if the indexes/indexes and type/types exist
    *
    * @param indexes The indexes
    * @param types The types
    */
  case class `/$indexes/$types`(indexes: String*)(types: String*)
  extends SimpleCheckable with EsResource
  {
    /**
      * A search over specified indexes and types
      *
      * @return The search resource
      */
    def _search = `/$indexes/$types/_search`(indexes:_*)(types:_*)

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/$indexes/$types/_msearch`(indexes:_*)(types:_*)

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/$indexes/$types/_count`(indexes:_*)(types:_*)

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/$indexes/$types/_validate`(indexes:_*)(types:_*)

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/$indexes/$types/_explain`(indexes:_*)(types:_*)

    /**
      * Suggests search terms over the specified indexes and types
      *
      * @return The suggest resource
      */
    def _suggest = `/$indexes/$types/_suggest`(indexes:_*)(types:_*)
  }

  /**
    * Performs activities on the specified index, type, and automatically generated id:
    * - `write`: adds the written object to the index/type
    * Also acts as an intermediate step to navigate to other resources
    *
    * @param index The index
    * @param `type` The type
    */
  case class `/$index/$type`(index: String, `type`: String)
    extends FullyModifiableWritable with SimpleCheckable
      with EsResource
  {
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
    def _search = `/$indexes/$types/_search`(index)(`type`)

    /**
      * The multi search resource
      *
      * @return The multi search resource
      */
    def _msearch = `/$indexes/$types/_msearch`(index)(`type`)

    /**
      * The search-and-count resource
      *
      * @return The search and count resource
      */
    def _count = `/$indexes/$types/_count`(index)(`type`)

    /**
      * The query validation resource
      *
      * @return The query validation resource
      */
    def _validate = `/$indexes/$types/_validate`(index)(`type`)

    /**
      * The query explanation resource
      *
      * @return The query explanation resource
      */
    def _explain = `/$indexes/$types/_explain`(index)(`type`)
    /**
      * The search suggest resource
      *
      * @return The search suggest resource
      */
    def _suggest = `/$indexes/$types/_suggest`(index)(`type`)
  }

  /**
    * An intermediate step to search all indexes
    * but a subset of types
    */
  case class `/_all/$types`(types: String*) {
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

  // 0.3.2 Search Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html

  /**
    * Intermediate class to render search templates
    */
  case class `/_render`() {
    def template = `/_render/template`()
  }

  // 0.3.3 Field mapping intermediate steps
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param indexes The indexes over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/field`(indexes: String*) {
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
  case class `/$indexes/_mapping/$types/field`(indexes: String*)(types: String*) {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/$indexes/_mapping/$types/field/$fields`(indexes:_*)(types:_*)(fields:_*)
  }
  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `/_mapping/field`() {
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
  case class `/_mapping/$types/field`(types: String*) {
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
  case class `/_all/_mapping/field`() {
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
  case class `/_all/_mapping/$types/field`(types: String*) {
    /**
      * Returns the get field mapping for these fields (+parent restrictions)
      *
      * @param fields The set of fields for which to retrieve the mapping
      * @return The get field mapping resource
      */
    def $(fields: String*) = `/_all/_mapping/$types/field/$fields`(types:_*)(fields:_*)
  }

  // 0.3.4 Index aliases
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html

  /**
    * An intermediate object for navigating to alias related resources
    */
  case class `/_alias`() {
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
  case class `/$indexes/_alias`(indexes: String*) {
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
  case class `/_all/_alias`() {
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

  /**
    * An intermediate class that provides navigation to the filter index settings resource
    */
  case class `/_settings/name=`() {
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
  case class `/_all/_settings/name=`() {
    /**
      * Returns the filtered resource for getting the index settings
      *
      * @param name The filter glob string vs index names
      * @return The filtered resource for getting the index settings
      */
    def $(name: String) = `/_all/_settings/name=$name`(name)
  }

  /**
    * An intermediate class that provides navigation to the filter index settings resource
    *
    * @param indexes The indexes to read
    */
  case class `/$indexes/_settings/name=`(indexes: String*) {
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
  case class `/_cache`() {
    /**
      * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
      * @return The clear cache resource
      */
    def clear = `/_cache/clear`()
  }

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * (Intermediate step)
    */
  case class `/_all/_cache`() {
    /**
      * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
      * @return The clear cache resource
      */
    def clear = `/_all/_cache/clear`()
  }

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * (Intermediate step)
    */
  case class `/$indexes/_cache`(indexes: String*) {
    /**
      * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
      * @return The clear cache resource
      */
    def clear = `/$indexes/_cache/clear`(indexes:_*)
  }

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
    extends FullyModifiableReadable with SimpleCheckable
      with FullyModifiableWritable
      with FullyModifiableDeletable
      with EsResource
  {
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
  {
  }

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
  {
  }

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
    *
    * @param index The index
    */
  case class `/$index/_mget`(index: String)
    extends RoutableSimpleReadable
      with EsResource
  {
  }

  /**
    * Multi gets from the specified index and type based on the object written to the resource
    *
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
    *
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
    *
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
    *
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
    *
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
    *
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
    *
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
    extends QueryUriReadable with QueryWithDataReadable
      with EsResource
  {
    /**
      * An intermediate result to use templates to search
      *
      * @return Templated search resource
      */
    def template = `/_search/template`()
  }

  /**
    * Search all indexes and the specified types, based on the query object written to the
    * resource
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_search`(types: String*)
    extends QueryUriReadable with QueryWithDataReadable
      with EsResource
  {
  }

  /**
    * Search the specified indexes and all types, based on the query object written to the
    * resource
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_search`(indexes: String*)
    extends QueryUriReadable with QueryWithDataReadable
      with EsResource
  {
  }

  /**
    * Search the specified indexes and types, based on the query object written to the
    * resource
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_search`(indexes: String*)(types: String*)
    extends QueryUriReadable with QueryWithDataReadable
      with EsResource
  {
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
    *
    * @param indexes The index or indexes to query
    */
  case class `/$indexes/_search_shards`(indexes: String*)
    extends QuerySearchShardsReadable
    with EsResource
  {
  }

  // 2.4 Suggesters
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    */
  case class `/_suggest`()
    extends QueryMiscUriReadable with QueryMiscWithDataReadable
      with EsResource
  {
  }

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    *
    * @param indexes The indexes over which to query
    */
  case class `/$indexes/_suggest`(indexes: String*)
    extends QueryMiscUriReadable with QueryMiscWithDataReadable
      with EsResource
  {
  }

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    *
    * @param types The types over which to query
    */
  case class `/_all/$types/_suggest`(types: String*)
    extends QueryMiscUriReadable with QueryMiscWithDataReadable
      with EsResource
  {
  }

  /**
    * The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    *
    * @param indexes The indexes over which to query
    * @param types The types over which to query
    */
  case class `/$indexes/$types/_suggest`(indexes: String*)(types: String*)
    extends QueryMiscUriReadable with QueryMiscWithDataReadable
      with EsResource
  {
  }

  // 2.5 Multi search
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html

  /**
    * Search all indexes and all types, based on the query objects written to the
    * resource
    */
  case class `/_msearch`()
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO: typed API for this
  }

  /**
    * Search all indexes and the specified types, based on the query objects written to the
    * resource
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_msearch`(types: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO: typed API for this
  }

  /**
    * Search the specified indexes and all types, based on the query objects written to the
    * resource
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_msearch`(indexes: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO: typed API for this
  }

  /**
    * Search the specified indexes and types, based on the query objects written to the
    * resource
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_msearch`(indexes: String*)(types: String*)
    extends SimpleWithDataReadable
      with EsResource
  {
    //TODO: typed API for this
  }

  // 2.6 Count API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html

  /**
    * Count all indexes and all types, based on the query object written to the
    * resource
    */
  case class `/_count`()
    extends QueryCountUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  /**
    * Count all indexes and the specified types, based on the query object written to the
    * resource
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_count`(types: String*)
    extends QueryCountUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  /**
    * Count the specified indexes and all types, based on the query object written to the
    * resource
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_count`(indexes: String*)
    extends QueryCountUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  /**
    * Count the specified indexes and types, based on the query object written to the
    * resource
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_count`(indexes: String*)(types: String*)
    extends QueryCountUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  // 2.7 Validate API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html

  /**
    * Validate the query over the specified indexes and types
    */
  case class `/_validate`()
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  /**
    * Validate the query over the specified indexes and types
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_validate`(types: String*)
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  /**
    * Validate the query over the specified indexes and types
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_validate`(indexes: String*)
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  /**
    * Validate the query over the specified indexes and types
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_validate`(indexes: String*)(types: String*)
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with EsResource
  {
  }

  // 2.8 Explain API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html

  /**
    * Explain the query over the specified indexes and types
    */
  case class `/_explain`()
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with EsResource
  {
  }

  /**
    * Explain the query over the specified indexes and types
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_explain`(types: String*)
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with EsResource
  {
  }

  /**
    * Explain the query over the specified indexes and types
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_explain`(indexes: String*)
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with EsResource
  {
  }

  /**
    * Explain the query over the specified indexes and types
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_explain`(indexes: String*)(types: String*)
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with EsResource
  {
  }

  // 2.9 Percolation API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-percolate.html

  //TODO add this set of operations later on

  // 3 Index operations

  // 3.1 Opening and closing indexes
  //https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html

  /**
    * Open one or more indexes
    *
    * @param indexes The index or indexes to open
    */
  case class `/$indexes/_open`(indexes: String*)
    extends OpenCloseIndexesNoDataWritable
      with EsResource
  {
  }

  /**
    * Open all the indexes
    */
  case class `/_all/_open`()
    extends SimpleNoDataWritable
      with EsResource
  {
  }

  /**
    * Close one or more indexes
    *
    * @param indexes The index or indexes to open
    */
  case class `/$indexes/_close`(indexes: String*)
    extends OpenCloseIndexesNoDataWritable
      with EsResource
  {
  }

  /**
    * Close all the indexes
    */
  case class `/_all/_close`()
    extends SimpleNoDataWritable
      with EsResource
  {
  }

  // 3.2 Mappings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html

  /**
    * Get the mapping for 1+ indexes and all types
    *
    * @param indexes The index or indexes whose mapping to get
    */
  case class `/$indexes/_mapping`(indexes: String*)
    extends SimpleReadable
      with EsResource
  {
    /**
      * Returns the mapping control resource for specified indexes and types
      *
      * @param types The types over which to restrict the mapping operations
      * @return Returns the mapping control resource for specified indexes and types
      */
    def $(types: String*) = `/$indexes/_mapping/$types`(indexes:_*)(types:_*)

    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `/$indexes/_mapping/field`(indexes:_*)
  }

  /**
    * Gets the mapping for 1+ indexes and 1+ types
    *
    * @param indexes The index or indexes whose mapping to get
    * @param types The type or type whose mapping to get
    */
  case class `/$indexes/_mapping/$types`(indexes: String*)(types: String*)
    extends SimpleReadable
      with EsResource
  {
    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `/$indexes/_mapping/$types/field`(indexes:_*)(types:_*)
  }

  /**
    * Gets the mapping for all indexes and all types
    */
  case class `/_mapping`()
    extends SimpleReadable
      with EsResource
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
    def field  = `/_mapping/field`()
  }

  /**
    * Gets the mapping for all indexes and 1+ types
    *
    * @param types The type or type whose mapping to get
    */
  case class `/_mapping/$types`(types: String*)
    extends SimpleReadable
      with EsResource
  {
    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `/_mapping/$types/field`(types:_*)
  }
  /**
    * Gets the mapping for all indexes and all types
    */
  case class `/_all/_mapping`()
    extends SimpleReadable
      with EsResource
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
    def field  = `/_all/_mapping/field`()
  }

  /**
    * Gets the mapping for all indexes and 1+ types
    *
    * @param types The type or type whose mapping to get
    */
  case class `/_all/_mapping/$types`(types: String*)
    extends SimpleReadable
      with EsResource
  {
    /**
      * Returns an intermediate step leading to the get field mapping resources
      *
      * @return An intermediate step leading to the get field mapping resources
      */
    def field  = `/_all/_mapping/$types/field`(types:_*)
  }

  // 3.3 Field mappings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html

  /**
    * A resource to retrieve
    *
    * @param indexes The indexes over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/field/$fields`(indexes: String*)(fields: String*)
    extends SimpleReadable
      with EsResource

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param indexes The indexes over which the field mappings are retrieved
    * @param types The types over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/$types/field/$fields`(indexes: String*)(types: String*)(fields: String*)
    extends SimpleReadable
      with EsResource

  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `/_mapping/field/$fields`(fields: String*)
    extends SimpleReadable
      with EsResource

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `/_mapping/$types/field/$fields`(types: String*)(fields: String*)
    extends SimpleReadable
      with EsResource

  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `/_all/_mapping/field/$fields`(fields: String*)
    extends SimpleReadable
      with EsResource

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `/_all/_mapping/$types/field/$fields`(types: String*)(fields: String*)
    extends SimpleReadable
      with EsResource

  // 3.4 Index aliases
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html

  /**
    * Create one or more aliases based on the posted content
    */
  case class `/_aliases`()
    extends SimpleWritable
      with EsResource

  /**
    * Retrieve/check the aliases across all aliases
    *
    * @param aliases The names of the aliases (including globs)
    */
  case class `/_alias/$aliases`(aliases: String)
    extends SimpleReadable
      with SimpleCheckable
      with EsResource

  /**
    * Retrieve/check all aliases across all aliases
    */
  case class `/_alias/*`()
    extends SimpleReadable
      with SimpleCheckable
      with EsResource

  /**
    * Create/delete/retrieve a mapping from a single alias to the specified index(es)
    *
    * @param indexes The list of indexes (including globs)
    * @param alias The specific alias
    */
  case class `/$indexes/_alias/$alias`(indexes: String*)(alias: String)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleNoDataWritable
      with SimpleDeletable
      with EsResource

  /**
    * Delete/retrieve mappings from a multiple aliases to the specified index(es)
    *
    * @param indexes The list of indexes (including globs)
    * @param aliases The names of the aliases (including globs)
    */
  case class `/$indexes/_alias/$aliases`(indexes: String*)(aliases: String*)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with EsResource

  /**
    * Delete/retrieve mappings from a multiple aliases to the specified index(es)
    *
    * @param indexes The list of indexes (including globs)
    */
  case class `/$indexes/_alias/*`(indexes: String*)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with EsResource

  /**
    * Create/delete/retrieve a mapping from single alias to all the indexes
    *
    * @param alias The specific alias
    */
  case class `/_all/_alias/$alias`(alias: String)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleNoDataWritable
      with SimpleDeletable
      with EsResource

  /**
    * Ddelete/retrieve a mapping from multiple aliases to all the indexes
    *
    * @param aliases The names of the aliases (including globs)
    */
  case class `/_all/_alias/$aliases`(aliases: String*)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with EsResource

  /**
    * Delete/retrieve a mapping from all the aliases to all the indexes
    */
  case class `/_all/_alias/*`()
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with EsResource

  // 3.5 Get and Update index settings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html

  /**
    * Read or Update the settings for the entire cluster
    */
  case class `/_settings`()
    extends SimpleReadable
      with SimpleWritable
      with EsResource
  {
    /**
      * Returns an intermediate class that provides navigation to the filter index settings resource
      *
      * @return An intermediate class that provides navigation to the filter index settings resource
      */
    def name =  `/_settings/name=`()
  }

  /**
    * Read the filtered settings for the entire cluster
    *
    * @param name The filter glob string vs index names
    */
  case class `/_settings/name=$name`(name: String)
    extends SimpleReadable
      with EsResource

  /**
    * Read or Update the settings for the entire cluster
    */
  case class `/_all/_settings`()
    extends SimpleReadable
      with SimpleWritable
      with EsResource
  {
    /**
      * Returns an intermediate class that provides navigation to the filter index settings resource
      *
      * @return An intermediate class that provides navigation to the filter index settings resource
      */
    def name =  `/_all/_settings/name=`()
  }

  /**
    * Read the filtered settings for the entire cluster
    *
    * @param name The filter glob string vs index names
    */
  case class `/_all/_settings/name=$name`(name: String)
    extends SimpleReadable
      with EsResource

  /**
    * Read or Update the settings for one or more clusters
    *
    * @param indexes The indexes to read/update
    */
  case class `/$indexes/_settings`(indexes: String*)
    extends SimpleReadable
      with SimpleWritable
      with EsResource
  {
    /**
      * Returns an intermediate class that provides navigation to the filter index settings resource
      *
      * @return An intermediate class that provides navigation to the filter index settings resource
      */
    def name =  `/$indexes/_settings/name=`(indexes:_*)
  }
  /**
    * Read the filtered settings for the entire cluster
    *
    * @param indexes The indexes to read
    * @param name The filter glob string vs index names
    */
  case class `/$indexes/_settings/name=$name`(indexes: String*)(name: String)
    extends SimpleReadable
      with EsResource

  // 3.6 Analyze
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html

  /**
    * Analyze some text against any of the analyzers registered vs the cluster
    */
  case class `/_analyze`()
    extends SimpleWithDataReadable
      with EsResource

  /**
    * Analyze some text against any of the analyzers registered vs the index
    *
    * @param index The index against which to run the analysis
    */
  case class `/$index/_analyze`(index: String)
    extends SimpleWithDataReadable
      with EsResource

  // 3.7 Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html

  /**
    * An intermediate class to get to the index templates resource
    * Also lists all of the index templates
    */
  case class `/_template`()
    extends SimpleReadable
      with EsResource
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

  /**
    * The create/read/update/delete resource for this template
    *
    * @param template The template name
    */
  case class `/_template/$template`(template: String)
    extends SimpleReadable
      with SimpleWritable
      with SimpleDeletable
      with SimpleCheckable
      with EsResource

  /**
    * The read resource for multuple templates
    *
    * @param templates The template names
    */
  case class `/_template/$templates`(templates: String*)
    extends SimpleReadable
      with EsResource

  // 3.8 Indices stats
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html

  /**
    * Returns high level aggregation and index level stats for all indices
    */
  case class `/_stats`()
    extends IndexStatsReadable
    with EsResource
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
    * Returns high level aggregation and index level stats for all indices and specific stats groups
    *
    * @param statsGroups The list of statistics groups
    */
  case class `/_stats/$statsGroups`(statsGroups: String*)
    extends IndexStatsReadable
      with EsResource
  {
    /**
      * Restricts the list to the specified fields
      *
      * @param fieldGroups The list of fields for these statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(fieldGroups: String*) = `/_stats/$statsGroups/$fieldGroups`(statsGroups:_*)(fieldGroups:_*)
  }

  /**
    * Returns high level aggregation and index level stats for all indices and specific stats groups
    * and with filtered fields
    *
    * @param statsGroups The list of statistics groups
    * @param fieldGroups The list of fields for these statistics groups
    */
  case class `/_stats/$statsGroups/$fieldGroups`(statsGroups: String*)(fieldGroups: String*)
    extends IndexStatsReadable
      with EsResource

  /**
    * Returns high level aggregation and index level stats for specified indices
    *
    * @param indexes The indexes over which to restrict the stats
    */
  case class `/$indexes/_stats`(indexes: String*)
    extends IndexStatsReadable
      with EsResource
  {
    /**
      * Restricts the list to the specified statistics
      *
      * @param statsGroups The list of statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(statsGroups: String*) = `/$indexes/_stats/$statsGroups`(indexes:_*)(statsGroups:_*)
  }

  /**
    * Returns high level aggregation and index level stats for specified indices and specific stats groups
    *
    * @param indexes The indexes over which to restrict the stats
    * @param statsGroups The list of statistics groups
    */
  case class `/$indexes/_stats/$statsGroups`(indexes: String*)(statsGroups: String*)
    extends IndexStatsReadable
      with EsResource
  {
    /**
      * Restricts the list to the specified fields
      *
      * @param fieldGroups The list of fields for these statistics groups
      * @return The resource restricted to the set of specified stats groups
      */
    def $(fieldGroups: String*) =
      `/$indexes/_stats/$statsGroups/$fieldGroups`(indexes:_*)(statsGroups:_*)(fieldGroups:_*)
  }

  /**
    * Returns high level aggregation and index level stats for specified indices and specific stats groups
    * and with filtered fields
    *
    * @param indexes The indexes over which to restrict the stats
    * @param statsGroups The list of statistics groups
    * @param fieldGroups The list of fields for these statistics groups
    */
  case class `/$indexes/_stats/$statsGroups/$fieldGroups`
    (indexes: String*)(statsGroups: String*)(fieldGroups: String*)
    extends IndexStatsReadable
      with EsResource

  // 3.9 Segments
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html

  /**
    * Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    */
  case class `/_segments`()
    extends VerboseSimpleReadable
      with EsResource

  /**
    * Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    */
  case class `/_all/_segments`()
    extends VerboseSimpleReadable
      with EsResource

  /**
    * Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    * @param indexes The indexes over which to check the segment information
    */
  case class `/$indexes/_segments`(indexes: String*)
    extends VerboseSimpleReadable
      with EsResource

  // 3.10 Indices recovery
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html

  /**
    * The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    */
  case class `/_recovery`()
    extends IndexRecoveryReadable
      with EsResource

  /**
    * The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    */
  case class `/_all/_recovery`()
    extends IndexRecoveryReadable
      with EsResource

  /**
    * The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    * @param indexes The indexes over which to check the segment information
    */
  case class `/$indexes/_recovery`(indexes: String*)
    extends IndexRecoveryReadable
      with EsResource

  // 2.11 Shard stores
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html

  /**
    * Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    */
  case class `/_shard_stores`()
    extends ShardStoreReadable
      with EsResource

  /**
    * Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    */
  case class `/_all/_shard_stores`()
    extends ShardStoreReadable
      with EsResource

  /**
    * Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_shard_stores`(indexes: String*)
    extends ShardStoreReadable
      with EsResource

  // 2.12 Clear cache
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    */
  case class `/_cache/clear`()
    extends ShardStoreReadable
      with EsResource

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    */
  case class `/_all/_cache/clear`()
    extends ShardStoreReadable
      with EsResource

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_cache/clear`(indexes: String*)
    extends ShardStoreReadable
      with EsResource

  // 2.13 Flush
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html

  /**
    * The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    */
  case class `/_flush`()
    extends FlushNoDataWritable
      with EsResource

  /**
    * The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    */
  case class `/_all/_flush`()
    extends FlushNoDataWritable
      with EsResource

  /**
    * The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_flush`(indexes: String*)
    extends FlushNoDataWritable
      with EsResource

  // 2.14 Refresh
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_refresh`()
    extends SimpleNoDataWritable
      with EsResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_all/_refresh`()
    extends SimpleNoDataWritable
      with EsResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_refresh`(indexes: String*)
    extends SimpleNoDataWritable
      with EsResource

  // 2.15 Force Merge
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_forcemerge`()
    extends ForceMergeNoDataWritable
      with EsResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_all/_forcemerge`()
    extends ForceMergeNoDataWritable
      with EsResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_forcemerge`(indexes: String*)
    extends ForceMergeNoDataWritable
      with EsResource

  // 2.16 Upgrade
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_upgrade`()
    extends UpgradeNoDataWritable
      with EsResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_all/_upgrade`()
    extends UpgradeNoDataWritable
      with EsResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_upgrade`(indexes: String*)
    extends UpgradeNoDataWritable
      with EsResource

  //TODO 4] cluster API

  //TODO others - eg common plugins like graph?

  //TODO: refactor into different traits and then have a "full object" that inherits from the different versions
  // elasticsearch.scala.driver.common ApiModelCommon, ApiModelSearch, ApiModelIndices, ApiModelCluster
  // elasticsearch.scala.driver.latest etc
  // elasticsearch.scala.driver.v2.3 etc
  // elasticsearch.scala.plugins.driver (for graph)
  // have a latest "pointer" somewhere eg Versions { latest = Common, 2_3_5 = V2_3_5 etc }

  //TODO: enforce >0 params anywhere there's an (eg) (index: String*) type call, currently can call with () which will fail
}
