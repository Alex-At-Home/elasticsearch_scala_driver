package org.elastic.rest.scala.driver.json

import utest._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import io.circe.jawn._
import io.circe._
import io.circe.generic.JsonCodec
import org.elastic.rest.scala.driver.RestBase
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.ResourceOperations._
import org.elastic.rest.scala.driver.utils.MockRestDriver
import org.elastic.rest.scala.driver.json.CirceTypeModule._
import org.elastic.rest.scala.driver.json.CirceJsonModule._

import scala.concurrent.duration.Duration

object CirceModuleTests extends TestSuite {

  val tests = this {
    "Test JSON" - {
      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(TestApi.`/`(), RestBase.PUT,
          Some("""{"test":"write"}"""), List(), List()) =>
            Future.successful("""{ "test": "written" }""")
        case BaseDriverOp(TestApi.`/`(), RestBase.GET, _, List(), List()) =>
          Future.successful("""{ "test": "get" }""")
      }
      implicit val mockDriver = new MockRestDriver(handler)

      Await.result(TestApi.`/`().read().execJ(), Duration("1 second")) ==>
        parse("""{ "test": "get" }""").getOrElse(Json.Null)

      val json = parse("""{ "test": "write" }""").getOrElse(Json.Null)
      Await.result(TestApi.`/`().write(json).execJ(), Duration("1 second")) ==>
        parse("""{ "test": "written" }""").getOrElse(Json.Null)
    }
    "Test typed" - {
      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(TestApi.`/typed`(), RestBase.PUT,
        Some("""{"testWrite":"write"}"""), List(), List()) =>
          Future.successful("""{ "test": "written" }""")
        case BaseDriverOp(TestApi.`/typed`(), RestBase.GET, _, List(), List()) =>
          Future.successful("""{ "testRead": "get" }""")
      }
      implicit val mockDriver = new MockRestDriver(handler)

      Await.result(TestApi.`/typed`().read().exec(), Duration("1 second")) ==>
        TestDataModel.TestRead("get")

      Await.result(TestApi.`/typed`().write(TestDataModel.TestWrite("write")).execJ(),
        Duration("1 second")) ==>
          parse("""{ "test": "written" }""").getOrElse(Json.Null)
    }
  }
}

/** Test object containing example data model for `TestApi`
  * (sidenote: annotating `TestDataModel` doesn't make `TestDataModelComponent` visible)
  */
object TestDataModel extends TestDataModelComponent{
  @JsonCodec case class TestRead(testRead: String)
  @JsonCodec case class TestWrite(testWrite: String)
}

/**Illustrates the case where sub-components are used to partition
  * the code
  */
trait TestDataModelComponent {
  //TODO: my x-coder case doesn't work if this is a trait, need to add test case and fix
  @JsonCodec case class OtherTestRead(testRead: String)
}

/** Sample API for testing CIRCE integration
  */
object TestApi {
  case class `/`()
    extends RestReadable[BaseDriverOp]
    with RestWritable[BaseDriverOp]
    with RestResource

  case class `/typed`()
    extends RestReadableT[BaseDriverOp, TestDataModel.TestRead]
      with RestWritableTU[BaseDriverOp, TestDataModel.TestWrite]
      with RestResource
}
