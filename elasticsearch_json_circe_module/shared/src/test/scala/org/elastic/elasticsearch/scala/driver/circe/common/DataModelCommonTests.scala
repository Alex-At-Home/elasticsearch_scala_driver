package org.elastic.elasticsearch.scala.driver.circe.common

import utest._
import org.elastic.elasticsearch.scala.driver.circe.versions.DataModelVersions.{latest => data_model}
import org.elastic.elasticsearch.scala.driver.versions.ApiModelVersions.{latest => api}
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.utils.MockRestDriver
import org.elastic.rest.scala.driver.json.fixed_typing.CirceTypeModule._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object DataModelCommonTests extends TestSuite {

  val tests = this {
    "Check typed version of /" - {

      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        case BaseDriverOp(api.`/`(), "GET", _, _, _) =>
          Future.successful(
            """{
              "name": "a", "cluster_name": "b",
              "version": {
                "number": "c", "build_hash": "d", "build_timestamp": "e", "build_snapshot": true, "lucene_version": "f"
              },
              "tagline": "g"
            }""")
      }
      implicit val mockDriver = new MockRestDriver(handler)


      val expectedResult = data_model.ElasticsearchInfo(
        name = "a", cluster_name = "b",
        version = data_model.ElasticsearchInfo.VersionInfo(
          number = "c", build_hash = "d", build_timestamp = "e", build_snapshot = true, lucene_version = "f"),
        tagline = "g")

      api().read().exec().map { result =>
        result.name ==> "a"
        result.cluster_name ==> "b"
        result.version.number ==> "c"
        result.version.build_hash ==> "d"
        result.version.build_timestamp ==> "e"
        result.version.build_snapshot ==> true
        result.version.lucene_version ==> "f"
        result.tagline ==> "g"
      }
    }
  }
}
