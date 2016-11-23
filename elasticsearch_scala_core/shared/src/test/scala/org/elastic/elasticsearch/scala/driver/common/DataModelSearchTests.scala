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
      "Terms query lookup term" - {
        parse(
          qb.TermsLookupQuery(terms = Map("user" ->
            qb.TermLookupQuery(index = "users", `type` = "user", id = "2", path = "followers"))
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "terms" : {
            |      "user" : {
            |        "index" : "users",
            |        "type" : "user",
            |        "id" : "2",
            |        "path" : "followers"
            |      }
            |    }
            |  }
          """.stripMargin
        )

        parse(
          qb.TermsLookupQuery(terms = Map("user" ->
            qb.TermLookupQuery(index = "users", `type` = "user", id = "2", path = "followers", routing = Some("route"))),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "terms" : {
            |      "user" : {
            |        "index" : "users",
            |        "type" : "user",
            |        "id" : "2",
            |        "path" : "followers",
            |        "routing": "route"
            |      },
            |      "_name": "test_name"
            |    }
            |  }
          """.stripMargin
        )
      }
      "Range query term" - {

        parse(
          qb.RangeQuery(field = "age", gte = Some(10), lte = Some(20), boost = Some(0.0)).fromTyped
        ) ==> parse(
          """
            |{
            |    "range" : {
            |        "age" : {
            |            "gte" : 10,
            |            "lte" : 20,
            |            "boost" : 0.0
            |        }
            |    }
            |}
          """.stripMargin
        )

        parse(
          qb.RangeQuery(
            field = "timestamp",
            gt = Some("2015-01-01 00:00:00"), lt = Some("now"),
            format = Some("dd/MM/yyyy||yyyy"), time_zone = Some("+01:00"),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "range" : {
            |        "timestamp" : {
            |            "gt": "2015-01-01 00:00:00",
            |            "lt": "now",
            |            "format": "dd/MM/yyyy||yyyy",
            |            "time_zone": "+01:00"
            |        },
            |        "_name": "test_name"
            |    }
            |}
          """.stripMargin
        )
      }
      "Prefix query term" - {

        parse(
          qb.PrefixQuery(
            field = "user",
            value = "ki"
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "prefix" : { "user" : "ki" }
            |}
          """.stripMargin
        )

        parse(
          qb.PrefixQuery(
            field = "user",
            value = "ki",
            boost = Some(0.0),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "prefix" : { "user" :  { "value" : "ki", "boost" : 0.0 }, "_name": "test_name" }
            |}
          """.stripMargin
        )
      }
      "Ids query term" - {

        parse(
          qb.IdsQuery(
            values = List("1", "4", "100"),
            `type` = List("my_type")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "ids" : {
            |        "type" : "my_type",
            |        "values" : ["1", "4", "100"]
            |    }
            |}
          """.stripMargin
        )

        parse(
          qb.IdsQuery(
            values = List("1", "4", "100"),
            `type` = List("my_type1", "my_type2")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "ids" : {
            |        "type" : [ "my_type1", "my_type2" ],
            |        "values" : ["1", "4", "100"]
            |    }
            |}
          """.stripMargin
        )

        parse(
          qb.IdsQuery(
            values = List("1", "4", "100"),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "ids" : {
            |        "values" : ["1", "4", "100"],
            |         "_name": "test_name"
            |    }
            |}
          """.stripMargin
        )
      }
      "Bool query term" - {

        parse(
          qb.BoolQuery(
            must = Seq(qb.TermQuery(field = "user", value = "kimchy")),
            filter = Seq(qb.TermQuery(field = "tag", value = "tech")),
            must_not = Seq(qb.RangeQuery(field = "age", gte = Some(10), lte = Some(20))),
            should = Seq(
              qb.TermQuery(field = "tag", value = "wow"), qb.TermQuery(field = "tag", value = "elasticsearch")
            ),
            minimum_should_match = Some(1),
            boost = Some(0.0)
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "bool" : {
            |        "must" : {
            |            "term" : { "user" : "kimchy" }
            |        },
            |        "must_not" : {
            |            "range" : {
            |                "age" : { "gte" : 10, "lte" : 20 }
            |            }
            |        },
            |        "should" : [
            |            {
            |                "term" : { "tag" : "wow" }
            |            },
            |            {
            |                "term" : { "tag" : "elasticsearch" }
            |            }
            |        ],
            |        "filter": {
            |            "term" : { "tag" : "tech" }
            |        },
            |        "minimum_should_match" : 1,
            |        "boost" : 0.0
            |    }
            |}
          """.stripMargin
        )

        parse(
          qb.BoolQuery(
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{ "bool" : {
            |   "_name": "test_name"
            |  }
            |}
          """.stripMargin
        )
      }
      "Constant score query term" - {

        parse(
          qb.ConstantScoreQuery(
            filter = qb.TermQuery("user", "kimchy"),
            boost = Some(0.0)
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "constant_score" : {
            |        "filter" : {
            |            "term" : { "user" : "kimchy"}
            |        },
            |        "boost" : 0.0
            |    }
            |}
          """.stripMargin
        )

        parse(
          qb.ConstantScoreQuery(
            filter = qb.TermQuery("user", "kimchy"),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "constant_score" : {
            |        "filter" : {
            |            "term" : { "user" : "kimchy"}
            |        },
            |        "_name" : "test_name"
            |    }
            |}
          """.stripMargin
        )
      }
      "Has Child query term" - {

        parse(
          qb.HasChildQuery(
            query = qb.TermQuery("tag", "something"),
            `type` = Some("blog_tag")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "has_child" : {
            |        "type" : "blog_tag",
            |        "query" : {
            |            "term" : {
            |                "tag" : "something"
            |            }
            |        }
            |    }
            |}
          """.stripMargin)

        //TODO various combos
      }
      "Has Parent query term" - {
        //TODO
      }
      "Raw query term" - {
        qb.RawQuery("test").fromTyped ==> "test"
      }
    }
    "Aggregation builders" - {
      "Raw aggregation builder" - {
        ab.RawAggregation("test").fromTyped ==> "test"
      }
    }
  }
}
