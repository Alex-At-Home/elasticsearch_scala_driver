package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.OperationGroups._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.rest.scala.driver.RestBase._

/**
  * A set of DSLs representing the Elasticsearch resources
  */
object ApiModel_common {

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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
  {
  }

  /**
    * Retrieves/stores/deletes templates from/to/from the .scripts index
    */
  case class `/_search/template/$template`(template: String)
    extends SimpleReadable
      with SimpleWritable
      with SimpleDeletable
      with RestResource
  {
  }

  /**
    * Used to test search templates (eg before executing them)
    */
  case class `/_render/template`()
    extends SimpleWithDataReadable
    with RestResource
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
    with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
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
      with RestResource
  {
  }

  // 2.7 Validate API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html

  /**
    * Validate the query over the specified indexes and types
    */
  case class `/_validate`()
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with RestResource
  {
  }

  /**
    * Validate the query over the specified indexes and types
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_validate`(types: String*)
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with RestResource
  {
  }

  /**
    * Validate the query over the specified indexes and types
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_validate`(indexes: String*)
    extends QueryMiscUriReadable with QueryCountWithDataReadable
      with RestResource
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
      with RestResource
  {
  }

  // 2.8 Explain API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html

  /**
    * Explain the query over the specified indexes and types
    */
  case class `/_explain`()
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with RestResource
  {
  }

  /**
    * Explain the query over the specified indexes and types
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_explain`(types: String*)
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with RestResource
  {
  }

  /**
    * Explain the query over the specified indexes and types
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_explain`(indexes: String*)
    extends QueryExplainUriReadable with QueryExplainWithDataReadable
      with RestResource
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
      with RestResource
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
      with RestResource
  {
  }

  /**
    * Open all the indexes
    */
  case class `/_all/_open`()
    extends SimpleNoDataWritable
      with RestResource
  {
  }

  /**
    * Close one or more indexes
    *
    * @param indexes The index or indexes to open
    */
  case class `/$indexes/_close`(indexes: String*)
    extends OpenCloseIndexesNoDataWritable
      with RestResource
  {
  }

  /**
    * Close all the indexes
    */
  case class `/_all/_close`()
    extends SimpleNoDataWritable
      with RestResource
  {
  }

  // 3.2 Mappings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html

  /**
    * Gets the mapping for all indexes and all types
    */
  case class `/_mapping`()
    extends `tree:/_mapping`
      with SimpleReadable
      with RestResource

  /**
    * Gets the mapping for all indexes and 1+ types
    *
    * @param types The type or type whose mapping to get
    */
  case class `/_mapping/$types`(types: String*)
    extends `tree:/_mapping/$types`
      with SimpleReadable
      with RestResource

  /**
    * Gets the mapping for all indexes and all types
    */
  case class `/_all/_mapping`()
    extends `tree:/_all/_mapping`
      with SimpleReadable
      with RestResource

  /**
    * Gets the mapping for all indexes and 1+ types
    *
    * @param types The type or type whose mapping to get
    */
  case class `/_all/_mapping/$types`(types: String*)
    extends `tree:/_all/_mapping/$types`
      with SimpleReadable
      with RestResource

  /**
    * Get the mapping for 1+ indexes and all types
    *
    * @param indexes The index or indexes whose mapping to get
    */
  case class `/$indexes/_mapping`(indexes: String*)
    extends `tree:/$indexes/_mapping`
      with SimpleReadable
      with RestResource

  /**
    * Gets the mapping for 1+ indexes and 1+ types
    *
    * @param indexes The index or indexes whose mapping to get
    * @param types The type or type whose mapping to get
    */
  case class `/$indexes/_mapping/$types`(indexes: Seq[String], types: Seq[String])
    extends `tree:/$indexes/_mapping/$types`
      with SimpleReadable
      with RestResource


  // 3.3 Field mappings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html

