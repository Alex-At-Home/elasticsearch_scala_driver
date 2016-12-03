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

        parse(
          qb.HasChildQuery(
            query = qb.TermQuery("tag", "something"),
            score_mode = Some("min"),
            min_children = Some(1), max_children = Some(2),
            inner_hits = Some(QueryBody(_source = Right(Seq("message")))),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "has_child" : {
            |        "query" : {
            |            "term" : {
            |                "tag" : "something"
            |            }
            |        },
            |        "score_mode": "min",
            |        "min_children": 1,
            |        "max_children": 2,
            |        "inner_hits": {
            |           "size": 10,
            |           "from": 0,
            |           "track_scores": false,
            |           "_source": [ "message" ],
            |           "explain": false,
            |           "version": false
            |        },
            |        "_name": "test_name"
            |    }
            |}
          """.stripMargin)
      }
      "Has Parent query term" - {
        parse(
          qb.HasParentQuery(
            query = qb.TermQuery("tag", "something"),
            parent_type = Some("blog_tag")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "has_parent" : {
            |        "parent_type" : "blog_tag",
            |        "query" : {
            |            "term" : {
            |                "tag" : "something"
            |            }
            |        }
            |    }
            |}
          """.stripMargin)

        parse(
          qb.HasParentQuery(
            query = qb.TermQuery("tag", "something"),
            score_mode = Some("min"),
            inner_hits = Some(QueryBody(_source = Right(Seq("message")))),
            _name = Some("test_name")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |    "has_parent" : {
            |        "query" : {
            |            "term" : {
            |                "tag" : "something"
            |            }
            |        },
            |        "score_mode": "min",
            |        "inner_hits": {
            |           "size": 10,
            |           "from": 0,
            |           "track_scores": false,
            |           "_source": [ "message" ],
            |           "explain": false,
            |           "version": false
            |        },
            |        "_name": "test_name"
            |    }
            |}
          """.stripMargin)
      }
      "Raw query term" - {
        qb.RawQuery("test").fromTyped ==> "test"
      }
    }
    "Query config builds" - {
      "RescoreConfig" - {

        parse(
          QueryBody.RescoreConfig(
            rescore_query = qb.MatchAllQuery(),
            rescore_query_weight = Some(0.0)
          ).fromTyped
        ) ==> parse(
          """
            |{ "query": {
            |   "rescore_query": {
            |      "match_all": {}
            |   },
            |   "rescore_query_weight": 0.0
            | }
            |}
          """.stripMargin
        )

        parse(
          QueryBody.RescoreConfig(
            rescore_query = qb.MatchAllQuery(),
            window_size = Some(5),
            score_mode = Some("min"),
            query_weight = Some(0.0)
          ).fromTyped
        ) ==> parse(
          """
            |{
            | "window_size": 5,
            | "query": {
            |  "rescore_query": {
            |     "match_all": {}
            |   },
            |   "score_mode": "min",
            |   "query_weight": 0.0
            | }
            |}
          """.stripMargin
        )
      }
      "HighlightConfig" - {

        parse(
          QueryBody.HighlightConfig(
            fields = Map(
              "content" -> QueryBody.FieldHighlightConfig(
                `type` = Some("fvh"),
                force_source = true,
                matched_fields = Seq("content", "content.plain")
              )
            ),
            order = Some("score")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |        "order": "score",
            |        "require_field_match" : true,
            |        "fields": {
            |            "content": {
            |               "force_source": true,
            |                "matched_fields": ["content", "content.plain"],
            |                "type" : "fvh"
            |            }
            |        },
            |        "force_source": false
            |}
          """.stripMargin
        )

        parse(
          QueryBody.HighlightConfig(
            fields = Map(
              "content" -> QueryBody.FieldHighlightConfig(
                number_of_fragments = Some(20),
                fragment_size = Some(200),
                no_match_size = Some(2),
                highlight_query = Some(qb.TermQuery("k", "v")),
                pre_tags = Seq("aa", "bb"),
                post_tags = Seq("xx", "yy")
              )
            ),
            `type` = Some("max"),
            require_field_match = false,
            force_source = true,
            number_of_fragments = Some(10),
            fragment_size = Some(100),
            no_match_size = Some(1),
            highlight_query = Some(qb.MatchAllQuery()),
            pre_tags = Seq("a", "b"),
            post_tags = Seq("x", "y"),
            matched_fields = Seq("1", "2")
          ).fromTyped
        ) ==> parse(
          """
            |{
            |        "require_field_match" : false,
            |        "fields": {
            |            "content": {
            |               "force_source": false,
            |               "number_of_fragments": 20,
            |               "fragment_size": 200,
            |               "no_match_size": 2,
            |               "highlight_query": { "term": { "k": "v" } },
            |                "pre_tags": ["aa", "bb"],
            |                "post_tags": ["xx", "yy"]
            |            }
            |        },
            |        "type": "max",
            |        "force_source": true,
            |        "number_of_fragments": 10,
            |        "fragment_size": 100,
            |        "no_match_size": 1,
            |        "highlight_query": { "match_all": {} },
            |        "pre_tags": [ "a", "b" ],
            |        "post_tags": [ "x", "y" ],
            |        "matched_fields": [ "1", "2" ]
            |}
          """.stripMargin
        )
      }
      "ScriptFieldConfig" - {

        parse(
          QueryBody.ScriptFieldConfig(
            inline = Some("doc['my_field_name'].value * factor"),
            params = Map("factor" -> 0.0)
          ).fromTyped
        ) ==> parse(
          """
            |{
            | "script": {
            |                "inline": "doc['my_field_name'].value * factor",
            |                "params" : {
            |                    "factor"  : 0.0
            |                }
            | }
            |}
          """.stripMargin
        )

        parse(
          QueryBody.ScriptFieldConfig(
            file = Some("test"),
            lang = Some(ScriptLang.groovy)
          ).fromTyped
        ) ==> parse(
          """
            |{
            | "script": {
            |   "file": "test",
            |   "lang": "groovy"
            | }
            |}
          """.stripMargin
        )
      }
      "Sorting" - {
        "SimpleSortConfig" - {
          QueryBody.SimpleSortConfig("test").fromTyped ==> "\"test\""
        }
        "FullSortConfig" - {
          //TODO
        }
        "ScriptSortConfig" - {
          //TODO
        }
        "RawSortConfig" - {
          QueryBody.RawSortConfig("test").fromTyped ==> "test"
        }
      }
    }
    "Value classes" - {
      "ScriptLang" - {
        ScriptLang.groovy.toString ==> "groovy"
        ScriptLang.expression.toString ==> "expression"
        ScriptLang.moustache.toString ==> "moustache"
      }
      "SortOrder" - {
        SortOrder.asc.toString ==> "asc"
        SortOrder.desc.toString ==> "desc"
      }
      //TODO
    }
    "Full query builder" - {
      //TODO
    }
    "Aggregation builders" - {
      "Raw aggregation builder" - {
        ab.RawAggregation("test").fromTyped ==> "test"
      }
    }
  }
}
