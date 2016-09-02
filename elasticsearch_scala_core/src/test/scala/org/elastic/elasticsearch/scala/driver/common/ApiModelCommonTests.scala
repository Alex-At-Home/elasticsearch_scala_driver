package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._

object ApiModelCommonTests extends TestSuite {

  val tests = this {
    "Basic checking for all the standard resources" - {

      object api extends ApiModelCommon

      api.`/`().read().pretty(true).getUrl ==> "/?pretty=true"

      api.`/$index`("a").read().pretty(false).getUrl ==> "/a?pretty=false"
      api.`/$index`("a").write("TEST").pretty(true).getUrl ==> "/a?pretty=true"
      api.`/$index`("a").delete().pretty().getUrl ==> "/a?pretty=true"
      api.`/$index`("a").check().getUrl ==> "/a"

      api.`/$indexes/$types`(Seq("a", "b"), Seq("x", "y")).check().getUrl ==> "/a,b/x,y"

      api.`/$index/$type`("a", "x").write("TEST")
        .pretty(true).parent("P").op_type("O").version(9).routing("R").timeout("T")
        .getUrl == "/a/x?pretty=true&parent=P&op_type=O&version=9&routing=R&timeout=T"
      api.`/$index/$type`("a", "x").check().getUrl == "/a/x"
    }
  }
}
