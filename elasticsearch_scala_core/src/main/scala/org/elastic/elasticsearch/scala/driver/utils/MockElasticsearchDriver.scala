package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsDriver, EsServerException}

import scala.concurrent.Future

/** A mock driver that you pass the list of handlers into.
  * Note that BaseDriverOp can be decomposed as
  * BaseDriverOp(resource, method, body, params, headers)
  *
  * @param handler The partial function, a set of cases on the decomposed BaseDriverOp
  */
class MockElasticsearchDriver
  (handler: PartialFunction[BaseDriverOp, Future[String]]) extends EsDriver
{
  private val fallback: PartialFunction[BaseDriverOp, Future[String]] =
   { case _ => Future.failed(EsServerException(404, "Resource not found", None)) }

  def exec(op: BaseDriverOp): Future[String] = (handler orElse fallback)(op)
}
