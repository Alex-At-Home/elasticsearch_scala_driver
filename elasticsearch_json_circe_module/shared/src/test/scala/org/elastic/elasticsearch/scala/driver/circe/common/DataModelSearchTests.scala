package org.elastic.elasticsearch.scala.driver.circe.common

import io.circe.parser.parse
import org.elastic.elasticsearch.scala.driver.common.ApiModelSearch
import org.elastic.rest.scala.driver.RestBase.BaseDriverOp
import utest._
import org.elastic.elasticsearch.scala.driver.versions.ApiModelVersions.{latest => api}
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.utils.MockRestDriver
import org.elastic.rest.scala.driver.json.flexible_typing.CirceTypeModule._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object DataModelSearchTests extends TestSuite {
  import DataModelSearch._

  /** This is used in 2 tests */
  val commonTestInput =
    """
      |{
      |  "took" : 63,
      |  "timed_out" : false,
      |  "_shards" : {
      |    "total" : 5,
      |    "successful" : 4,
      |    "failed" : 1
      |  },
      |  "hits" : {
      |    "total" : 1000,
      |    "max_score" : 1.0,
      |    "hits" : [ {
      |      "_index" : "bank",
      |      "_type" : "account",
      |      "_id" : "1",
      |      "_score" : 1.0, "_source" : {"account_number":1,"balance":39225,"firstname":"Amber","lastname":"Duke","age":32,"gender":"M","address":"880 Holmes Lane","employer":"Pyrami","email":"amberduke@pyrami.com","city":"Brogan","state":"IL"}
      |    }, {
      |      "_index" : "bank",
      |      "_type" : "account",
      |      "_id" : "6",
      |      "_score" : 1.0, "_source" : {"account_number":6,"balance":5686,"firstname":"Hattie","lastname":"Bond","age":36,"gender":"M","address":"671 Bristol Street","employer":"Netagy","email":"hattiebond@netagy.com","city":"Dante","state":"TN"}
      |    } ]
      |  }
      | }"""
      .stripMargin

  /** This set if tests is peformed in 2 places */
  def testCommonInput(test1Res: SearchResults): Unit = {
    test1Res._shards.total ==> 5
    test1Res._shards.successful ==> 4
    test1Res._shards.failed ==> 1
    test1Res.took ==> 63
    test1Res.timed_out ==> false
    test1Res._scroll_id ==> None
    test1Res.max_score ==> Some(1.0)
    test1Res.total_hits ==> 1000
    test1Res.hits.size ==> 2
    test1Res.hits.head._id ==> "1"
    test1Res.hits.head._index ==> "bank"
    test1Res.hits.head._type ==> "account"
    test1Res.hits.head._score ==> Some(1.0)
    test1Res.hits.head._version ==> None
    test1Res.hits.head._source ==> parse(
      """
        |{"account_number":1,"balance":39225,"firstname":"Amber","lastname":"Duke","age":32,"gender":"M","address":"880 Holmes Lane","employer":"Pyrami","email":"amberduke@pyrami.com","city":"Brogan","state":"IL"}
      """.stripMargin).right.toOption.flatMap(_.asObject)
    test1Res.hits.head.highlight ==> None
    test1Res.hits.head.explain ==> None
    test1Res.hitsJ.size ==> 2
    test1Res.hitsJ.drop(1).head ==> test1Res.hits.drop(1).head.asJson
    test1Res.sourcesJ.size ==> 2
    test1Res.sourcesJ.headOption ==> test1Res.hits.head._source
    test1Res.aggregationsJ ==> None
    test1Res.aggregationJ("not_there") ==> None
  }

  val tests = this {
    "Check deserialization of search requests - misc" - {

      val test1Res = SearchResults(commonTestInput)
      testCommonInput(test1Res)

    }
    "Check deserialization of search requests - called via API" - {

      val handler: PartialFunction[BaseDriverOp, Future[String]] = {
        //TODO: annoyingly, the package has to match ... api.X == ApiModelSearch.X vs api.`X`() == api.`X`()
        case BaseDriverOp(ApiModelSearch.`/_search`(), "GET", _, _, _) =>
          Future.successful(commonTestInput)
      }
      implicit val mockDriver = new MockRestDriver(handler)

      api._search.read().query("test").exec() map { testCommonInput(_) }
    }
    "Check deserialization of search requests - misc unusual settings" - {

      val unusualConfigTestInput =
        """
          |{
          |  "took" : 63,
          |  "timed_out" : true,
          |  "_shards" : {
          |    "total" : 5,
          |    "successful" : 4,
          |    "failed" : 1
          |  },
          |  "_scroll_id": "x",
          |  "hits" : {
          |    "total" : 1000,
          |    "hits" : [ {
          |      "_index" : "bank",
          |      "_version": 100,
          |      "_type" : "account",
          |      "_id" : "1",
          |      "explain" : { "explain": true }, "highlight": { "highlight": true }
          |    }]
          |  }
          | }"""
          .stripMargin

      val test1Res = SearchResults(unusualConfigTestInput)

      test1Res.timed_out ==> true
      test1Res._scroll_id ==> Some("x")
      test1Res.max_score ==> None

      test1Res.hits.head._score ==> None
      test1Res.hits.head._version ==> Some(100)
      test1Res.hits.head._source ==> None
      test1Res.hits.head.highlight ==> parse("""{ "highlight": true }""").right.toOption.flatMap(_.asObject)
      test1Res.hits.head.explain ==> parse("""{ "explain": true }""").right.toOption.flatMap(_.asObject)
    }
    "Check deserialization of search requests - aggregations only" - {

      val aggsOnlyTestInput =
        """
          |{
          |  "took" : 63,
          |  "timed_out" : false,
          |  "_shards" : {
          |    "total" : 5,
          |    "successful" : 4,
          |    "failed" : 1
          |  },
          |  "aggregations" : {
          |    "test1" : {"test1": true},
          |    "test2" : {"test2": true}
          |  }
          | }"""
          .stripMargin

      val test1Res = SearchResults(aggsOnlyTestInput)

      test1Res.hits ==> List.empty
      test1Res.hitsJ ==> List.empty
      test1Res.sourcesJ ==> List.empty

      test1Res.aggregationsJ.map(_.fields) ==> Some(List("test1", "test2"))
      test1Res.aggregationJ("test1") ==> parse("""{"test1": true}""").right.toOption.flatMap(_.asObject)
      test1Res.aggregationJ("not_present") ==> None
    }
  }
}
