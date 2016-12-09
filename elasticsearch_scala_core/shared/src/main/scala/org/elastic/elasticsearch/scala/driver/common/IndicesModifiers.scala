package org.elastic.elasticsearch.scala.driver.common

import CommonModifiers._
import CommonModifierGroups._
import org.elastic.rest.scala.driver.RestBase._

/** Value class representing the different shard statuses */
case class ShardStatus(value: String) extends AnyVal with ToStringAnyVal[String]
/** Available shard statuses */
object ShardStatus {
  @Constant val red, green, yellow = ToStringAnyVal.AutoGenerate[ShardStatus]
}

/** Parameters for resources to monitor and manage indices in Elasticsearch
  */
object IndicesModifiers {

  // Index statistics

  /** (modifier - see method for details) */
  trait Types extends Modifier { 
    /** The types over which to filter statistics
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html#indices-stats]]
      *
      * @param types The types over which to filter statistics
      * @return The updated driver operation
      */
    @Param def types(types: String*): this.type = Modifier.Body
  }

  // Segments modifiers

  /** (modifier - see method for details) */
  trait Verbose extends Modifier { 
    /** Controls the verbosity of many index related resources
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html eg Segments resources]]
      *
      * @param b Whether the response should be verbose
      * @return The updated driver operation
      */
    @Param def verbose(b: Boolean): this.type = Modifier.Body
  }

  // Recovery Modifiers

  /** (modifier - see method for details) */
  trait Detailed extends Modifier { 
    /** Display a detailed view of ongoing shard recoveries.
      * This is primarily useful for viewing the recovery of physical index files. Default: false.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html]]
      *
      * @param b Whether the reply should add extra detail (default: false)
      * @return The updated driver operation
      */
    @Param def detailed(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait ActiveOnly extends Modifier { 
    /** Display only those recoveries that are currently on-going. Default: false.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html]]
      *
      * @param b Whether the only ongoing recoveries are displayed (default: false)
      * @return The updated driver operation
      */
    @Param def active_only(b: Boolean): this.type = Modifier.Body
  }

  // Index shard status information

  /** (modifier - see method for details) */
  trait Status extends Modifier { 
    /** Only shards with this status are shown
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html]]
      *
      * @param status Only shards with this status are shown
      * @return The updated driver operation
      */
    @Param def status(status: ShardStatus): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait CacheFields extends Modifier { 
    /** All caches relating to a specific field(s) can also be cleared by specifying fields parameter with a
      * comma delimited list of the relevant fields.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html]]
      *
      * @param fields Which fields from the document index to include
      * @return The updated driver operation
      */
    @Param def fields(fields: String*): this.type = Modifier.Body
  }

  // Flush parameters

  /** (modifier - see method for details) */
  trait WaitIfOngoing extends Modifier { 
    /** If set to true the flush operation will block until the flush can be executed if another flush operation is
      * already executing. The default is false and will cause an exception to be thrown on the shard level if another
      * flush operation is already running.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html]]
      *
      * @param b If set to true the flush operation will block until the flush can be executed if another flush
      *          operation is already executing.
      * @return The updated driver operation
      */
    @Param def wait_if_ongoing(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Force extends Modifier { 
    /** Whether a flush should be forced even if it is not necessarily needed ie. if no changes will be committed to the
      * index. This is useful if transaction log IDs should be incremented even if no uncommitted changes are present.
      * (This setting can be considered as internal)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html]]
      *
      * @param b  Whether a flush should be forced
      * @return The updated driver operation
      */
    @Param def force(b: Boolean): this.type = Modifier.Body
  }

  // Force Merge Parameters

  /** (modifier - see method for details) */
  trait MaxNumSegments extends Modifier { 
    /** The number of segments to merge to. To fully merge the index, set it to 1.
      * Defaults to simply checking if a merge needs to execute, and if so, executes it.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html]]
      *
      * @param n The number of segments to merge to. To fully merge the index, set it to 1.
      * @return The updated driver operation
      */
    @Param def max_num_segments(n: Integer): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait OnlyExpungeDeletes extends Modifier { 
    /** The number of segments to merge to. To fully merge the index, set it to 1.
      * Defaults to simply checking if a merge needs to execute, and if so, executes it.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html]]
      *
      * @param b Whether the only ongoing recoveries are displayed (default: false)
      * @return The updated driver operation
      */
    @Param def only_expunge_deletes(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Flush extends Modifier { 
    /** Should a flush be performed after the forced merge. Defaults to true.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html]]
      *
      * @param b Should a flush be performed after the forced merge. Defaults to true.
      * @return The updated driver operation
      */
    @Param def flush(b: Boolean): this.type = Modifier.Body
  }

  // Upgrade modifiers

  /** (modifier - see method for details) */
  trait OnlyAncientSegments extends Modifier { 
    /**
      * If true, only very old segments (from a previous Lucene major release) will be upgraded. While this will do the
      * minimal work to ensure the next major release of Elasticsearch can read the segments, it’s dangerous because
      * it can leave other very old segments in sub-optimal formats. Defaults to false.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-upgrade.html]]
      *
      * @param b If true, only very old segments (from a previous Lucene major release) will be upgraded
      * @return The updated driver operation
      */
    @Param def only_ancient_segments(b: Boolean): this.type = Modifier.Body
  }

}

/** Common groupings of modifiers relating to index resources
  */
object IndicesModifierGroups {
  import IndicesModifiers._

  /** Parameters for resources for opening and closing indices */
  trait OpenCloseIndexesParams extends IgnoreUnavailable with StandardParams

  /** Parameters for getting index statistics parameters */
  trait IndexStatsParams extends Level with Groups with Types with StandardParams

  /** Parameters with controllable verbosity */
  trait VerboseStandardParams extends Verbose with StandardParams

  /** Parameters for index recovery resources */
  trait IndexRecoveryParams extends Detailed with ActiveOnly with StandardParams

  /** Parameters for index shard store resources */
  trait IndexShardStoreParams extends Status with StandardParams

  /** Parameters for clearing caches */
  trait IndexCacheClearParams extends CacheFields with StandardParams

  /** Parameters for flushing indices */
  trait IndexFlushParams extends WaitIfOngoing with Force with StandardParams

  /** Parameters for force merging indices */
  trait IndexForceMergeParams extends MaxNumSegments with OnlyExpungeDeletes with Flush with StandardParams

  /** Parameters for upgrading indices */
  trait IndexUpgradeParams extends OnlyAncientSegments with StandardParams
}
