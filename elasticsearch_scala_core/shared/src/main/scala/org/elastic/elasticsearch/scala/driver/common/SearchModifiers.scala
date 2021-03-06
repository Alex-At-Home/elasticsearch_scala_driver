package org.elastic.elasticsearch.scala.driver.common

import CommonModifiers._
import CommonModifierGroups._
import org.elastic.rest.scala.driver.RestBase._

/** Value class representing the default search operator */
case class DefaultOperator(value: String) extends AnyVal with ToStringAnyVal[String]
/** Available default search operators */
object DefaultOperator {
  @Constant val AND, OR = ToStringAnyVal.AutoGenerate[DefaultOperator]
}

/** Value class representing the different search types */
case class SearchType(value: String) extends AnyVal with ToStringAnyVal[String]
/** Available search types */
object SearchType {
  @Constant val dfs_query_then_fetch, query_then_fetch = ToStringAnyVal.AutoGenerate[SearchType]
}

/** Value class representing the different search preferences */
case class Preference(value: String) extends AnyVal with ToStringAnyVal[String]
/** Available search types */
object Preference {
  @Constant val _primary, _primary_first, _replica, _replica_first, _local, _only_nodes =
    ToStringAnyVal.AutoGenerate[Preference]

  def _only_node(node: String) = Preference(s"_only_node:$node")
  def _prefer_node(node: String) = Preference(s"_prefer_node:$node")
  def _shards(shards: String*) = Preference(s"_shards:${shards.mkString(",")}")
}


/** Modifiers for Elasticsearch resources - apply to search related API resources
  */
object SearchModifiers {

  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search.html

  // Search modifiers - URI version

