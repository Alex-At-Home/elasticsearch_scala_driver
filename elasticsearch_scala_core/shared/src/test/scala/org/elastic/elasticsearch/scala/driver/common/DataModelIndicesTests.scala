package org.elastic.elasticsearch.scala.driver.common

import utest._
import io.circe.parser.parse

object DataModelIndicesTests extends TestSuite {

  val tests = this {
    import DataModelIndices._
    import DataModelSearch._

    "Alias commands" - {

      parse(
        AliasControl(
          actions = List(
            AddAliasControl(
              alias = "test_alias1a",
              index = Some("test_index1a"),
              filter = Some(qb.MatchAllQuery()),
              routing = Some("routing_test")
            ),
            RemoveAliasControl(
              alias = "test_alias1b",
              index = Some("test_index1b")
            ),
            RemoveIndexControl(
              index = Some("test_index1c")
            ),
            AddAliasControl(
              alias = "test_alias2a",
              indices = Seq("test_index2a", "test_index3a"),
              search_routing = Some("search_routing_test"),
              index_routing = Some("index_routing_test")
            ),
            RemoveAliasControl(
              alias = "test_alias2b",
              indices = Seq("test_index2b", "test_index3b")
            ),
            RemoveIndexControl(
              indices = Seq("test_index2c", "test_index3c")
            )
          )
        ).fromTyped
      ) ==>
      parse(
        """
          |{
          |   "actions": [
          |     { "add": {
          |         "alias": "test_alias1a",
          |         "index": "test_index1a",
          |         "filter": { "match_all": {} },
          |         "routing": "routing_test"
          |       }
          |     },
          |     { "remove": {
          |         "alias": "test_alias1b",
          |         "index": "test_index1b"
          |       }
          |     },
          |     { "remove_index": {
          |         "index": "test_index1c"
          |       }
          |     },
          |     { "add": {
          |         "alias": "test_alias2a",
          |         "indices": [ "test_index2a", "test_index3a" ],
          |         "search_routing": "search_routing_test",
          |         "index_routing": "index_routing_test"
          |       }
          |     },
          |     { "remove": {
          |         "alias": "test_alias2b",
          |         "indices": [ "test_index2b", "test_index3b" ]
          |       }
          |     },
          |     { "remove_index": {
          |         "indices": [ "test_index2c", "test_index3c" ]
          |       }
          |     }
          |  ]
          |}
        """.stripMargin
      )
    }
  }
}
