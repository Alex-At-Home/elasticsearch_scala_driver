package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._

object ApiModelCommonTests extends TestSuite {

  val tests = this {
    "Basic checking for all the standard resources" - {

      object api extends ApiModelCommon

      api.`/`().read().pretty(true).getUrl ==> "/?pretty=true"

      api.`/$uri`("/a").read().m("param=val").getUrl ==> "/a?param=val"
      api.`/$uri`("/a").read("TEST").getUrl ==> "/a"
      api.`/$uri`("/a").check().getUrl ==> "/a"
      api.`/$uri`("/a").write("TEST").getUrl ==> "/a"
      api.`/$uri`("/a").write().getUrl ==> "/a"
      api.`/$uri`("/a").delete().getUrl ==> "/a"
      api.`/$uri`("/a").delete("TEST").getUrl ==> "/a"
      api.`/$uri`("/a").check().getUrl ==> "/a"


      api.`/$index`("a").read().pretty(false).getUrl ==> "/a?pretty=false"
      api.`/$index`("a").write("TEST").pretty(true).getUrl ==> "/a?pretty=true"
      api.`/$index`("a").delete().pretty().getUrl ==> "/a?pretty=true"
      api.`/$index`("a").check().getUrl ==> "/a"

      api.`/$indexes/$types`(Seq("a", "b"), Seq("x", "y")).check().getUrl ==> "/a,b/x,y"

      api.`/$index/$type`("a", "x").write("TEST")
        .pretty(true).parent("P").op_type("O").version(9).routing("R").timeout("T")
        .getUrl == "/a/x?pretty=true&parent=P&op_type=O&version=9&routing=R&timeout=T"
      api.`/$index/$type`("a", "x").check().getUrl == "/a/x"

      api.`/$index/$type/$id`("a", "x", "1").read().pretty(true)
        ._source(true)._source("s1", "s2")._source_include("s3", "s4")._source_exclude("s5", "s6")
          .fields("f1", "f2").getUrl ==>
      "/a/x/1?pretty=true&_source=true&_source=s1,s2&_source_include=s3,s4&_source_exclude=s5,s6&fields=f1,f2"
      api.`/$index/$type/$id`("a", "x", "1").check().getUrl ==> "/a/x/1"
      api.`/$index/$type/$id`("a", "x", "1").write("TEST").pretty()
        .op_type("op").parent("2").routing("node").timeout("1m").version(3).getUrl ==>
        "/a/x/1?pretty=true&op_type=op&parent=2&routing=node&timeout=1m&version=3"
      api.`/$index/$type/$id`("a", "x", "1").delete().pretty(false)
          .op_type("op").parent("2").routing("node").timeout("1m").getUrl ==>
        "/a/x/1?pretty=false&op_type=op&parent=2&routing=node&timeout=1m"

      api.`/$index/$type/$id/_source`("a", "x", "1").read().pretty(true)
        ._source(true)._source("s1", "s2")._source_include("s3", "s4")._source_exclude("s5", "s6")
        .fields("f1", "f2").getUrl ==>
        "/a/x/1/_source?pretty=true&_source=true&_source=s1,s2&_source_include=s3,s4&_source_exclude=s5,s6&fields=f1,f2"

      api.`/$index/$type/$id/_update`("a", "x", "1").write("TEST").pretty(true).getUrl ==>
        "/a/x/1/_update?pretty=true"

      api.`/$index/_update_by_query`("a").write("TEST").pretty(false)
          .conflict("proceed").getUrl ==> "/a/_update_by_query?pretty=false&conflict=proceed"

      api.`/_mget`().read().pretty().routing("a").getUrl ==> "/_mget?pretty=true&routing=a"
      api.`/$index/_mget`("a").read().pretty(true).routing("a").getUrl ==> "/a/_mget?pretty=true&routing=a"
      api.`/$index/$type/_mget`("a", "x").read().pretty(false).routing("a").getUrl ==>
        "/a/x/_mget?pretty=false&routing=a"

      api.`/_bulk`().write("TEST").pretty().getUrl ==> "/_bulk?pretty=true"
      api.`/$index/_bulk`("a").write("TEST").pretty(true).getUrl ==> "/a/_bulk?pretty=true"
      api.`/$index/$type/_bulk`("a", "x").write("TEST").pretty(false).getUrl ==> "/a/x/_bulk?pretty=false"

      api.`/_reindex`().write("TEST").pretty().getUrl ==> "/_reindex?pretty=true"

      api.`/$index/$type/_termvectors`("a", "x").read().pretty().fields("f1", "f2").getUrl ==>
        "/a/x/_termvectors?pretty=true&fields=f1,f2"
      api.`/$index/$type/$id/_termvectors`("a", "x", "1").read().pretty(false).fields("f1", "f2").getUrl ==>
        "/a/x/1/_termvectors?pretty=false&fields=f1,f2"

      api.`/_mtermvectors`().read("TEST").pretty().getUrl ==> "/_mtermvectors?pretty=true"
      api.`/$index/_mtermvectors`("a").read("TEST").pretty(true).getUrl ==> "/a/_mtermvectors?pretty=true"
    }
  }
}
