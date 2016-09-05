package org.elastic.elasticsearch.scala.driver

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._
import org.elastic.elasticsearch.scala.driver.common.ApiModelCommon.`/$uri`
import org.elastic.elasticsearch.scala.driver.utils.MockElasticsearchDriver
import utest._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object ElasticsearchBaseTests extends TestSuite {

  val tests = this {
    "Check modifiers work" - {

      case class ModTest() extends Modifier {
        def getStringModifier(s: String): String = this.getModifier(s)

        def getBooleanModifier(b: Boolean): String = this.getModifier(b)

        def getListModifier(ss: List[String]): String = this.getModifier(ss)
      }
      ModTest().getStringModifier("x") ==> "getStringModifier=x"
      ModTest().getBooleanModifier(true) ==> "getBooleanModifier=true"
      ModTest().getListModifier(List("x", "y")) ==> "getListModifier=x,y"
    }
    "Check the location generator works" - {
      case class `/`() extends EsResource
      case class `/$list`(list: Seq[String]) extends EsResource
      case class `/test/$variable/test2/$anotherVariable`
        (variable: String, anotherVariable: String) extends EsResource
      case class `/test/$list`(ss: String*) extends EsResource

      `/`().location ==> "/"
      `/$list`(Seq("a", "b")).location ==> "/a,b"
      `/test/$variable/test2/$anotherVariable`("a", "b").location ==> "/test/a/test2/b"
      `/test/$list`("a", "b").location ==> "/test/a,b"
    }
    "Check JSON serialization" - {
      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(`/$uri`(index), "GET", None, List(), List()) =>
          Future.successful(s"""{"index":"$index"}""")
      }
      implicit val mockDriver = new MockElasticsearchDriver(handler)

      case class MockJson(s: String)
      implicit class StringToJsonHelper(op: BaseDriverOp) {
        def execJ()(implicit driver: EsDriver): Future[MockJson] =
          driver.exec(op).map(s => MockJson(s))
      }
      val res = Await.result(`/$uri`("test").read().execJ(), Duration("1 second"))
      res ==> MockJson("""{"index":"test"}""")
    }
    "Check JSON de-serialization" - {
      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(`/$uri`(index), method @ _, Some(s @ _), List(), List()) =>
          Future.successful(s"$method: $s")
        case BaseDriverOp(`/$uri`(index), method @ _, None, List(), List()) =>
          Future.successful(s"$method")
      }
      implicit val mockDriver = new MockElasticsearchDriver(handler)

      case class MockJson(s: String)
      implicit val myStringToJsonHelper = new JsonToStringHelper[MockJson] {
        override def fromJson(j: MockJson): String = j match { case MockJson(s) => s }
      }
      // Read with data
      {
        val res = Await.result(`/$uri`("test").read(MockJson("test")).execS(), Duration("1 second"))
        res ==> "GET: test"
      }
      // Write
      {
        val res = Await.result(`/$uri`("test").write(MockJson("test")).execS(), Duration("1 second"))
        res ==> "PUT: test"
      }
      // Delete with data
      {
        val res = Await.result(`/$uri`("test").delete(MockJson("test")).execS(), Duration("1 second"))
        res ==> "DELETE: test"
      }
      // (Check the operations without data while we're here)
      // Check
      {
        val res = Await.result(`/$uri`("test").check().execS(), Duration("1 second"))
        res ==> "HEAD"
      }
      // Read
      {
        val res = Await.result(`/$uri`("test").read().execS(), Duration("1 second"))
        res ==> "GET"
      }
      // Write
      {
        val res = Await.result(`/$uri`("test").write().execS(), Duration("1 second"))
        res ==> "PUT"
      }
      // Delete
      {
        val res = Await.result(`/$uri`("test").delete().execS(), Duration("1 second"))
        res ==> "DELETE"
      }
    }
  }
}
