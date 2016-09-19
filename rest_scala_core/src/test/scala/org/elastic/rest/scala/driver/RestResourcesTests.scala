package org.elastic.rest.scala.driver

import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.ResourceOperations._
import utest._

import scala.concurrent.Future

object RestResourcesTests extends TestSuite {

  def formatter(op: BaseDriverOp): (String, Option[String]) = (op.op, op.body)

  import util.SampleResources._

  case class MockJson(s: String)
  implicit val myStringToJsonHelper = new JsonToStringHelper[MockJson] {
    override def fromJson(j: MockJson): String = j match { case MockJson(s) => s }
  }

  val tests = this {
    "Test all resources, untyped" - {
      formatter(`/$resource`("/").check()) ==> ("HEAD", None)

      formatter(`/$resource`("/").read()) ==> ("GET", None)
      formatter(`/$resource`("/").read("body")) ==> ("GET", Some("body"))
      formatter(`/$resource`("/").read(MockJson("body"))) ==> ("GET", Some("body"))

      //TODO sends

      formatter(`/$resource`("/").write()) ==> ("PUT", None)
      formatter(`/$resource`("/").write("body")) ==> ("PUT", Some("body"))
      formatter(`/$resource`("/").write(MockJson("body"))) ==> ("PUT", Some("body"))

      formatter(`/$resource`("/").delete()) ==> ("DELETE", None)
      formatter(`/$resource`("/").delete("body")) ==> ("DELETE", Some("body"))
      formatter(`/$resource`("/").delete(MockJson("body"))) ==> ("DELETE", Some("body"))
    }
    "Test all resources, typed output" - {
      //TODO
    }
    "Test all resources, typed input" - {
      //TODO
    }
    "Test all resources, typed input and output" - {
      //TODO
    }
  }
}
