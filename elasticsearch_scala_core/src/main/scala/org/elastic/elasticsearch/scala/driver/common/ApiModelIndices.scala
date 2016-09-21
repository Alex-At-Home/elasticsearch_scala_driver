package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestResources._
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.elasticsearch.scala.driver.common.IndicesModifierGroups._

/** Resources to monitor and manage indices in Elasticsearch
  */
trait ApiModelIndices {

  // 3 Index operations

  // 3.1 Opening and closing indexes
  //https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html

  /** Open one or more indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html Docs]]
    *
    * @param indexes The index or indexes to open
    */
  case class `/$indexes/_open`(indexes: String*)
    extends RestNoDataSendable[OpenCloseIndexesParams]
      with RestResource

  /** Open all the indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html Docs]]
    */
  case class `/_all/_open`()
    extends RestNoDataSendable[StandardParams]
      with RestResource

  /** Close one or more indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html Docs]]
    *
    * @param indexes The index or indexes to open
    */
  case class `/$indexes/_close`(indexes: String*)
    extends RestNoDataSendable[OpenCloseIndexesParams]
      with RestResource

  /** Close all the indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html Docs]]
    */
  case class `/_all/_close`()
    extends RestNoDataSendable[StandardParams]
      with RestResource

  // 3.2 Mappings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html

  /** Gets the mapping for all indexes and all types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html Docs]]
    */
  case class `/_mapping`()
    extends `tree:/_mapping`
      with RestReadable[StandardParams]
      with RestResource

  /** Gets the mapping for all indexes and 1+ types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html Docs]]
    *
    * @param types The type or type whose mapping to get
    */
  case class `/_mapping/$types`(types: String*)
    extends `tree:/_mapping/$types`
      with RestReadable[StandardParams]
      with RestResource

  /** Gets the mapping for all indexes and all types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html Docs]]
    */
  case class `/_all/_mapping`()
    extends `tree:/_all/_mapping`
      with RestReadable[StandardParams]
      with RestResource

  /** Gets the mapping for all indexes and 1+ types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html Docs]]
    *
    * @param types The type or type whose mapping to get
    */
  case class `/_all/_mapping/$types`(types: String*)
    extends `tree:/_all/_mapping/$types`
      with RestReadable[StandardParams]
      with RestResource

  /** Get the mapping for 1+ indexes and all types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html Docs]]
    *
    * @param indexes The index or indexes whose mapping to get
    */
  case class `/$indexes/_mapping`(indexes: String*)
    extends `tree:/$indexes/_mapping`
      with RestReadable[StandardParams]
      with RestResource

  /** Gets the mapping for 1+ indexes and 1+ types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html Docs]]
    *
    * @param indexes The index or indexes whose mapping to get
    * @param types The type or type whose mapping to get
    */
  case class `/$indexes/_mapping/$types`(indexes: Seq[String], types: Seq[String])
    extends `tree:/$indexes/_mapping/$types`
      with RestReadable[StandardParams]
      with RestResource

  // 3.3 Field mappings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html