  /**
    * A resource to retrieve
    *
    * @param indexes The indexes over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/field/$fields`(indexes: String*)(fields: String*)
    extends SimpleReadable
      with RestResource

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param indexes The indexes over which the field mappings are retrieved
    * @param types The types over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/$types/field/$fields`(indexes: String*)(types: String*)(fields: String*)
    extends SimpleReadable
      with RestResource

  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `/_mapping/field/$fields`(fields: String*)
    extends SimpleReadable
      with RestResource

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `/_mapping/$types/field/$fields`(types: String*)(fields: String*)
    extends SimpleReadable
      with RestResource

  /**
    * an intermediate step leading to the get field mapping resources
    */
  case class `/_all/_mapping/field/$fields`(fields: String*)
    extends SimpleReadable
      with RestResource

  /**
    * an intermediate step leading to the get field mapping resources
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `/_all/_mapping/$types/field/$fields`(types: String*)(fields: String*)
    extends SimpleReadable
      with RestResource

  // 3.4 Index aliases
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html

  /**
    * Create one or more aliases based on the posted content
    */
  case class `/_aliases`()
    extends SimpleWritable
      with RestResource

  /**
    * Retrieve/check the aliases across all aliases
    *
    * @param aliases The names of the aliases (including globs)
    */
  case class `/_alias/$aliases`(aliases: String)
    extends SimpleReadable
      with SimpleCheckable
      with RestResource

  /**
    * Retrieve/check all aliases across all aliases
    */
  case class `/_alias/*`()
    extends SimpleReadable
      with SimpleCheckable
      with RestResource

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
      with RestResource

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
      with RestResource

  /**
    * Delete/retrieve mappings from a multiple aliases to the specified index(es)
    *
    * @param indexes The list of indexes (including globs)
    */
  case class `/$indexes/_alias/*`(indexes: String*)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with RestResource

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
      with RestResource

  /**
    * Ddelete/retrieve a mapping from multiple aliases to all the indexes
    *
    * @param aliases The names of the aliases (including globs)
    */
  case class `/_all/_alias/$aliases`(aliases: String*)
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with RestResource

  /**
    * Delete/retrieve a mapping from all the aliases to all the indexes
    */
  case class `/_all/_alias/*`()
    extends SimpleReadable
      with SimpleCheckable
      with SimpleDeletable
      with RestResource

  // 3.5 Get and Update index settings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html

  /**
    * Read or Update the settings for the entire cluster
    */
  case class `/_settings`()
    extends `tree:/_settings`
      with SimpleReadable
      with SimpleWritable
      with RestResource

  /**
    * Read the filtered settings for the entire cluster
    *
    * @param name The filter glob string vs index names
    */
  case class `/_settings/name=$name`(name: String)
    extends SimpleReadable
      with RestResource

  /**
    * Read or Update the settings for the entire cluster
    */
  case class `/_all/_settings`()
    extends `tree:/_all/_settings`
      with SimpleReadable
      with SimpleWritable
      with RestResource

  /**
    * Read the filtered settings for the entire cluster
    *
    * @param name The filter glob string vs index names
    */
  case class `/_all/_settings/name=$name`(name: String)
    extends SimpleReadable
      with RestResource

  /**
    * Read or Update the settings for one or more clusters
    *
    * @param indexes The indexes to read/update
    */
  case class `/$indexes/_settings`(indexes: String*)
    extends `tree:/$indexes/_settings`
      with SimpleReadable
      with SimpleWritable
      with RestResource

  /**
    * Read the filtered settings for the entire cluster
    *
    * @param indexes The indexes to read
    * @param name The filter glob string vs index names
    */
  case class `/$indexes/_settings/name=$name`(indexes: String*)(name: String)
    extends SimpleReadable
      with RestResource

  // 3.6 Analyze
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html

  /**
    * Analyze some text against any of the analyzers registered vs the cluster
    */
  case class `/_analyze`()
    extends SimpleWithDataReadable
      with RestResource

