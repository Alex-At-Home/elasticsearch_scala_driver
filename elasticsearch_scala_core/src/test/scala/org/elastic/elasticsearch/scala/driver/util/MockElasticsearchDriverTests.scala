package org.elastic.elasticsearch.scala.driver.util

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._
import org.elastic.elasticsearch.scala.driver.versions.Versions
import utest._
import Versions.latest._
import org.elastic.elasticsearch.scala.driver.utils.MockElasticsearchDriver

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object MockElasticsearchDriverTests extends TestSuite {

  val tests = this {
    "Basic mock functionality" - {

      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(`/$index`(index: String), "GET", None, List(), List()) =>
          Future.successful(s"Received: /$index")
      }
      val mockDriver = new MockElasticsearchDriver(handler)

      // Handler
      {
        val future = `/$index`("test").read().execS()(mockDriver)
        val retVal = Await.result(future, Duration("1 second"))
        retVal ==> "Received: /test"
      }
      // Unhandled:
      {
        val future = `/`().read().execS()(mockDriver)
          .recover { case e: EsServerException => s"${e.code}" }

        val retVal = Await.result(future, Duration("1 second"))
        retVal ==> "404"
      }
    }
  }
}
