package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.ElasticsearchBase._

/**
  * A list of modifiers for the various Elasticsearch resources
  */
object Modifiers {

  // Generic modifiers, used in lots of places

  sealed trait Modifier
  /**
    * Represents a resource that can have the modifier ?pretty
    * (Controls the format of the response if returned as a string, else ignored)
    */
  trait Pretty extends Modifier { self: BaseDriverOp =>
    /**
      * Returns a resource with modifier ?pretty
      * (Controls the format of the response if returned as a string, else ignored)
      * @param b The prettiness
      * @return The updated driver operation
      */
    def `?pretty=`(b: Boolean) = self.withModifier(s"?pretty=$b")

    /**
      * Returns a resource with modifier ?pretty
      * (Controls the format of the response if returned as a string, else ignored)
      * @return The updated driver operation
      */
    def `?pretty` = `?pretty=`(true)
  }
  /**
    * Represents a resource that can have the modifier ?routing
    * (Forces the request to go to a specific node in the cluster)
    */
  trait Routing extends Modifier { self: BaseDriverOp =>
    /**
      * Represents a resource that can have the modifier ?routing
      * (Forces the request to go to a specific node in the cluster)
      * @param node The node to which to restrict the request
      * @return The updated driver operation
      */
    def `?routing=`(node: String) = self.withModifier(s"?routing=$node")
  }

  /**
    * TODO
    */
  trait Version extends Modifier { self: BaseDriverOp =>
    def `?version=`(v: Int) = self.withModifier(s"?version=$v")
  }
  /**
    * TODO
    */
  trait OpType extends Modifier { self: BaseDriverOp =>
    def `?op_type=`(opType: String) = self.withModifier(s"?op_type=$opType")
  }
  /**
    * TODO
    */
  trait Parent extends Modifier { self: BaseDriverOp =>
    def `?parent=`(parent: String) = self.withModifier(s"?parent=$parent")
  }

  /**
    * A search timeout, bounding the search request to be executed within the specified time value and bail with the
    * hits accumulated up to that point when expired. Defaults to no timeout.
    */
  trait Timeout extends Modifier { self: BaseDriverOp =>
    /**
      * A search timeout, bounding the search request to be executed within the specified time value and bail with the
      * hits accumulated up to that point when expired. Defaults to no timeout.
      * @param timeout The timeout
      */
    def `?timeout=`(timeout: String) = self.withModifier(s"?timeout=$timeout")
  }

  /**
    * TODO
    */
  trait SourceBase extends Modifier { self: BaseDriverOp =>
    def `?source=`(b: Boolean) = self.withModifier(s"?source=$b")
  }
  /**
    * TODO
    */
  trait SourceInclude extends Modifier { self: BaseDriverOp =>
    def `?source_include=`(fields: String*) = self.withModifier(s"?source_include=${fields.mkString(",")}")
  }
  /**
    * TODO
    */
  trait SourceExclude extends Modifier { self: BaseDriverOp =>
    def `?source_exclude=`(fields: String*) = self.withModifier(s"?source_exclude=${fields.mkString(",")}")
  }
  /**
    * TODO
    */
  trait Fields extends Modifier { self: BaseDriverOp =>
    def `?fields=`(fields: String*) = self.withModifier(s"?fields=${fields.mkString(",")}")
  }
  /**
    * TODO
    */
  trait Refresh extends Modifier { self: BaseDriverOp =>
    def `?refresh=`(b: Boolean) = self.withModifier(s"?refresh=$b")
    def `?refresh` = `?refresh=`(true)
  }

  // Search modifiers - URI version

  /**
    * The query string (maps to the query_string query)
    */
  trait Query extends Modifier { self: BaseDriverOp =>
    /**
      * The query string (maps to the query_string query)
      * @param query The query string (maps to the query_string query)
      */
    def `?q=`(query: String) = self.withModifier(s"?q=$query}")
  }

  /**
    * The default field to use when no field prefix is defined within the query.
    */
  trait DefaultField extends Modifier { self: BaseDriverOp =>
    /**
      * The default field to use when no field prefix is defined within the query.
      * @param defaultField The default field to use when no field prefix is defined within the query
      */
    def `?df=`(defaultField: String) = self.withModifier(s"?df=$defaultField")
  }