  /** (modifier - see method for details) */
  trait Query extends Modifier {
    /** The query string (maps to the query_string query)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param query The query string (maps to the query_string query)
      * @return The updated driver operation
      */
    @Param def query(query: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait DefaultField extends Modifier {
    /** The default field to use when no field prefix is defined within the query.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param defaultField The default field to use when no field prefix is defined within the query
      * @return The updated driver operation
      */
    @Param def df(defaultField: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Analyzer extends Modifier {
    /** The analyzer name to be used when analyzing the query string.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param analyzer The analyzer name to be used when analyzing the query string.
      * @return The updated driver operation
      */
    @Param def analyzer(analyzer: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait LowercaseExpandedTerms extends Modifier {
    /** Should terms be automatically lowercased or not. Defaults to `true`.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param b Should terms be automatically lowercased or not. Defaults to `true`.
      * @return The updated driver operation
      */
    @Param def lowercase_expanded_terms(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait AnalyzeWildcard extends Modifier {
    /** Should wildcard and prefix queries be analyzed or not. Defaults to `false`.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param b Should wildcard and prefix queries be analyzed or not. Defaults to `false`.
      * @return The updated driver operation
      */
    @Param def analyze_wildcard(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait DefaultOperator extends Modifier {
    /**
      * The default operator to be used, can be AND or OR. Defaults to OR.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param defaultOperator The default operator to be used, can be AND or OR. Defaults to OR.
      * @return The updated driver operation
      */
    @Param def default_operator
      (defaultOperator: org.elastic.elasticsearch.scala.driver.common.DefaultOperator): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Lenient extends Modifier {
    /**
      * If set to true will cause format based failures (like providing text to a numeric field) to be ignored.
      * Defaults to false.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param b If set to true will cause format based failures (like providing text to a numeric field) to be ignored.
      * @return The updated driver operation
      */
    @Param def lenient(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Sort extends Modifier {
    /**
      * Sorting to perform. Can either be in the form of fieldName, or fieldName:asc/fieldName:desc. The fieldName can
      * either be an actual field within the document, or the special _score name to indicate sorting based on scores.
      * There can be several sort parameters (order is important).
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param sortFields List of fields to sort (fieldName, fieldName:asc, fieldName:desc)
      * @return The updated driver operation
      */
    @Param def sort(sortFields: String*): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait TrackScores extends Modifier {
    /**
      * When sorting, set to true in order to still track scores and return them as part of each hit.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * @param b When sorting, set to true in order to still track scores and return them as part of each hit.
      * @return The updated driver operation
      */
    @Param def track_scores(b: Boolean): this.type = Modifier.Body
  }

  // Search modifiers - standard "body data" version

  /** (modifier - see method for details) */
  trait RequestCache extends Modifier {
    /** Set to true or false to enable or disable the caching of search results for requests where size is 0,
      * ie aggregations and suggestions (no top hits returned). See Shard request cache.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html#_parameters_4]]
      *
      * @param b enable or disable the caching of search results for requests where size is 0
      * @return The updated driver operation
      */
    @Param def request_cache(b: Boolean): this.type = Modifier.Body
  }

  // Search modifiers - common

  /** (modifier - see method for details) */
  trait From extends Modifier {
    /** The starting from index of the hits to return. Defaults to 0.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html#_parameters_4]]
      *
      * @param n The starting from index of the hits to return. Defaults to 0.
      * @return The updated driver operation
      */
    @Param def from(n: Integer): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Size extends Modifier {
    /** The number of hits to return. Defaults to 10.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html#_parameters_4]]
      *
      * @param n The number of hits to return. Defaults to 10.
      * @return The updated driver operation
      */
    @Param def size(n: Integer): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait SearchType extends Modifier {
    /** The type of the search operation to perform. Can be dfs_query_then_fetch, query_then_fetch.
      * Defaults to query_then_fetch.
      * See Search Type for more details on the different types of search that can be performed.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html#_parameters_4]]
      *
      * @param searchType The search type: dfs_query_then_fetch, query_then_fetch
      * @return The updated driver operation
      */
    @Param def search_type
      (searchType: org.elastic.elasticsearch.scala.driver.common.SearchType): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait TerminateAfter extends Modifier {
    /**  The maximum number of documents to collect for each shard, upon reaching which the query execution will
      * terminate early. If set, the response will have a boolean field terminated_early to indicate whether the query
      * execution has actually `terminated_early`. Defaults to no terminate_after.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html#_parameters_4]]
      *
      * @param n The maximum number of documents to collect for each shard
      * @return The updated driver operation
      */
    @Param def terminate_after(n: Integer): this.type = Modifier.Body
  }

  // Query modifiers - shard information

  /** (modifier - see method for details) */
  trait Preference extends Modifier {
    /** Controls on which shard the explain is executed.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-shards.html#_all_parameters]]
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-preference.html Docs]]
      *
      * @param preference Controls on which shard the explain is executed.
      * @return The updated driver operation
      */
    @Param def preference
      (preference: org.elastic.elasticsearch.scala.driver.common.Preference): this.type = Modifier.Body
  }

  /** Indicates that the result operation can be used in a multi search */
  trait MultiSearchDeclaration
}
/** Common groupings of modifiers relating to search resources
  */
object SearchModifierGroups {
  import SearchModifiers._

  /** Parameters controlling query operations - URI version */
  trait UriQueryParams
    extends Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with SearchModifiers.DefaultOperator with Lenient with Explain
      with SourceBase with SourceInclude with SourceExclude
      with Fields with Sort with TrackScores with Timeout with TerminateAfter
      with From with Size with SearchModifiers.SearchType
      with StandardParams

  /** Parameters controlling query operations - standard (body) version */
  trait QueryParams
    extends Timeout with RequestCache with TerminateAfter
      with From with Size with SearchModifiers.SearchType
      with StandardParams
      with MultiSearchDeclaration

  /** Parameters controlling information about which shards are affected by a query */
  trait QuerySearchShardsParams extends Routing with SearchModifiers.Preference with Local with StandardParams

  /** Parameters controlling various other query resources (than the URI/body search ones) */
  trait MiscQueryParams
    extends Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with SearchModifiers.DefaultOperator with Lenient
      with StandardParams

  /** Parameters controlling counting docs that match a query */
  trait QueryCountParams
    extends Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with SearchModifiers.DefaultOperator with Lenient
      with TerminateAfter
      with StandardParams

  /** Parameters controlling the query explain resources */
  trait QueryExplainParams
    extends Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with SearchModifiers.DefaultOperator with Lenient
      with SourceBase with SourceInclude with SourceExclude
      with Fields with Routing with Parent with SearchModifiers.Preference
      with StandardParams
}
