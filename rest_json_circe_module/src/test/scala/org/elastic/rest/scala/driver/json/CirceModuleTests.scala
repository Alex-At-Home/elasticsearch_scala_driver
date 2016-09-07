package org.elastic.rest.scala.driver.json

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{TypedOperation, BaseDriverOp, EsResource}
import org.elastic.elasticsearch.scala.driver.ResourceOperations.{EsReadable, EsReadableT, EsWritable, EsWritableTU}
import org.elastic.elasticsearch.scala.driver.utils.MockElasticsearchDriver
import utest._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import org.elastic.rest.scala.driver.json.CirceModule._
import io.circe.jawn._
import io.circe._, io.circe.generic.semiauto._
import scala.reflect.runtime.universe._

import scala.concurrent.duration.Duration

object CirceModuleTests extends TestSuite {

  val tests = this {
    "Test JSON" - {
      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(TestApi.`/`(), ElasticsearchBase.PUT,
          Some("""{"test":"write"}"""), List(), List()) =>
            Future.successful("""{ "test": "written" }""")
        case BaseDriverOp(TestApi.`/`(), ElasticsearchBase.GET, _, List(), List()) =>
          Future.successful("""{ "test": "get" }""")
      }
      implicit val mockDriver = new MockElasticsearchDriver(handler)

      Await.result(TestApi.`/`().read().execJ(), Duration("1 second")) ==>
        parse("""{ "test": "get" }""").getOrElse(Json.Null)

      val json = parse("""{ "test": "write" }""").getOrElse(Json.Null)
      Await.result(TestApi.`/`().write(json).execJ(), Duration("1 second")) ==>
        parse("""{ "test": "written" }""").getOrElse(Json.Null)
    }
    "Test typed" - {
      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(TestApi.`/typed`(), ElasticsearchBase.PUT,
        Some("""{"testWrite":"write"}"""), List(), List()) =>
          Future.successful("""{ "test": "written" }""")
        case BaseDriverOp(TestApi.`/typed`(), ElasticsearchBase.GET, _, List(), List()) =>
          Future.successful("""{ "testRead": "get" }""")
      }
      implicit val mockDriver = new MockElasticsearchDriver(handler)

      CirceModule.decoderRegistry.putIfAbsent(
        weakTypeTag[TestDataModel.TestRead].tpe.toString,
        deriveDecoder[TestDataModel.TestRead].asInstanceOf[Decoder[_]]
      )
      Await.result(TestApi.`/typed`().read().exec(), Duration("1 second")) ==>
        TestDataModel.TestRead("get")

      CirceModule.encoderRegistry.putIfAbsent(
        weakTypeTag[TestDataModel.TestWrite].tpe.toString,
        deriveEncoder[TestDataModel.TestWrite].asInstanceOf[Encoder[_]]
      )
      Await.result(TestApi.`/typed`().write(TestDataModel.TestWrite("write")).execJ(),
        Duration("1 second")) ==>
          parse("""{ "test": "written" }""").getOrElse(Json.Null)
    }
  }
}
object TestDataModel {
  case class TestRead(testRead: String)
  case class TestWrite(testWrite: String)
}
object TestApi {
  case class `/`()
    extends EsReadable[BaseDriverOp]
    with EsWritable[BaseDriverOp]
    with EsResource

  case class `/typed`()
    extends EsReadableT[BaseDriverOp, TestDataModel.TestRead]
      with EsWritableTU[BaseDriverOp, TestDataModel.TestWrite]
      with EsResource
}