  /**
    * The analyzer name to be used when analyzing the query string.
    */
  trait Analyzer extends Modifier { self: BaseDriverOp =>
    /**
      * The analyzer name to be used when analyzing the query string.
      * @param analyzer The analyzer name to be used when analyzing the query string.
      */
    def `?analyzer=`(analyzer: String) = self.withModifier(s"?analyzer=$analyzer")
  }

  /**
    * Should terms be automatically lowercased or not. Defaults to `true`.
    */
  trait LowercaseExpandedTerms extends Modifier { self: BaseDriverOp =>
    /**
      * Should terms be automatically lowercased or not. Defaults to `true`.
      * @param b Should terms be automatically lowercased or not. Defaults to `true`.
      */
    def `?lowercase_expanded_terms=`(b: Boolean) = self.withModifier(s"?lowercase_expanded_terms=$b")
  }

  /**
    * Should wildcard and prefix queries be analyzed or not. Defaults to `false`.
    */
  trait AnalyzeWildcard extends Modifier { self: BaseDriverOp =>
    /**
      * Should wildcard and prefix queries be analyzed or not. Defaults to `false`.
      * @param b Should wildcard and prefix queries be analyzed or not. Defaults to `false`.
      */
    def `?analyze_wildcard=`(b: Boolean) = self.withModifier(s"?analyze_wildcard=$b")
  }

  /**
    * The default operator to be used, can be AND or OR. Defaults to OR.
    */
  trait DefaultOperator extends Modifier { self: BaseDriverOp =>
    /**
      * The default operator to be used, can be AND or OR. Defaults to OR.
      * @param defaultOperator The default operator to be used, can be AND or OR. Defaults to OR.
      */
    def `?default_operator=`(defaultOperator: String) = self.withModifier(s"?default_operator=$defaultOperator")
  }

  /**
    * If set to true will cause format based failures (like providing text to a numeric field) to be ignored.
    * Defaults to false.
    */
  trait Lenient extends Modifier { self: BaseDriverOp =>
    /**
      * If set to true will cause format based failures (like providing text to a numeric field) to be ignored.
      * Defaults to false.
      * @param b If set to true will cause format based failures (like providing text to a numeric field) to be ignored.
      */
    def `?lenient=`(b: Boolean) = self.withModifier(s"?lenient=$b")
  }

  /**
    * For each hit, contain an explanation of how scoring of the hits was computed.
    */
  trait Explain extends Modifier { self: BaseDriverOp =>
    /**
      * For each hit, contain an explanation of how scoring of the hits was computed.
      * @param b For each hit, contain an explanation of how scoring of the hits was computed.
      */
    def `?explain=`(b: Boolean) = self.withModifier(s"?explain=$b")
  }

  /**
    * Set to false to disable retrieval of the _source field. You can also retrieve part of the document by using
    * _source_include & _source_exclude (see the request body documentation for more details)
    */
  trait SourceQuery extends Modifier { self: BaseDriverOp =>
    /**
      * Set to false to disable retrieval of the _source field. You can also retrieve part of the document by using
      * _source_include & _source_exclude (see the request body documentation for more details)
      * @param b Whether to include the matching objects source
      */
    def `?_source=`(b: Boolean) = self.withModifier(s"?_source=$b")
  }

  /**
    * Sorting to perform. Can either be in the form of fieldName, or fieldName:asc/fieldName:desc. The fieldName can
    * either be an actual field within the document, or the special _score name to indicate sorting based on scores.
    * There can be several sort parameters (order is important).
    */
  trait Sort extends Modifier { self: BaseDriverOp =>
    /**
      * Sorting to perform. Can either be in the form of fieldName, or fieldName:asc/fieldName:desc. The fieldName can
      * either be an actual field within the document, or the special _score name to indicate sorting based on scores.
      * There can be several sort parameters (order is important).
      * @param sortFields List of fields to sort (fieldName, fieldName:asc, fieldName:desc)
      */
    def `?sort=`(sortFields: String*) = self.withModifier(s"?sort=${sortFields.mkString(",")}")
  }

  /**
    * When sorting, set to true in order to still track scores and return them as part of each hit.
    */
  trait TrackScores extends Modifier { self: BaseDriverOp =>
    /**
      * When sorting, set to true in order to still track scores and return them as part of each hit.
      * @param b When sorting, set to true in order to still track scores and return them as part of each hit.
      */
    def `?track_scores=`(b: Boolean) = self.withModifier(s"?track_scores=$b")
  }

  // Search modifiers - "with data" version

