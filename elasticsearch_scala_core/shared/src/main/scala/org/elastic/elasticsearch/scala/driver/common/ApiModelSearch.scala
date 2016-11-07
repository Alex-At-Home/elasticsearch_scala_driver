package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestResources._
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.elasticsearch.scala.driver.common.DataModelSearch.{MultiSearchOps, SearchResults}
import org.elastic.elasticsearch.scala.driver.common.SearchModifierGroups._
import org.elastic.elasticsearch.scala.driver.common.DataModelSearch._

/** Resources to retrieve data from Elasticsearch
  */
trait ApiModelSearch {

  //TODO: need to handle scroll?

  // 2] Search API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search.html

  // 2.1] Basic search
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-search.html

  /** Search all indexes and all types, based on the query object written to the
    * resource - URI version
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    */
  case class `/_search`()
    extends `tree:/_search`
    with RestReadableT[UriQueryParams, SearchResults]
    with RestWithDataReadableTT[QueryParams, QueryBody, SearchResults]
    with RestResource

  /** Search all indexes and the specified types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_search`(types: String*) extends RestResource
    with RestReadableT[UriQueryParams, SearchResults]
    with RestWithDataReadableTT[QueryParams, QueryBody, SearchResults]

  /** Search the specified indexes and all types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_search`(indexes: String*) extends RestResource
    with RestReadableT[UriQueryParams, SearchResults]
    with RestWithDataReadableTT[QueryParams, QueryBody, SearchResults]

  /** Search the specified indexes and types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_search`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestReadableT[UriQueryParams, SearchResults]
    with RestWithDataReadableTT[QueryParams, QueryBody, SearchResults]

  // 2.2] Search Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html

  /** Performs a search using the provided template together
    * with a set of parameters
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html Read Docs]]
    */
  case class `/_search/template`() extends `tree:/_search/template`
    with RestWithDataReadableUT[StandardParams, SearchResults]
    with RestResource

  /** Retrieves/stores/deletes templates from/to/from the .scripts index
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html Read Docs]]
    *
    * @param template The template on which to perform the CRUD
    */
  case class `/_search/template/$template`(template: String) extends RestResource
    with RestReadable[StandardParams]
    with RestWritable[StandardParams]
    with RestDeletable[StandardParams]

  /** Used to test search templates (eg before executing them)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html Read Docs]]
    */
  case class `/_render/template`() extends RestResource
    with RestWithDataReadable[StandardParams]

  // 2.3 Search Shards
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-shards.html

  /** The search shards api returns the indices and shards that a search request
    * would be executed against. This can give useful feedback for working out issues
    * or planning optimizations with routing and shard preferences.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-shards.html Read Docs]]
    *
    * @param indexes The index or indexes to query
    */
  case class `/$indexes/_search_shards`(indexes: String*) extends RestResource
    with RestWithDataReadable[QuerySearchShardsParams]

  // 2.4 Suggesters
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html

  /** The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html Docs]]
    */
  case class `/_suggest`() extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[MiscQueryParams]

  /** The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html Docs]]
    *
    * @param indexes The indexes over which to query
    */
  case class `/$indexes/_suggest`(indexes: String*) extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[MiscQueryParams]

  /** The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html Docs]]
    *
    * @param types The types over which to query
    */
  case class `/_all/$types/_suggest`(types: String*) extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[MiscQueryParams]

  /** The suggest feature suggests similar looking terms based on a
    * provided text by using a suggester.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html Docs]]
    *
    * @param indexes The indexes over which to query
    * @param types The types over which to query
    */
  case class `/$indexes/$types/_suggest`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[MiscQueryParams]

  // 2.5 Multi search
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html

  /** Search all indexes and all types, based on the query objects written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html Docs]]
    */
  case class `/_msearch`() extends RestResource
    with RestWithDataReadableTU[StandardParams, MultiSearchOps]

  /** Search all indexes and the specified types, based on the query objects written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html Docs]]
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_msearch`(types: String*) extends RestResource
    with RestWithDataReadableTU[StandardParams, MultiSearchOps]

  /** Search the specified indexes and all types, based on the query objects written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html Docs]]
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_msearch`(indexes: String*) extends RestResource
    with RestWithDataReadableTU[StandardParams, MultiSearchOps]

  /** Search the specified indexes and types, based on the query objects written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html Docs]]
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_msearch`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestWithDataReadableTU[StandardParams, MultiSearchOps]

  // 2.6 Count API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html

  /** Count all indexes and all types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html Docs]]
    */
  case class `/_count`() extends RestResource
    with RestReadable[QueryCountParams] //TODO is this really true, seems like you'd need query_count to not be applied for one of these? (Also below)
    with RestWithDataReadable[QueryCountParams]

  /** Count all indexes and the specified types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html Docs]]
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_count`(types: String*) extends RestResource
    with RestReadable[QueryCountParams]
    with RestWithDataReadable[QueryCountParams]

  /** Count the specified indexes and all types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html Docs]]
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_count`(indexes: String*) extends RestResource
    with RestReadable[QueryCountParams]
    with RestWithDataReadable[QueryCountParams]

  /** Count the specified indexes and types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html Docs]]
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_count`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestReadable[QueryCountParams]
    with RestWithDataReadable[QueryCountParams]

  // 2.7 Validate API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html

  /** Validate the query over alk indexes and types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html Docs]]
    */
  case class `/_validate`() extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[QueryCountParams]

  /** Validate the query over the specified types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html Docs]]
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_validate`(types: String*) extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[QueryCountParams]

  /** Validate the query over the specified indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html Docs]]
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_validate`(indexes: String*) extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[QueryCountParams]

  /** Validate the query over the specified indexes and types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html Docs]]
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_validate`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestReadable[MiscQueryParams]
    with RestWithDataReadable[QueryCountParams]

  // 2.8 Explain API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html

  /** Explain the query over all indexes and types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html Docs]]
    */
  case class `/_explain`() extends RestResource
    with RestReadable[QueryExplainParams]
    with RestWithDataReadable[QueryExplainParams]

  /** Explain the query over the specified types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html Docs]]
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_explain`(types: String*) extends RestResource
    with RestReadable[QueryExplainParams]
    with RestWithDataReadable[QueryExplainParams]

  /** Explain the query over the specified indexes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html Docs]]
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_explain`(indexes: String*) extends RestResource
    with RestReadable[QueryExplainParams]
    with RestWithDataReadable[QueryExplainParams]

  /** Explain the query over the specified indexes and types
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html Docs]]
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_explain`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestReadable[QueryExplainParams]
    with RestWithDataReadable[QueryExplainParams]

  // 2.9 Percolation API
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-percolate.html

  //TODO add this set of operations later on

}
object ApiModelSearch extends ApiModelSearch
