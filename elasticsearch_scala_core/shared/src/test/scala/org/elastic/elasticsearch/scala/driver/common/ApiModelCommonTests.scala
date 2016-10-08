package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.elasticsearch.scala.driver.common.DataModelCommon.BulkIndexOps
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.utils.NoJsonHelpers



object ApiModelCommonTests extends TestSuite {

  val tests = this {
    "Basic checking for all the standard resources" - {

      object api extends ApiModelCommon

      api.`/`().read().pretty(true).getUrl ==> "/?pretty=true"

      api.`/$uri`("/a").read().m("param", "val").getUrl ==> "/a?param=val"
      api.`/$uri`("/a").readS("TEST").getUrl ==> "/a"
      api.`/$uri`("/a").check().getUrl ==> "/a"
      api.`/$uri`("/a").writeS("TEST").getUrl ==> "/a"
      api.`/$uri`("/a").write().getUrl ==> "/a"
      api.`/$uri`("/a").delete().getUrl ==> "/a"
      api.`/$uri`("/a").deleteS("TEST").getUrl ==> "/a"
      api.`/$uri`("/a").check().getUrl ==> "/a"

      api.`/$index`("a").read().pretty(false).getUrl ==> "/a?pretty=false"
      api.`/$index`("a").writeS("TEST").pretty(true).getUrl ==> "/a?pretty=true"
      api.`/$index`("a").delete().pretty(true).getUrl ==> "/a?pretty=true"
      api.`/$index`("a").check().getUrl ==> "/a"
      api.`/`().$("a").read().getUrl ==> "/a"
      api.`/`().$("a").writeS("TEST").getUrl ==> "/a"
      api.`/`().$("a").delete().getUrl ==> "/a"
      api.`/`().$("a").check().getUrl ==> "/a"

      api.`/$indexes/$types`(Seq("a", "b"), Seq("x", "y")).check().getUrl ==> "/a,b/x,y"
      api.`/`().$("a", "b").$("x", "y").check().getUrl ==> "/a,b/x,y"

      api.`/$index/$type`("a", "x").writeS("TEST")
        .pretty(true).parent("P").op_type("O").version(9).routing("R").timeout("T")
        .getUrl == "/a/x?pretty=true&parent=P&op_type=O&version=9&routing=R&timeout=T"
      api.`/$index/$type`("a", "x").check().getUrl == "/a/x"
      api.`/`().$("a").$("x").writeS("TEST").getUrl ==> "/a/x"
      api.`/`().$("a").$("x").check().getUrl ==> "/a/x"

      api.`/$index/$type/$id`("a", "x", "1").read().pretty(true)
        ._source(true)._source("s1", "s2")._source_include("s3", "s4")._source_exclude("s5", "s6")
          .fields("f1", "f2").getUrl ==>
      "/a/x/1?pretty=true&_source=true&_source=s1,s2&_source_include=s3,s4&_source_exclude=s5,s6&fields=f1,f2"
      api.`/$index/$type/$id`("a", "x", "1").check().getUrl ==> "/a/x/1"
      api.`/$index/$type/$id`("a", "x", "1").writeS("TEST").pretty(true)
        .op_type("op").parent("2").routing("node").timeout("1m").version(3).getUrl ==>
        "/a/x/1?pretty=true&op_type=op&parent=2&routing=node&timeout=1m&version=3"
      api.`/$index/$type/$id`("a", "x", "1").delete().pretty(false)
          .op_type("op").parent("2").routing("node").timeout("1m").getUrl ==>
        "/a/x/1?pretty=false&op_type=op&parent=2&routing=node&timeout=1m"
      api.`/`().$("a").$("x").$("1").read().getUrl ==> "/a/x/1"
      api.`/`().$("a").$("x").$("1").check().getUrl ==> "/a/x/1"
      api.`/`().$("a").$("x").$("1").writeS("TEST").getUrl ==> "/a/x/1"
      api.`/`().$("a").$("x").$("1").delete().getUrl ==> "/a/x/1"

      api.`/$index/$type/$id/_source`("a", "x", "1").read().pretty(true)
        ._source(true)._source("s1", "s2")._source_include("s3", "s4")._source_exclude("s5", "s6")
        .fields("f1", "f2").getUrl ==>
        "/a/x/1/_source?pretty=true&_source=true&_source=s1,s2&_source_include=s3,s4&_source_exclude=s5,s6&fields=f1,f2"
      api.`/`().$("a").$("x").$("1")._source.read().getUrl ==> "/a/x/1/_source"

      api.`/$index/$type/$id/_update`("a", "x", "1").writeS("TEST").pretty(true).getUrl ==>
        "/a/x/1/_update?pretty=true"
      api.`/`().$("a").$("x").$("1")._update.writeS("TEST").getUrl ==> "/a/x/1/_update"

      api.`/$index/_update_by_query`("a").writeS("TEST").pretty(false)
          .conflict("proceed").getUrl ==> "/a/_update_by_query?pretty=false&conflict=proceed"
      api.`/`().$("a")._update_by_query.writeS("TEST").getUrl ==> "/a/_update_by_query"

      api.`/_mget`().read().pretty(true).routing("a").getUrl ==> "/_mget?pretty=true&routing=a"
      api.`/$index/_mget`("a").read().pretty(true).routing("a").getUrl ==> "/a/_mget?pretty=true&routing=a"
      api.`/$index/$type/_mget`("a", "x").read().pretty(false).routing("a").getUrl ==>
        "/a/x/_mget?pretty=false&routing=a"
      api.`/`()._mget.read().getUrl ==> "/_mget"
      api.`/`().$("a")._mget.read().getUrl ==> "/a/_mget"
      api.`/`().$("a").$("x")._mget.read().getUrl ==> "/a/x/_mget"

      api.`/_bulk`().writeS("TEST").pretty(true).getUrl ==> "/_bulk?pretty=true"
      api.`/$index/_bulk`("a").writeS("TEST").pretty(true).getUrl ==> "/a/_bulk?pretty=true"
      api.`/$index/$type/_bulk`("a", "x").writeS("TEST").pretty(false).getUrl ==> "/a/x/_bulk?pretty=false"
      api.`/`()._bulk.writeS("TEST").getUrl ==> "/_bulk"
      api.`/`().$("a")._bulk.writeS("TEST").getUrl ==> "/a/_bulk"
      api.`/`().$("a").$("x")._bulk.writeS("TEST").getUrl ==> "/a/x/_bulk"

      api.`/_reindex`().writeS("TEST").pretty(true).getUrl ==> "/_reindex?pretty=true"
      api.`/`()._reindex.writeS("TEST").getUrl ==> "/_reindex"

      api.`/$index/$type/_termvectors`("a", "x").read().pretty(true).fields("f1", "f2").getUrl ==>
        "/a/x/_termvectors?pretty=true&fields=f1,f2"
      api.`/$index/$type/$id/_termvectors`("a", "x", "1").read().pretty(false).fields("f1", "f2").getUrl ==>
        "/a/x/1/_termvectors?pretty=false&fields=f1,f2"
      api.`/`().$("a").$("x")._termvectors.read().getUrl ==> "/a/x/_termvectors"
      api.`/`().$("a").$("x").$("1")._termvectors.read().getUrl ==> "/a/x/1/_termvectors"

      api.`/_mtermvectors`().readS("TEST").pretty(true).getUrl ==> "/_mtermvectors?pretty=true"
      api.`/$index/_mtermvectors`("a").readS("TEST").pretty(true).getUrl ==> "/a/_mtermvectors?pretty=true"
      api.`/`()._mtermvectors.readS("TEST").getUrl ==> "/_mtermvectors"
      api.`/`().$("a")._mtermvectors.readS("TEST").getUrl ==> "/a/_mtermvectors"
    }
    "Custom typed operations: _bulk" - {
      object api extends ApiModelCommon

      import NoJsonHelpers._

      val bulkOps = BulkIndexOps(List(
        api.`/$index/$type`("index", "type").writeS("TEST1").m("param", "tp"),
        api.`/$index/$type/$id`("index", "type", "id").writeS("TEST2").m("param", true),
        api.`/$index/$type/$id`("", "type", "id").writeS("TEST3"),
        api.`/$index/$type/$id`("index", "", "id").delete(),
        api.`/$index/$type/$id`("", "", "id").writeS("TEST5"),
        api.`/$uri`("index").writeS("TEST6").m("param", 3),
        api.`/$index/$type/$id/_update`("index", "type", "id").writeS("TEST7").m("param", List("l1", "l2"))
      ))

      def formatVals(s: String) =
        s.replace(" ", "").replace("\t", "").replace("\r", "")

      val expected = formatVals(
        s"""{ "index": {  "_index": "index" ,  "_type": "type", "param": "tp"  } }
            |TEST1
            |{ "index": {  "_index": "index" ,  "_type": "type", "_id": "id", "param": true  } }
            |TEST2
            |{ "index": {  "_type": "type", "_id": "id"  } }
            |TEST3
            |{ "delete": {  "_index": "index", "_id": "id"  } }
            |{ "index": {  "_id": "id"  } }
            |TEST5
            |{ "index": {  "_index": "index", "param": 3  } }
            |TEST6
            |{ "update": {  "_index": "index", "_type": "type", "_id": "id", "param": ["l1", "l2"]  } }
            |TEST7
            |"""
          .stripMargin.stripSuffix("\n"))

      // (handy debug statement for if this fails)
      //val xxx = formatVals(api.`/_bulk`().write(bulkOps).body.get)
      //println("???????? " + xxx.getBytes.toList.zip(expected.getBytes.toList)
      //  .map(x => if (x._1 != x._2) s"****$x****" else s"$x").mkString("|"))

      formatVals(api.`/_bulk`().write(bulkOps).body.get) ==> expected
    }
  }
}
