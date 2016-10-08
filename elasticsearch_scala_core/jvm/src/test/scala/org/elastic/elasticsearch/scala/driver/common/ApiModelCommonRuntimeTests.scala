package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.DataModelCommon.{BulkIndexOps, ElasticsearchInfo}
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.RestBaseRuntimeTyped.RuntimeStringToTypedHelper
import org.elastic.rest.scala.driver.utils.{MockRestDriver, NoJsonHelpers}
import utest._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.reflect.runtime.universe._


object ApiModelCommonRuntimeTests extends TestSuite {

  val tests = this {
    "Custom typed operations: return values" - {
      object latest extends ApiModelCommon
      import org.elastic.rest.scala.driver.RestBaseRuntimeTyped.RuntimeTypedOperation

      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(latest.`/`(), _, None, List(("pretty", true)), _) =>
          Future.successful("test_cluster")
      }
      implicit val mockDriver = new MockRestDriver(handler)

      implicit val stringToTypedHelper = new RuntimeStringToTypedHelper() {
        override def toType[T](s: String)(implicit ct: WeakTypeTag[T]): T = {
          if (ct.tpe =:= typeOf[ElasticsearchInfo]) {
            ElasticsearchInfo(
              s, s, ElasticsearchInfo.VersionInfo("", "", "", build_snapshot = true, ""), "You know, for search")
          }.asInstanceOf[T]
          else throw RestRequestException(s"Internal logic error: toType $ct vs ElasticsearchInfo")
        }
      }

      val result = Await.result(latest.`/`().read().pretty(true).exec(), Duration("1 second"))
      result.cluster_name ==> "test_cluster"
    }
  }
}
