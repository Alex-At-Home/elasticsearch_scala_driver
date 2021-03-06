package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.common.ApiModelCommon._
import org.elastic.elasticsearch.scala.driver.common.ApiModelSearch._
import org.elastic.elasticsearch.scala.driver.common.CommonModifiers.BulkManagementDeclaration
import org.elastic.elasticsearch.scala.driver.common.SearchModifiers.MultiSearchDeclaration
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.RestBase

/**
  * Utility methods to add a nice custom typed API for bulk operations
  */
object BulkUtils {

  /**
    * Translates from REST method to ES operation
 *
    * @param m The REST method
    * @return The ES operation string
    */
  private def methodToBulkIndexOp(m: String): String = m match {
    case RestBase.PUT => "index"
    case RestBase.POST => "index"
    case RestBase.DELETE => "delete"
    case _ => throw RestRequestException(s"This method is not supported by _bulk: $m")
  }

  /** Handles default index and types
    *
    * @param field "_index" or "_type"
    * @param value The index or type or "" to use default
    * @return A block of JSON to insert into the JSON object
    */
  private def addBlock(field: String, value: String) = value match {
    case "" => ""
    case _ => s""" "$field": "$value" """
  }

  /** Adds an optional body block (eg DELETE doesn't have any body)
    *
    * @param body The optional body
    * @return The "_bulk"-ified body (with newline) if present
    */
  private def addBodyBlock(body: Option[String]) = body.map(b => s"\n$b").getOrElse("")

  /** Add modifiers to the JSON
    *
    * @param mods The list of modifiers
    * @return A list of JSON-ified mods
    */
  private def addMods(mods: List[(String, Any)]): List[String] = mods.map {
    case (key, value: List[_]) =>
      s""" "$key": [ ${value.map(modValueToString).mkString(",")} ] """
    case (key, other: Any) =>
      s""" "$key": ${modValueToString(other)} """
  }

  /** JSON-ify a single value
    *
    * @param v A JSON-ifiable atomic type
    * @return A JSON-ified string
    */
  private def modValueToString(v: Any): String = v match {
    case s: String => s""""$s""""
    case o: Any => s"""$o"""
  }

  /** Convert a list of strings into a block of JSON clauses
    *
    * @param l The list of strings
    */
  private def listToString(l: List[String]) = l.filter(!_.isEmpty).mkString(",")

  /** List of operations to apply as part of the bulk operation
    *
    * @param ops The list of ES operations
    * @return The body to send to the client
    */
  def buildBulkIndexOps(ops: List[BaseDriverOp with BulkManagementDeclaration]): String = ops.map {

    case BaseDriverOp(`/$index`(index),
      method @ _, body @ _, mods @ _, _) =>
        val blocks = listToString(List(addBlock("_index", index)) ++ addMods(mods))
        s"""{ "${methodToBulkIndexOp(method)}": { $blocks } }${addBodyBlock(body)}"""

    case BaseDriverOp(`/$index/$type`(index, theType),
      method @ _, body @ _, mods @ _, _) =>
        val blocks = listToString(List(
          addBlock("_index", index), addBlock("_type", theType)) ++
          addMods(mods))
        s"""{ "${methodToBulkIndexOp(method)}": { $blocks } }${addBodyBlock(body)}"""

    case BaseDriverOp(`/$index/$type/$id`(index, theType, id),
      method @ _, body @ _, mods @ _, _) =>
        val blocks = listToString(List(
          addBlock("_index",index), addBlock("_type", theType), addBlock("_id", id)) ++
          addMods(mods))
        s"""{ "${methodToBulkIndexOp(method)}": { $blocks } }${addBodyBlock(body)}"""

    case BaseDriverOp(`/$index/$type/$id/_update`(index, theType, id),
      _, body @ _, mods @ _, _) =>
        val blocks = listToString(List(
          addBlock("_index", index), addBlock("_type", theType), addBlock("_id", id)) ++
          addMods(mods))
        s"""{ "update": { $blocks } }${addBodyBlock(body)}"""

    case op @ _ => throw RestRequestException(s"This operation is not supported by _bulk: $op")

  }.mkString("\n")

  /** The list of searches to apply as part of the search operation
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html Docs]]
    * @param ops The list of multi-searchable operations
    * @return The body to send to the client
    */
  def buildBulkSearchOps(ops: List[BaseDriverOp with MultiSearchDeclaration]): String = ops.map {

    case BaseDriverOp(`/_search`(),
      RestBase.GET, body @ _, mods @ _, _) =>
        val blocks = listToString(addMods(mods))
        s"""{ $blocks }${addBodyBlock(body)}"""

    case BaseDriverOp(`/_all/$types/_search`(types @ _*),
      RestBase.GET, body @ _, mods @ _, _) =>
        val blocks = listToString(List(addBlock("type", types.mkString(","))) ++ addMods(mods))
        s"""{ $blocks }${addBodyBlock(body)}"""

    case BaseDriverOp(`/$indexes/_search`(indexes @ _*),
      RestBase.GET, body @ _, mods @ _, _) =>
        val blocks = listToString(List(addBlock("index", indexes.mkString(","))) ++ addMods(mods))
        s"""{ $blocks }${addBodyBlock(body)}"""

    case BaseDriverOp(`/$indexes/$types/_search`(indexes, types),
      RestBase.GET, body @ _, mods @ _, _) =>
        val blocks = listToString(List(
          addBlock("index", indexes.mkString(",")), addBlock("type", types.mkString(","))) ++
          addMods(mods))
        s"""{ $blocks }${addBodyBlock(body)}"""

    case op @ _ => throw RestRequestException(s"This operation is not supported by _msearch: $op")

  }.mkString("\n")
}
