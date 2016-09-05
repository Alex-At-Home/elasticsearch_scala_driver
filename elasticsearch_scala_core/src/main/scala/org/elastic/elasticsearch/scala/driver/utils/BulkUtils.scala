package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsRequestException}
import org.elastic.elasticsearch.scala.driver.common.ApiModelCommon.{`/$index/$type/$id/_update`, `/$index/$type/$id`, `/$index/$type`, `/$index`}

/**
  * Utility methods to add a nice custom typed API for bulk operations
  */
object BulkUtils {

  /**
    * Translates from REST method to ES operation
    * @param m The REST method
    * @return The ES operation string
    */
  private def methodToBulkOp(m: String): String = m match {
    case ElasticsearchBase.PUT => "index"
    case ElasticsearchBase.POST => "index"
    case ElasticsearchBase.DELETE => "delete"
    case _ => throw EsRequestException(s"This method is not supported by _bulk: $m")
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

  /** List of operations to apply as part of the bulk operation
    *
    * @param ops The list of ES operations
    * @return builds a JSON string out of the list of operations
    */
  def buildBulkOps(ops: List[BaseDriverOp]): String = ops.map {

    case BaseDriverOp(`/$index`(index),
    method @ _, Some(body @ _), _, _) =>
      s"""{ "${methodToBulkOp(method)}": { ${addBlock("_index", index)} } }\n$body """

    case BaseDriverOp(`/$index/$type`(index, theType),
    method @ _, Some(body @ _), _, _) =>
      s"""{ "${methodToBulkOp(method)}": { ${addBlock("_index", index)}, ${addBlock("_type", theType)} } }\n$body """

    case BaseDriverOp(`/$index/$type/$id`(index, theType, id),
    method @ _, Some(body @ _), _, _) =>
      s"""{ "${methodToBulkOp(method)}": { ${addBlock("_index", index)}, ${addBlock("_type", theType)}, "_id": "$id" } }\n$body """

    case BaseDriverOp(`/$index/$type/$id/_update`(index, theType, id),
    _, Some(body @ _), _, _) =>
      s"""{ "update": { ${addBlock("_index", index)}, ${addBlock("_type", theType)}, "_id": "$id" } }\n$body """

    case op @ _ => throw EsRequestException(s"This operation is not supported by _bulk: $op")

  }.mkString("\n")
}