  /**
    * Analyze some text against any of the analyzers registered vs the index
    *
    * @param index The index against which to run the analysis
    */
  case class `/$index/_analyze`(index: String)
    extends SimpleWithDataReadable
      with RestResource

  // 3.7 Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html

  /**
    * An intermediate class to get to the index templates resource
    * Also lists all of the index templates
    */
  case class `/_template`()
    extends SimpleReadable
      with RestResource
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
      with RestResource

  /**
    * The read resource for multuple templates
    *
    * @param templates The template names
    */
  case class `/_template/$templates`(templates: String*)
    extends SimpleReadable
      with RestResource

  // 3.8 Indices stats
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html

  /**
    * Returns high level aggregation and index level stats for all indices
    */
  case class `/_stats`()
    extends IndexStatsReadable
    with RestResource
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
      with RestResource
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
      with RestResource

  /**
    * Returns high level aggregation and index level stats for specified indices
    *
    * @param indexes The indexes over which to restrict the stats
    */
  case class `/$indexes/_stats`(indexes: String*)
    extends IndexStatsReadable
      with RestResource
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
      with RestResource
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
      with RestResource

  // 3.9 Segments
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html

  /**
    * Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    */
  case class `/_segments`()
    extends VerboseSimpleReadable
      with RestResource

  /**
    * Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    */
  case class `/_all/_segments`()
    extends VerboseSimpleReadable
      with RestResource

  /**
    * Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    * @param indexes The indexes over which to check the segment information
    */
  case class `/$indexes/_segments`(indexes: String*)
    extends VerboseSimpleReadable
      with RestResource

  // 3.10 Indices recovery
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html

  /**
    * The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    */
  case class `/_recovery`()
    extends IndexRecoveryReadable
      with RestResource

  /**
    * The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    */
  case class `/_all/_recovery`()
    extends IndexRecoveryReadable
      with RestResource

  /**
    * The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    * @param indexes The indexes over which to check the segment information
    */
  case class `/$indexes/_recovery`(indexes: String*)
    extends IndexRecoveryReadable
      with RestResource

  // 2.11 Shard stores
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html

  /**
    * Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    */
  case class `/_shard_stores`()
    extends ShardStoreReadable
      with RestResource

  /**
    * Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    */
  case class `/_all/_shard_stores`()
    extends ShardStoreReadable
      with RestResource

  /**
    * Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_shard_stores`(indexes: String*)
    extends ShardStoreReadable
      with RestResource

  // 2.12 Clear cache
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    */
  case class `/_cache/clear`()
    extends ShardStoreReadable
      with RestResource

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    */
  case class `/_all/_cache/clear`()
    extends ShardStoreReadable
      with RestResource

  /**
    * The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_cache/clear`(indexes: String*)
    extends ShardStoreReadable
      with RestResource

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
      with RestResource

  /**
    * The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    */
  case class `/_all/_flush`()
    extends FlushNoDataWritable
      with RestResource

  /**
    * The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_flush`(indexes: String*)
    extends FlushNoDataWritable
      with RestResource

  // 2.14 Refresh
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_refresh`()
    extends SimpleNoDataWritable
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_all/_refresh`()
    extends SimpleNoDataWritable
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_refresh`(indexes: String*)
    extends SimpleNoDataWritable
      with RestResource

  // 2.15 Force Merge
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_forcemerge`()
    extends ForceMergeNoDataWritable
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_all/_forcemerge`()
    extends ForceMergeNoDataWritable
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_forcemerge`(indexes: String*)
    extends ForceMergeNoDataWritable
      with RestResource

  // 2.16 Upgrade
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_upgrade`()
    extends UpgradeNoDataWritable
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    */
  case class `/_all/_upgrade`()
    extends UpgradeNoDataWritable
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_upgrade`(indexes: String*)
    extends UpgradeNoDataWritable
      with RestResource

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
