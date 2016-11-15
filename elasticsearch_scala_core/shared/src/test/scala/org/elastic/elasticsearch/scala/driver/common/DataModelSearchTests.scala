package org.elastic.elasticsearch.scala.driver.common

import utest._
import io.circe.parser.parse

object DataModelSearchTests extends TestSuite {
  import DataModelSearch._

  val tests = this {
    "Query builders" - {
      "Match all query term" - {

        parse(qb.MatchAllQuery().fromTyped) ==> parse(
          """{ "match_all": {} }"""
        )

        parse(qb.MatchAllQuery(
          boost = Some(0.0), _name = Some("test_name")).fromTyped
        ) ==> parse(
          """{ "match_all": { "boost": 0.0, "_name": "test_name" } }"""
        )
      }
      "Term query term" - {

        parse(qb.TermQuery(field = "status", value = "urgent").fromTyped) ==> parse(
          """{ "term": { "status": "urgent" } }"""
        )

        parse(qb.TermQuery(
          field = "status", value = "urgent", boost = Some(0.0), _name = Some("test_name")).fromTyped
        ) ==> parse(
          """
            |        {
            |          "term": {
            |            "status": {
            |              "value": "urgent",
            |              "boost": 0.0
            |            },
            |            "_name": "test_name"
            |          }
            |        }
            """.stripMargin
        )
      }

      "Terms query term" - {

        parse(
          qb.TermsQuery(terms = Map("t1" -> Seq("v1.1", "v1.2"), "t2" -> Seq("v2.1"))).fromTyped
        ) ==> parse(
          """
            { "terms": {
                "t1": [ "v1.1", "v1.2" ],
                "t2": [ "v2.1" ]
              }
            }
          """.stripMargin)

        parse(qb.TermsQuery(
          terms = Map("t1" -> Seq("v1.1", "v1.2"), "t2" -> Seq("v2.1")), _name = Some("test_name")).fromTyped
        ) ==> parse(
          """
              { "terms": {
                  "t1": [ "v1.1", "v1.2" ],
                  "t2": [ "v2.1" ],
                  "_name": "test_name"
                }
              }
          """.stripMargin)
      }
    }
    "Aggregation builders" - {
      "Raw aggregation builder" - {
        ab.RawAggregation("test").fromTyped ==> "test"
      }
    }
  }
}
