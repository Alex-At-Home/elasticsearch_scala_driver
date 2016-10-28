package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.SearchModifiers.MultiSearchDeclaration
import org.elastic.elasticsearch.scala.driver.utils.BulkUtils
import org.elastic.rest.scala.driver.RestBase.BaseDriverOp
import org.elastic.rest.scala.driver.RestBaseImplicits.CustomTypedToString

/** Data model types used by the search resources
  */
trait DataModelSearch {

  /** The base term that all non-scrolling `_search` queries return */
  trait SearchResults

  /** A case class representing the query to execute
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-body.html Docs]]
    *
    * @param query The query term or terms that comprise the query
    * @param timeout A search timeout, bounding the search request to be executed within the specified time value and
    *                bail with the hits accumulated up to that point when expired. Defaults to no timeout.
    * @param from To retrieve hits from a certain offset. Defaults to 0.
    * @param size The number of hits to return. Defaults to 10. If you do not care about getting some hits back
    *             but only about the number of matches and/or aggregations, setting the value to 0 will help
    *             performance.
    * @param terminate_after The maximum number of documents to collect for each shard, upon reaching which the query
    *                        execution will terminate early. If set, the response will have a boolean field
    *                        terminated_early to indicate whether the query execution has actually terminated_early.
    *                        Defaults to no terminate_after.
    */
  case class Query
    (query: QueryTerm,
     timeout: Option[String] = None,
     from: Integer = 0,
     size: Integer = 10,
     terminate_after: Option[Integer] = None)

  /**
    * An implicit helper to allow developers to invoke the query resource with a single query term if they don't want
    * to change any of the search parameters
    * @param queryTerm The query term to convert to a full query
    * @return The full query with default search parameters
    */
  implicit def QueryTermToQuery(queryTerm: QueryTerm): Query = Query(queryTerm)

  /** The base trait that all query elements must inherit */
  trait QueryTerm

  /** A query term to retrieve all
    *
    * @param boost The optional boost to apply to the scoring
    */
  case class MatchAll(boost: Option[Double] = None) extends CustomTypedToString with QueryTerm {
    override def fromTyped: String = {
      val boostTerm = boost.map(b => """ "boost": $b """).getOrElse("")
      s"""{ "match_all: { $boostTerm }" }"""
    }
  }

  /** List of search operations to apply as part of the multi search operation
    *
    * @param ops The list of ES operations
    */
  case class MultiSearchOps(ops: List[BaseDriverOp with MultiSearchDeclaration]) extends CustomTypedToString {
    override def fromTyped: String = BulkUtils.buildBulkSearchOps(ops)
  }
}
object DataModelSearch extends DataModelSearch