  /**
    * Set to true or false to enable or disable the caching of search results for requests where size is 0,
    * ie aggregations and suggestions (no top hits returned). See Shard request cache.
    */
  trait RequestCache extends Modifier { self: BaseDriverOp =>
    /**
      * Set to true or false to enable or disable the caching of search results for requests where size is 0,
      * ie aggregations and suggestions (no top hits returned). See Shard request cache.
      * @param b enable or disable the caching of search results for requests where size is 0
      */
    def `?request_cache=`(b: Boolean) = self.withModifier(s"?request_cache=$b")
  }

  // Search modifiers - common

  /**
    * The starting from index of the hits to return. Defaults to 0.
    */
  trait From extends Modifier { self: BaseDriverOp =>
    /**
      * The starting from index of the hits to return. Defaults to 0.
      * @param n The starting from index of the hits to return. Defaults to 0.
      */
    def `?from=`(n: Integer) = self.withModifier(s"?from=$n")
  }

  /**
    * The number of hits to return. Defaults to 10.
    */
  trait Size extends Modifier { self: BaseDriverOp =>
    /**
      * The number of hits to return. Defaults to 10.
      * @param n The number of hits to return. Defaults to 10.
      */
    def `?size=`(n: Integer) = self.withModifier(s"?size=$n")
  }

  /**
    * The type of the search operation to perform. Can be dfs_query_then_fetch, query_then_fetch.
    * Defaults to query_then_fetch.
    * See Search Type for more details on the different types of search that can be performed.
    */
  trait SearchType extends Modifier { self: BaseDriverOp =>
    /**
      * The type of the search operation to perform. Can be dfs_query_then_fetch, query_then_fetch.
      * Defaults to query_then_fetch.
      * See Search Type for more details on the different types of search that can be performed.
      * @param searchType The search type: dfs_query_then_fetch, query_then_fetch
      */
    def `?search_type=`(searchType: String) = self.withModifier(s"?search_type=$searchType")
  }

  /**
    * The maximum number of documents to collect for each shard, upon reaching which the query execution will terminate
    * early. If set, the response will have a boolean field terminated_early to indicate whether the query execution
    * has actually `terminated_early`. Defaults to no terminate_after.
    */
  trait TerminateAfter extends Modifier { self: BaseDriverOp =>
    /**
      * The maximum number of documents to collect for each shard, upon reaching which the query execution will
      * terminate early. If set, the response will have a boolean field terminated_early to indicate whether the query
      * execution has actually `terminated_early`. Defaults to no terminate_after.
      * @param n The maximum number of documents to collect for each shard
      */
    def `?terminate_after=`(n: Integer) = self.withModifier(s"?terminate_after=$n")
  }

  // Misc other modifiers

  /**
    * TODO
    */
  trait Conflict extends Modifier { self: BaseDriverOp =>
    def `?conflict=`(op: String) = self.withModifier(s"?conflict=$op")
  }

  /**
    * Controls on which shard the explain is executed.
    */
  trait Preference extends Modifier { self: BaseDriverOp =>
    /**
      * Controls on which shard the explain is executed.
      * @param preference Controls on which shard the explain is executed.
      */
    def `?preference=`(preference: String) = self.withModifier(s"?preference=$preference")
  }

  /**
    * Defines if field stats should be returned on a per index level or on a cluster wide level.
    * Valid values are indices and cluster (default).
    */
  trait Level extends Modifier { self: BaseDriverOp =>
    /**
      * Defines if field stats should be returned on a per index level or on a cluster wide level.
      * Valid values are indices and cluster (default).
      * @param level indices or cluster
      */
    def `?level=`(level: String) = self.withModifier(s"?level=$level")
  }

  /**
    * A boolean value whether to read the cluster state locally in order to determine where shards are allocated
    * instead of using the Master node’s cluster state.
    */
  trait Local extends Modifier { self: BaseDriverOp =>
    /**
      * A boolean value whether to read the cluster state locally in order to determine where shards are allocated
      * instead of using the Master node’s cluster state.
      * @param b Whether the cluster state is read locally
      */
    def `?local=`(b: Boolean) = self.withModifier(s"?local=$b")
  }

  /**
    * TODO
    */
  trait IgnoreUnavailable extends Modifier { self: BaseDriverOp =>
    /**
      * TODO
      * @param b Whether to error if any of the specified clusters are not available
      */
    def `?ignore_unavailable=`(b: Boolean) = self.withModifier(s"?ignore_unavailable=$b")
  }

}
