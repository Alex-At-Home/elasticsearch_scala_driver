package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree.`tree:/`
import org.elastic.rest.scala.driver.RestResources._
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.elasticsearch.scala.driver.common.SearchModifierGroups._

//TODO: remove this when I can
import org.elastic.elasticsearch.driver.scala.OperationGroups._

/** Resources to retrieve data from Elasticsearch
  */
trait ApiModelSearch {

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
    with RestReadable[UriQueryParams]
    with RestWithDataReadable[QueryParams]
    with RestResource

  /** Search all indexes and the specified types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    *
    * @param types The types over which to search
    */
  case class `/_all/$types/_search`(types: String*) extends RestResource
    with RestReadable[UriQueryParams]
    with RestWithDataReadable[QueryParams]

  /** Search the specified indexes and all types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    *
    * @param indexes The indexes over which to search
    */
  case class `/$indexes/_search`(indexes: String*) extends RestResource
    with RestReadable[UriQueryParams]
    with RestWithDataReadable[QueryParams]

  /**
    * Search the specified indexes and types, based on the query object written to the
    * resource
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-uri-request.html Read Docs]]
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.0/search-request-body.html Write Docs]]
    *
    * @param indexes The indexes over which to search
    * @param types The types over which to search
    */
  case class `/$indexes/$types/_search`(indexes: Seq[String], types: Seq[String]) extends RestResource
    with RestReadable[UriQueryParams]
    with RestWithDataReadable[QueryParams]

  // 2.2] Search Templates
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html

  /**
    * Performs a search using the provided template together
    * with a set of parameters
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html Read Docs]]
    */
  case class `/_search/template`() extends RestResource
    with RestWithDataReadable[StandardParams]

  /**
    * Retrieves/stores/deletes templates from/to/from the .scripts index
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html Read Docs]]
    *
    * @param template The template on which to perform the CRUD
    */
  case class `/_search/template/$template`(template: String) extends RestResource
    with RestReadable[StandardParams]
    with RestWritable[StandardParams]
    with RestDeletable[StandardParams]

  /**
    * Used to test search templates (eg before executing them)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html Read Docs]]
    */
  case class `/_render/template`() extends RestResource
    with RestWithDataReadable[StandardParams]

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

}
object ApiModelSearch extends ApiModelSearch