  /** A resource to retrieve the field mappings for various index/type/fieldname combinations
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html Docs]]
    *
    * @param indexes The indexes over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/field/$fields`(indexes: String*)(fields: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** A resource to retrieve the field mappings for various index/type/fieldname combinations
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html Docs]]
    *
    * @param indexes The indexes over which the field mappings are retrieved
    * @param types The types over which the field mappings are retrieved
    */
  case class `/$indexes/_mapping/$types/field/$fields`(indexes: String*)(types: String*)(fields: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** A resource to retrieve the field mappings for various index/type/fieldname combinations
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html Docs]]
    */
  case class `/_mapping/field/$fields`(fields: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** A resource to retrieve the field mappings for various index/type/fieldname combinations
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html Docs]]
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `/_mapping/$types/field/$fields`(types: String*)(fields: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** A resource to retrieve the field mappings for various index/type/fieldname combinations
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html Docs]]
    */
  case class `/_all/_mapping/field/$fields`(fields: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** A resource to retrieve the field mappings for various index/type/fieldname combinations
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html Docs]]
    *
    * @param types The types over which the field mappings are retrieved
    */
  case class `/_all/_mapping/$types/field/$fields`(types: String*)(fields: String*)
    extends RestReadable[StandardParams]
      with RestResource

  // 3.4 Index aliases
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html

  /** Create one or more aliases based on the posted content
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    */
  case class `/_aliases`()
    extends RestWritable[StandardParams]
      with RestResource

  /** Retrieve/check the aliases across all aliases
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    *
    * @param aliases The names of the aliases (including globs)
    */
  case class `/_alias/$aliases`(aliases: String)
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestResource

  /** Retrieve/check all aliases across all aliases
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    */
  case class `/_alias/*`()
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestResource

  /** Create/delete/retrieve a mapping from a single alias to the specified index(es)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    *
    * @param indexes The list of indexes (including globs)
    * @param alias The specific alias
    */
  case class `/$indexes/_alias/$alias`(indexes: String*)(alias: String)
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestNoDataWritable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** Delete/retrieve mappings from a multiple aliases to the specified index(es)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    *
    * @param indexes The list of indexes (including globs)
    * @param aliases The names of the aliases (including globs)
    */
  case class `/$indexes/_alias/$aliases`(indexes: String*)(aliases: String*)
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** Delete/retrieve mappings from a multiple aliases to the specified index(es)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    *
    * @param indexes The list of indexes (including globs)
    */
  case class `/$indexes/_alias/*`(indexes: String*)
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** Create/delete/retrieve a mapping from single alias to all the indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    *
    * @param alias The specific alias
    */
  case class `/_all/_alias/$alias`(alias: String)
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestNoDataWritable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** Delete/retrieve a mapping from multiple aliases to all the indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    *
    * @param aliases The names of the aliases (including globs)
    */
  case class `/_all/_alias/$aliases`(aliases: String*)
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** Delete/retrieve a mapping from all the aliases to all the indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html Docs]]
    */
  case class `/_all/_alias/*`()
    extends RestReadable[StandardParams]
      with RestCheckable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  // 3.5 Get and Update index settings
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html

  /** Read or Update the settings for the entire cluster
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html Update Docs]]
    */
  case class `/_settings`()
    extends `tree:/_settings`
      with RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestResource

  /** Read the filtered settings for the entire cluster
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html Read Docs]]
    *
    * @param name The filter glob string vs index names
    */
  case class `/_settings/name=$name`(name: String)
    extends RestReadable[StandardParams]
      with RestResource

  /** Read or Update the settings for the entire cluster
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html Update Docs]]
    */
  case class `/_all/_settings`()
    extends `tree:/_all/_settings`
      with RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestResource

  /** Read the filtered settings for the entire cluster
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html Read Docs]]
    *
    * @param name The filter glob string vs index names
    */
  case class `/_all/_settings/name=$name`(name: String)
    extends RestReadable[StandardParams]
      with RestResource

  /** Read or Update the settings for one or more clusters
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html Update Docs]]
    *
    * @param indexes The indexes to read/update
    */
  case class `/$indexes/_settings`(indexes: String*)
    extends `tree:/$indexes/_settings`
      with RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestResource

  /** Read the filtered settings for the entire cluster
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html Read Docs]]
    *
    * @param indexes The indexes to read
    * @param name The filter glob string vs index names
    */
  case class `/$indexes/_settings/name=$name`(indexes: String*)(name: String)
    extends RestReadable[StandardParams]
      with RestResource

  // 3.6 Analyze
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html

  /** Analyze some text against any of the analyzers registered vs the cluster
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html Docs]]
    */
  case class `/_analyze`()
    extends RestWithDataReadable[StandardParams]
      with RestResource

  /** Analyze some text against any of the analyzers registered vs the index
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html Docs]]
    *
    * @param index The index against which to run the analysis
    */
  case class `/$index/_analyze`(index: String)
    extends RestWithDataReadable[StandardParams]
      with RestResource

  // 3.7 Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html

  /** Also lists all of the index templates
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html Docs]]
    */
  case class `/_template`()
    extends `tree:/_template`
      with RestReadable[StandardParams]
      with RestResource

  /** The create/read/update/delete resource for this template
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html Docs]]
    *
    * @param template The template name
    */
  case class `/_template/$template`(template: String)
    extends RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestDeletable[StandardParams]
      with RestCheckable[StandardParams]
      with RestResource

  /** The read resource for multiple templates
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html Docs]]
    *
    * @param templates The template names
    */
  case class `/_template/$templates`(templates: String*)
    extends RestReadable[StandardParams]
      with RestResource

  // 3.8 Indices stats
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html

  /** Returns high level aggregation and index level stats for all indices
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html Docs]]
    */
  case class `/_stats`()
    extends `tree:/_stats`
      with RestReadable[IndexStatsParams]
      with RestResource

  /** Returns high level aggregation and index level stats for all indices and specific stats groups
    *
    * @param statsGroups The list of statistics groups
    */
  case class `/_stats/$statsGroups`(statsGroups: String*)
    extends `tree:/_stats/$statsGroups`
      with RestReadable[IndexStatsParams]
      with RestResource

  /** Returns high level aggregation and index level stats for all indices and specific stats groups
    * and with filtered fields
    *
    * @param statsGroups The list of statistics groups
    * @param fieldGroups The list of fields for these statistics groups
    */
  case class `/_stats/$statsGroups/$fieldGroups`(statsGroups: Seq[String], fieldGroups: Seq[String])
    extends RestReadable[IndexStatsParams]
      with RestResource

  /** Returns high level aggregation and index level stats for specified indices
    *
    * @param indexes The indexes over which to restrict the stats
    */
  case class `/$indexes/_stats`(indexes: String*)
    extends `tree:/$indexes/_stats`
      with RestReadable[IndexStatsParams]
      with RestResource

  /** Returns high level aggregation and index level stats for specified indices and specific stats groups
    *
    * @param indexes The indexes over which to restrict the stats
    * @param statsGroups The list of statistics groups
    */
  case class `/$indexes/_stats/$statsGroups`(indexes: Seq[String], statsGroups: Seq[String])
    extends `tree:/$indexes/_stats/$statsGroups`
      with RestReadable[IndexStatsParams]
      with RestResource

  /** Returns high level aggregation and index level stats for specified indices and specific stats groups
    * and with filtered fields
    *
    * @param indexes The indexes over which to restrict the stats
    * @param statsGroups The list of statistics groups
    * @param fieldGroups The list of fields for these statistics groups
    */
  case class `/$indexes/_stats/$statsGroups/$fieldGroups`
  (indexes: Seq[String], statsGroups: Seq[String], fieldGroups: Seq[String])
    extends RestReadable[IndexStatsParams]
      with RestResource

  // 3.9 Segments
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html

  /** Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html Docs]]
    */
  case class `/_segments`()
    extends RestReadable[VerboseStandardParams]
      with RestResource

  /**  Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html Docs]]
    */
  case class `/_all/_segments`()
    extends RestReadable[VerboseStandardParams]
      with RestResource

  /** Provide low level segments information that a Lucene index (shard level) is built with.
    * Allows to be used to provide more information on the state of a shard and an index, possibly optimization
    * information, data "wasted" on deletes, and so on.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html Docs]]
    *
    * @param indexes The indexes over which to check the segment information
    */
  case class `/$indexes/_segments`(indexes: String*)
    extends RestReadable[VerboseStandardParams]
      with RestResource

  // 3.10 Indices recovery
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html

  /** The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html Docs]]
    */
  case class `/_recovery`()
    extends RestReadable[IndexRecoveryParams]
      with RestResource

  /** The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html Docs]]
    */
  case class `/_all/_recovery`()
    extends RestReadable[IndexRecoveryParams]
      with RestResource

  /** The indices recovery API provides insight into on-going index shard recoveries.
    * Recovery status may be reported for specific indices, or cluster-wide.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html Docs]]
    *
    * @param indexes The indexes over which to check the segment information
    */
  case class `/$indexes/_recovery`(indexes: String*)
    extends RestReadable[IndexRecoveryParams]
      with RestResource

  // 2.11 Shard stores
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html

  /** Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html Docs]]
    */
  case class `/_shard_stores`()
    extends RestReadable[IndexShardStoreParams]
      with RestResource

  /** Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html Docs]]
    */
  case class `/_all/_shard_stores`()
    extends RestReadable[IndexShardStoreParams]
      with RestResource

  /** Provides store information for shard copies of indices. Store information reports on which nodes shard copies
    * exist, the shard copy version, indicating how recent they are, and any exceptions encountered while opening
    * the shard index or from earlier engine failure.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html Docs]]
    *
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_shard_stores`(indexes: String*)
    extends RestReadable[IndexShardStoreParams]
      with RestResource

  // 2.12 Clear cache
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html

  //TODO: not sure what to make of Specific caches can be cleaned explicitly by setting query, fielddata or request. needs to investigate further..

  /** The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html Docs]]
    */
  case class `/_cache/clear`()
    extends RestNoDataSendable[IndexCacheClearParams]
      with RestResource

  /** The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html Docs]]
    */
  case class `/_all/_cache/clear`()
    extends RestNoDataSendable[IndexCacheClearParams]
      with RestResource

  /** The clear cache API allows to clear either all caches or specific cached associated with one or more indices.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html Docs]]
    *
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_cache/clear`(indexes: String*)
    extends RestNoDataSendable[IndexCacheClearParams]
      with RestResource

  // 2.13 Flush
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html

  /** The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html Docs]]
    */
  case class `/_flush`()
    extends RestNoDataSendable[IndexFlushParams]
      with RestResource

  /** The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html Docs]]
    */
  case class `/_all/_flush`()
    extends RestNoDataSendable[IndexFlushParams]
      with RestResource

  /** The flush API allows to flush one or more indices through an API. The flush process of an index basically frees
    * memory from the index by flushing data to the index storage and clearing the internal transaction log.
    * By default, Elasticsearch uses memory heuristics in order to automatically trigger flush operations as required
    * in order to clear memory.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html Docs]]
    *
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_flush`(indexes: String*)
    extends RestNoDataSendable[IndexFlushParams]
      with RestResource

  // 2.14 Refresh
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html

  /** The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html Docs]]
    */
  case class `/_refresh`()
    extends RestNoDataSendable[StandardParams]
      with RestResource

  /** The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html Docs]]
    */
  case class `/_all/_refresh`()
    extends RestNoDataSendable[StandardParams]
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html Docs]]
    *
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_refresh`(indexes: String*)
    extends RestNoDataSendable[StandardParams]
      with RestResource

  // 2.15 Force Merge
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html

  /** The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html Docs]]
    */
  case class `/_forcemerge`()
    extends RestNoDataSendable[IndexForceMergeParams]
      with RestResource

  /** The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html Docs]]
    */
  case class `/_all/_forcemerge`()
    extends RestNoDataSendable[IndexForceMergeParams]
      with RestResource

  /** The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html Docs]]
    *
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_forcemerge`(indexes: String*)
    extends RestNoDataSendable[IndexForceMergeParams]
      with RestResource

  // 2.16 Upgrade
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html

  /** The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html Docs]]
    */
  case class `/_upgrade`()
    extends RestNoDataSendable[IndexUpgradeParams]
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html Docs]]
    */
  case class `/_all/_upgrade`()
    extends RestNoDataSendable[IndexUpgradeParams]
      with RestResource

  /**
    * The refresh API allows to explicitly refresh one or more index, making all operations performed since the last
    * refresh available for search. The (near) real-time capabilities depend on the index engine used. For example,
    * the internal one requires refresh to be called, but by default a refresh is scheduled periodically.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html Docs]]
    *
    * @param indexes The indexes over which to read the shards
    */
  case class `/$indexes/_upgrade`(indexes: String*)
    extends RestNoDataSendable[IndexUpgradeParams]
      with RestResource
}
object ApiModelIndices extends ApiModelIndices
