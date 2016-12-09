package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.rest.scala.driver.RestBase._

object ApiModelIndicesTests extends TestSuite {

  val tests = this {
    "Enums" - {
      "ShardStatus" - {
        ShardStatus("test").toString ==> "test"
        ShardStatus.red ==> ShardStatus("red")
        ShardStatus.green ==> ShardStatus("green")
        ShardStatus.yellow ==> ShardStatus("yellow")
      }
    }
    "Basic checking for all the indices resources" - {

      object api extends ApiModelCommon
      object indices extends ApiModelIndices

      // 3.1

      indices.`/$indexes/_open`("a", "b").send()
        .human(true).`case`(true).filter_path("f1", "f2")
        .flat_settings(false).ignore_unavailable(false)
        .getUrl ==> "/a,b/_open?human=true&case=true&filter_path=f1,f2&flat_settings=false&ignore_unavailable=false"
      api.`/`().$("a", "b")._open.send().getUrl ==> "/a,b/_open"
      api.`/`().$("a")._open.send().getUrl ==> "/a/_open"

      indices.`/_all/_open`().send()
        .human(true).`case`(true).filter_path("f1", "f2")
        .flat_settings(false)
        .getUrl ==> "/_all/_open?human=true&case=true&filter_path=f1,f2&flat_settings=false"
      api.`/`()._all._open.send().getUrl ==> "/_all/_open"

      indices.`/$indexes/_close`("a", "b").send()
        .human(true).`case`(true).filter_path("f1", "f2")
        .flat_settings(false).ignore_unavailable(false)
        .getUrl ==> "/a,b/_close?human=true&case=true&filter_path=f1,f2&flat_settings=false&ignore_unavailable=false"
      api.`/`().$("a", "b")._close.send().getUrl ==> "/a,b/_close"
      api.`/`().$("a")._close.send().getUrl ==> "/a/_close"

      indices.`/_all/_close`().send()
        .human(true).`case`(true).filter_path("f1", "f2")
        .flat_settings(false)
        .getUrl ==> "/_all/_close?human=true&case=true&filter_path=f1,f2&flat_settings=false"
      api.`/`()._all._close.send().getUrl ==> "/_all/_close"

      // 3.2

      indices.`/_mapping`().read()
        .pretty(true).human(false).`case`(false).flat_settings(true)
        .getUrl ==> "/_mapping?pretty=true&human=false&case=false&flat_settings=true"
      indices.`/_mapping/$types`("x", "y").read()
        .pretty(true).human(false).`case`(false).flat_settings(true)
        .getUrl ==> "/_mapping/x,y?pretty=true&human=false&case=false&flat_settings=true"
      api.`/`()._mapping.read().getUrl ==> "/_mapping"
      api.`/`()._mapping.$("x", "y").read().getUrl ==> "/_mapping/x,y"

      indices.`/_all/_mapping`().read()
        .pretty(true).human(false).`case`(false).flat_settings(true)
        .getUrl ==> "/_all/_mapping?pretty=true&human=false&case=false&flat_settings=true"
      indices.`/_all/_mapping/$types`("x", "y").read()
        .pretty(true).human(false).`case`(false).flat_settings(true)
        .getUrl ==> "/_all/_mapping/x,y?pretty=true&human=false&case=false&flat_settings=true"
      api.`/`()._all._mapping.read().getUrl ==> "/_all/_mapping"
      api.`/`()._all._mapping.$("x", "y").read().getUrl ==> "/_all/_mapping/x,y"

      indices.`/$indexes/_mapping`("a", "b").read()
        .pretty(true).human(false).`case`(false).flat_settings(true)
        .getUrl ==> "/a,b/_mapping?pretty=true&human=false&case=false&flat_settings=true"
      indices.`/$indexes/_mapping/$types`(Seq("a", "b"), Seq("x", "y")).read()
        .pretty(true).human(false).`case`(false).flat_settings(true)
        .getUrl ==> "/a,b/_mapping/x,y?pretty=true&human=false&case=false&flat_settings=true"
      api.`/`().$("a")._mapping.read().getUrl ==> "/a/_mapping"
      api.`/`().$("a", "b")._mapping.read().getUrl ==> "/a,b/_mapping"
      api.`/`().$("a")._mapping.$("x", "y").read().getUrl ==> "/a/_mapping/x,y"
      api.`/`().$("a", "b")._mapping.$("x", "y").read().getUrl ==> "/a,b/_mapping/x,y"

      // 3.3

      indices.`/$indexes/_mapping/field/$fields`(Seq("a", "b"), Seq("f", "g")).read()
        .pretty(true).human(false).`case`(false).filter_path("f1", "f2")
        .getUrl ==> "/a,b/_mapping/field/f,g?pretty=true&human=false&case=false&filter_path=f1,f2"
      indices.`/$indexes/_mapping/$types/field/$fields`(Seq("a", "b"), Seq("x", "y"), Seq("f", "g")).read()
        .pretty(true).human(false).`case`(false).filter_path("f1", "f2")
        .getUrl ==> "/a,b/_mapping/x,y/field/f,g?pretty=true&human=false&case=false&filter_path=f1,f2"
      api.`/`().$("a", "b")._mapping.field.$("f", "g").read()
        .getUrl ==> "/a,b/_mapping/field/f,g"
      api.`/`().$("a")._mapping.field.$("f", "g").read()
        .getUrl ==> "/a/_mapping/field/f,g"
      api.`/`().$("a", "b")._mapping.$("x", "y").field.$("f", "g").read()
        .getUrl ==> "/a,b/_mapping/x,y/field/f,g"
      api.`/`().$("a")._mapping.$("x", "y").field.$("f", "g").read()
        .getUrl ==> "/a/_mapping/x,y/field/f,g"

      indices.`/_mapping/field/$fields`("f", "g").read()
        .pretty(true).human(false).`case`(false).filter_path("f1", "f2")
        .getUrl ==> "/_mapping/field/f,g?pretty=true&human=false&case=false&filter_path=f1,f2"
      indices.`/_mapping/$types/field/$fields`(Seq("x", "y"), Seq("f", "g")).read()
        .pretty(true).human(false).`case`(false).filter_path("f1", "f2")
        .getUrl ==> "/_mapping/x,y/field/f,g?pretty=true&human=false&case=false&filter_path=f1,f2"
      api.`/`()._mapping.field.$("f", "g").read()
        .getUrl ==> "/_mapping/field/f,g"
      api.`/`()._mapping.$("x", "y").field.$("f", "g").read()
        .getUrl ==> "/_mapping/x,y/field/f,g"

      indices.`/_all/_mapping/field/$fields`("f", "g").read()
        .pretty(true).human(false).`case`(false).filter_path("f1", "f2")
        .getUrl ==> "/_all/_mapping/field/f,g?pretty=true&human=false&case=false&filter_path=f1,f2"
      indices.`/_all/_mapping/$types/field/$fields`(Seq("x", "y"), Seq("f", "g")).read()
        .pretty(true).human(false).`case`(false).filter_path("f1", "f2")
        .getUrl ==> "/_all/_mapping/x,y/field/f,g?pretty=true&human=false&case=false&filter_path=f1,f2"
      api.`/`()._all._mapping.field.$("f", "g").read()
        .getUrl ==> "/_all/_mapping/field/f,g"
      api.`/`()._all._mapping.$("x", "y").field.$("f", "g").read()
        .getUrl ==> "/_all/_mapping/x,y/field/f,g"

      // 3.4

      indices.`/_aliases`().writeS("TEST").human(false).getUrl ==> "/_aliases?human=false"
      api.`/`()._aliases.writeS("TEST").getUrl ==> "/_aliases"

      indices.`/_alias/$aliases`("a1", "a2").read().pretty(true).getUrl ==> "/_alias/a1,a2?pretty=true"
      indices.`/_alias/$aliases`("a1", "a2").check().pretty(false).getUrl ==> "/_alias/a1,a2?pretty=false"
      api.`/`()._alias.$("a1", "a2").read().getUrl ==> "/_alias/a1,a2"
      api.`/`()._alias.$("a1", "a2").check().getUrl ==> "/_alias/a1,a2"

      indices.`/_alias/*`().read().pretty(true).getUrl ==> "/_alias/*?pretty=true"
      indices.`/_alias/*`().check().pretty(false).getUrl ==> "/_alias/*?pretty=false"
      api.`/`()._alias.*.read().getUrl ==> "/_alias/*"
      api.`/`()._alias.*.check().getUrl ==> "/_alias/*"

      indices.`/_alias/$aliases`("a1", "a2").read().`case`(true).getUrl ==> "/_alias/a1,a2?case=true"
      indices.`/_alias/$aliases`("a1", "a2").check().`case`(false).getUrl ==> "/_alias/a1,a2?case=false"
      api.`/`()._alias.$("a1", "a2").read().getUrl ==> "/_alias/a1,a2"
      api.`/`()._alias.$("a1", "a2").check().getUrl ==> "/_alias/a1,a2"

      indices.`/$indexes/_alias/$alias`(Seq("a", "b"), "a1").read()
        .`case`(true).getUrl ==> "/a,b/_alias/a1?case=true"
      indices.`/$indexes/_alias/$alias`(Seq("a", "b"), "a1").check()
        .`case`(false).getUrl ==> "/a,b/_alias/a1?case=false"
      indices.`/$indexes/_alias/$alias`(Seq("a", "b"), "a1").write()
        .`case`(true).getUrl ==> "/a,b/_alias/a1?case=true"
      indices.`/$indexes/_alias/$alias`(Seq("a", "b"), "a1").delete()
        .`case`(false).getUrl ==> "/a,b/_alias/a1?case=false"
      api.`/`().$("a", "b")._alias.$("a1").read().getUrl ==> "/a,b/_alias/a1"
      api.`/`().$("a", "b")._alias.$("a1").check().getUrl ==> "/a,b/_alias/a1"
      api.`/`().$("a", "b")._alias.$("a1").write().getUrl ==> "/a,b/_alias/a1"
      api.`/`().$("a", "b")._alias.$("a1").delete().getUrl ==> "/a,b/_alias/a1"
      api.`/`().$("a")._alias.$("a1").read().getUrl ==> "/a/_alias/a1"
      api.`/`().$("a")._alias.$("a1").check().getUrl ==> "/a/_alias/a1"
      api.`/`().$("a")._alias.$("a1").write().getUrl ==> "/a/_alias/a1"
      api.`/`().$("a")._alias.$("a1").delete().getUrl ==> "/a/_alias/a1"

      indices.`/$indexes/_alias/$aliases`(Seq("a", "b"), Seq("a1", "a2")).read()
        .`case`(true).getUrl ==> "/a,b/_alias/a1,a2?case=true"
      indices.`/$indexes/_alias/$aliases`(Seq("a", "b"), Seq("a1", "a2")).check()
        .`case`(false).getUrl ==> "/a,b/_alias/a1,a2?case=false"
      indices.`/$indexes/_alias/$aliases`(Seq("a", "b"), Seq("a1", "a2")).delete()
        .`case`(false).getUrl ==> "/a,b/_alias/a1,a2?case=false"
      api.`/`().$("a", "b")._alias.$("a1", "a2").read().getUrl ==> "/a,b/_alias/a1,a2"
      api.`/`().$("a", "b")._alias.$("a1", "a2").check().getUrl ==> "/a,b/_alias/a1,a2"
      api.`/`().$("a", "b")._alias.$("a1", "a2").delete().getUrl ==> "/a,b/_alias/a1,a2"
      api.`/`().$("a")._alias.$("a1", "a2").read().getUrl ==> "/a/_alias/a1,a2"
      api.`/`().$("a")._alias.$("a1", "a2").check().getUrl ==> "/a/_alias/a1,a2"
      api.`/`().$("a")._alias.$("a1", "a2").delete().getUrl ==> "/a/_alias/a1,a2"

      indices.`/$indexes/_alias/*`("a", "b").read()
        .`case`(true).getUrl ==> "/a,b/_alias/*?case=true"
      indices.`/$indexes/_alias/*`("a", "b").check()
        .`case`(false).getUrl ==> "/a,b/_alias/*?case=false"
      indices.`/$indexes/_alias/*`("a", "b").delete()
        .`case`(false).getUrl ==> "/a,b/_alias/*?case=false"
      api.`/`().$("a", "b")._alias.*.read().getUrl ==> "/a,b/_alias/*"
      api.`/`().$("a", "b")._alias.*.check().getUrl ==> "/a,b/_alias/*"
      api.`/`().$("a", "b")._alias.*.delete().getUrl ==> "/a,b/_alias/*"
      api.`/`().$("a")._alias.*.read().getUrl ==> "/a/_alias/*"
      api.`/`().$("a")._alias.*.check().getUrl ==> "/a/_alias/*"
      api.`/`().$("a")._alias.*.delete().getUrl ==> "/a/_alias/*"

      indices.`/_all/_alias/*`().read().pretty(true).getUrl ==> "/_all/_alias/*?pretty=true"
      indices.`/_all/_alias/*`().check().pretty(false).getUrl ==> "/_all/_alias/*?pretty=false"
      indices.`/_all/_alias/*`().delete().pretty(false).getUrl ==> "/_all/_alias/*?pretty=false"
      api.`/`()._all._alias.*.read().getUrl ==> "/_all/_alias/*"
      api.`/`()._all._alias.*.check().getUrl ==> "/_all/_alias/*"
      api.`/`()._all._alias.*.delete().getUrl ==> "/_all/_alias/*"

      indices.`/_all/_alias/$aliases`("a1", "a2").read().`case`(true).getUrl ==> "/_all/_alias/a1,a2?case=true"
      indices.`/_all/_alias/$aliases`("a1", "a2").check().`case`(false).getUrl ==> "/_all/_alias/a1,a2?case=false"
      indices.`/_all/_alias/$aliases`("a1", "a2").delete().`case`(false).getUrl ==> "/_all/_alias/a1,a2?case=false"
      api.`/`()._all._alias.$("a1", "a2").read().getUrl ==> "/_all/_alias/a1,a2"
      api.`/`()._all._alias.$("a1", "a2").check().getUrl ==> "/_all/_alias/a1,a2"
      api.`/`()._all._alias.$("a1", "a2").delete().getUrl ==> "/_all/_alias/a1,a2"

      indices.`/_all/_alias/*`().read()
        .`case`(true).getUrl ==> "/_all/_alias/*?case=true"
      indices.`/_all/_alias/*`().check()
        .`case`(false).getUrl ==> "/_all/_alias/*?case=false"
      indices.`/_all/_alias/*`().delete()
        .`case`(false).getUrl ==> "/_all/_alias/*?case=false"
      api.`/`()._all._alias.*.read().getUrl ==> "/_all/_alias/*"
      api.`/`()._all._alias.*.check().getUrl ==> "/_all/_alias/*"
      api.`/`()._all._alias.*.delete().getUrl ==> "/_all/_alias/*"

      //3.5

      indices.`/_settings`().read().pretty(true).getUrl ==> "/_settings?pretty=true"
      indices.`/_settings`().writeS("TEST").pretty(false).getUrl ==> "/_settings?pretty=false"
      api.`/`()._settings.read().getUrl ==> "/_settings"
      api.`/`()._settings.writeS("TEST").getUrl ==> "/_settings"

      indices.`/_settings/name=$name`("n").read().human(true).getUrl ==> "/_settings/name=n?human=true"
      api.`/`()._settings.name.$("n").read().getUrl ==> "/_settings/name=n"

      indices.`/_all/_settings`().read().pretty(true).getUrl ==> "/_all/_settings?pretty=true"
      indices.`/_all/_settings`().writeS("TEST").pretty(false).getUrl ==> "/_all/_settings?pretty=false"
      api.`/`()._all._settings.read().getUrl ==> "/_all/_settings"
      api.`/`()._all._settings.writeS("TEST").getUrl ==> "/_all/_settings"

      indices.`/_all/_settings/name=$name`("n").read().human(true).getUrl ==> "/_all/_settings/name=n?human=true"
      api.`/`()._all._settings.name.$("n").read().getUrl ==> "/_all/_settings/name=n"

      indices.`/$indexes/_settings`("a", "b").read().pretty(true).getUrl ==> "/a,b/_settings?pretty=true"
      indices.`/$indexes/_settings`("a", "b").writeS("TEST").pretty(false).getUrl ==> "/a,b/_settings?pretty=false"
      api.`/`().$("a")._settings.read().getUrl ==> "/a/_settings"
      api.`/`().$("a")._settings.writeS("TEST").getUrl ==> "/a/_settings"
      api.`/`().$("a", "b")._settings.read().getUrl ==> "/a,b/_settings"
      api.`/`().$("a", "b")._settings.writeS("TEST").getUrl ==> "/a,b/_settings"

      indices.`/$indexes/_settings/name=$name`(Seq("a", "b"), "n").read().human(true)
        .getUrl ==> "/a,b/_settings/name=n?human=true"
      api.`/`().$("a")._settings.name.$("n").read().getUrl ==> "/a/_settings/name=n"
      api.`/`().$("a", "b")._settings.name.$("n").read().getUrl ==> "/a,b/_settings/name=n"

      // 3.6

      indices.`/_analyze`().readS("TEST").`case`(true).getUrl ==> "/_analyze?case=true"
      api.`/`()._analyze.readS("TEST").getUrl ==> "/_analyze"

      indices.`/$index/_analyze`("a").readS("TEST").`case`(false).getUrl ==> "/a/_analyze?case=false"
      api.`/`().$("a")._analyze.readS("TEST").getUrl ==> "/a/_analyze"

      // 3.7

      indices.`/_template`().read().filter_path("f1", "f2").getUrl ==> "/_template?filter_path=f1,f2"
      api.`/`()._template.read().getUrl ==> "/_template"

      indices.`/_template/$template`("t").read().filter_path("f1", "f2").getUrl ==> "/_template/t?filter_path=f1,f2"
      indices.`/_template/$template`("t").check().filter_path("f1").getUrl ==> "/_template/t?filter_path=f1"
      api.`/`()._template.$("t").read().getUrl ==> "/_template/t"
      api.`/`()._template.$("t").check().getUrl ==> "/_template/t"

      indices.`/_template/$templates`("t1", "t2").read().filter_path("f1", "f2")
        .getUrl ==> "/_template/t1,t2?filter_path=f1,f2"
      api.`/`()._template.$("t1", "t2").read().getUrl ==> "/_template/t1,t2"

      // 3.8

      indices.`/_stats`().read()
        .level(StatsLevel("l1")).groups("g1", "g2").types("t1", "t2").human(true)
        .getUrl ==> "/_stats?level=l1&groups=g1,g2&types=t1,t2&human=true"
      api.`/`()._stats.read().getUrl ==> "/_stats"

      indices.`/_stats/$statsGroups`("sg1", "sg2").read()
        .groups("g1").human(false)
        .getUrl ==> "/_stats/sg1,sg2?groups=g1&human=false"
      api.`/`()._stats.$("sg1", "sg2").read().getUrl ==> "/_stats/sg1,sg2"

      indices.`/_stats/$statsGroups/$fieldGroups`(Seq("sg1", "sg2"), Seq("fg1", "fg2")).read()
        .level(StatsLevel("l1")).groups("g1", "g2").types("t1", "t2").human(true)
        .getUrl ==> "/_stats/sg1,sg2/fg1,fg2?level=l1&groups=g1,g2&types=t1,t2&human=true"
      api.`/`()._stats.$("sg1", "sg2").$("fg1", "fg2").read().getUrl ==> "/_stats/sg1,sg2/fg1,fg2"

      indices.`/$indexes/_stats`("a", "b").read()
        .groups("g1").human(false)
        .getUrl ==> "/a,b/_stats?groups=g1&human=false"
      api.`/`().$("a")._stats.read().getUrl ==> "/a/_stats"
      api.`/`().$("a", "b")._stats.read().getUrl ==> "/a,b/_stats"

      indices.`/$indexes/_stats/$statsGroups`(
        Seq("a", "b"), Seq("sg1", "sg2")).read()
        .level(StatsLevel("l1")).groups("g1", "g2").types("t1", "t2").human(true)
        .getUrl ==> "/a,b/_stats/sg1,sg2?level=l1&groups=g1,g2&types=t1,t2&human=true"
      api.`/`().$("a")._stats.$("sg1", "sg2").read().getUrl ==> "/a/_stats/sg1,sg2"
      api.`/`().$("a", "b")._stats.$("sg1", "sg2").read().getUrl ==> "/a,b/_stats/sg1,sg2"

      indices.`/$indexes/_stats/$statsGroups/$fieldGroups`(
        Seq("a", "b"), Seq("sg1", "sg2"), Seq("fg1", "fg2")).read()
        .level(StatsLevel("l1")).groups("g1", "g2").types("t1", "t2").human(true)
        .getUrl ==> "/a,b/_stats/sg1,sg2/fg1,fg2?level=l1&groups=g1,g2&types=t1,t2&human=true"
      api.`/`().$("a")._stats.$("sg1", "sg2").$("fg1", "fg2").read().getUrl ==> "/a/_stats/sg1,sg2/fg1,fg2"
      api.`/`().$("a", "b")._stats.$("sg1", "sg2").$("fg1", "fg2").read().getUrl ==> "/a,b/_stats/sg1,sg2/fg1,fg2"

      // 3.9

      indices.`/_segments`().read().verbose(true).pretty(true)
        .getUrl ==> "/_segments?verbose=true&pretty=true"
      api.`/`()._segments.read().getUrl ==> "/_segments"

      indices.`/_all/_segments`().read().verbose(false).pretty(false)
        .getUrl ==> "/_all/_segments?verbose=false&pretty=false"
      api.`/`()._all._segments.read().getUrl ==> "/_all/_segments"

      indices.`/$indexes/_segments`("a", "b").read().getUrl ==> "/a,b/_segments"
      api.`/`().$("a")._segments.read().getUrl ==> "/a/_segments"
      api.`/`().$("a", "b")._segments.read().getUrl ==> "/a,b/_segments"

      // 3.10

      indices.`/_recovery`().read().detailed(true).active_only(true).pretty(true)
        .getUrl ==> "/_recovery?detailed=true&active_only=true&pretty=true"
      api.`/`()._recovery.read().getUrl ==> "/_recovery"

      indices.`/_all/_recovery`().read().detailed(false).active_only(false).pretty(false)
        .getUrl ==> "/_all/_recovery?detailed=false&active_only=false&pretty=false"
      api.`/`()._all._recovery.read().getUrl ==> "/_all/_recovery"

      indices.`/$indexes/_recovery`("a", "b").read().getUrl ==> "/a,b/_recovery"
      api.`/`().$("a")._recovery.read().getUrl ==> "/a/_recovery"
      api.`/`().$("a", "b")._recovery.read().getUrl ==> "/a,b/_recovery"
      
      // 3.11

      indices.`/_shard_stores`().read().status(ShardStatus("s")).pretty(true)
        .getUrl ==> "/_shard_stores?status=s&pretty=true"
      api.`/`()._shard_stores.read().getUrl ==> "/_shard_stores"

      indices.`/_all/_shard_stores`().read().status(ShardStatus("s")).pretty(false)
        .getUrl ==> "/_all/_shard_stores?status=s&pretty=false"
      api.`/`()._all._shard_stores.read().getUrl ==> "/_all/_shard_stores"

      indices.`/$indexes/_shard_stores`("a", "b").read().getUrl ==> "/a,b/_shard_stores"
      api.`/`().$("a")._shard_stores.read().getUrl ==> "/a/_shard_stores"
      api.`/`().$("a", "b")._shard_stores.read().getUrl ==> "/a,b/_shard_stores"

      // 3.12

      indices.`/_cache/clear`().send().fields("f1","f2").pretty(true)
        .getUrl ==> "/_cache/clear?fields=f1,f2&pretty=true"
      api.`/`()._cache.clear.send().getUrl ==> "/_cache/clear"

      indices.`/_all/_cache/clear`().send().pretty(false)
        .getUrl ==> "/_all/_cache/clear?pretty=false"
      api.`/`()._all._cache.clear.send().getUrl ==> "/_all/_cache/clear"

      indices.`/$indexes/_cache/clear`("a", "b").send().getUrl ==> "/a,b/_cache/clear"
      api.`/`().$("a")._cache.clear.send().getUrl ==> "/a/_cache/clear"
      api.`/`().$("a", "b")._cache.clear.send().getUrl ==> "/a,b/_cache/clear"

      // 3.13

      indices.`/_flush`().send().wait_if_ongoing(true).force(true).human(true)
        .getUrl ==> "/_flush?wait_if_ongoing=true&force=true&human=true"
      api.`/`()._flush.send().getUrl ==> "/_flush"

      indices.`/_all/_flush`().send().wait_if_ongoing(false).force(false).human(false)
        .getUrl ==> "/_all/_flush?wait_if_ongoing=false&force=false&human=false"
      api.`/`()._all._flush.send().getUrl ==> "/_all/_flush"

      indices.`/$indexes/_flush`("a", "b").send().getUrl ==> "/a,b/_flush"
      api.`/`().$("a")._flush.send().getUrl ==> "/a/_flush"
      api.`/`().$("a", "b")._flush.send().getUrl ==> "/a,b/_flush"

      // 3.14

      indices.`/_refresh`().send().human(true)
        .getUrl ==> "/_refresh?human=true"
      api.`/`()._refresh.send().getUrl ==> "/_refresh"

      indices.`/_all/_refresh`().send().human(false)
        .getUrl ==> "/_all/_refresh?human=false"
      api.`/`()._all._refresh.send().getUrl ==> "/_all/_refresh"

      indices.`/$indexes/_refresh`("a", "b").send().getUrl ==> "/a,b/_refresh"
      api.`/`().$("a")._refresh.send().getUrl ==> "/a/_refresh"
      api.`/`().$("a", "b")._refresh.send().getUrl ==> "/a,b/_refresh"

      // 3.15

      indices.`/_forcemerge`().send()
        .flat_settings(true).max_num_segments(1).only_expunge_deletes(true).flush(true)
        .getUrl ==> "/_forcemerge?flat_settings=true&max_num_segments=1&only_expunge_deletes=true&flush=true"
      api.`/`()._forcemerge.send().getUrl ==> "/_forcemerge"

      indices.`/_all/_forcemerge`().send()
        .flat_settings(false).max_num_segments(2).only_expunge_deletes(false).flush(false)
        .getUrl ==> "/_all/_forcemerge?flat_settings=false&max_num_segments=2&only_expunge_deletes=false&flush=false"
      api.`/`()._all._forcemerge.send().getUrl ==> "/_all/_forcemerge"

      indices.`/$indexes/_forcemerge`("a", "b").send().getUrl ==> "/a,b/_forcemerge"
      api.`/`().$("a")._forcemerge.send().getUrl ==> "/a/_forcemerge"
      api.`/`().$("a", "b")._forcemerge.send().getUrl ==> "/a,b/_forcemerge"

      // 3.16

      indices.`/_upgrade`().send().human(true).only_ancient_segments(false)
        .getUrl ==> "/_upgrade?human=true&only_ancient_segments=false"
      api.`/`()._upgrade.send().getUrl ==> "/_upgrade"

      indices.`/_all/_upgrade`().send().human(false).only_ancient_segments(true)
        .getUrl ==> "/_all/_upgrade?human=false&only_ancient_segments=true"
      api.`/`()._all._upgrade.send().getUrl ==> "/_all/_upgrade"

      indices.`/$indexes/_upgrade`("a", "b").send().getUrl ==> "/a,b/_upgrade"
      api.`/`().$("a")._upgrade.send().getUrl ==> "/a/_upgrade"
      api.`/`().$("a", "b")._upgrade.send().getUrl ==> "/a,b/_upgrade"
      
      
    }
  }
}
