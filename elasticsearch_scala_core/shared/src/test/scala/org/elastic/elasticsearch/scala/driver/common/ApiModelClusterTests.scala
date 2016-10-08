package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.rest.scala.driver.RestBase._

object ApiModelClusterTests  extends TestSuite {

  val tests = this {
    "Basic checking for all the cluster resources" - {

      object api extends ApiModelCommon
      object clusters extends ApiModelCluster

      // Cluster health

      clusters.`/_cluster/health`().read().human(true)
        .local(true).timeout("1m").level("red").wait_for_active_shards(1)
        .wait_for_nodes(2).wait_for_relocating_shards(1).wait_for_status("red")
        .getUrl ==> "/_cluster/health?human=true&local=true&timeout=1m&level=red&wait_for_active_shards=1" +
                    "&wait_for_nodes=2&wait_for_relocating_shards=1&wait_for_status=red"

      api.`/`()._cluster.health.read().wait_for_nodes("3").getUrl ==> "/_cluster/health?wait_for_nodes=3"

      clusters.`/_cluster/health/$indexes`("i1", "i2").read().human(true)
        .local(true).timeout("1m").level("red").wait_for_active_shards(1)
        .wait_for_nodes(2).wait_for_relocating_shards(1).wait_for_status("red")
        .getUrl ==> "/_cluster/health/i1,i2?human=true&local=true&timeout=1m&level=red&wait_for_active_shards=1" +
        "&wait_for_nodes=2&wait_for_relocating_shards=1&wait_for_status=red"

      api.`/`()._cluster.health.$("i1", "i2").read().wait_for_nodes("3")
        .getUrl ==> "/_cluster/health/i1,i2?wait_for_nodes=3"

      // Cluster state

      clusters.`/_cluster/state`().read().pretty(true).getUrl ==> "/_cluster/state?pretty=true"
      api.`/`()._cluster.state.read().getUrl ==> "/_cluster/state"

      clusters.`/_cluster/state/$metrics`("m1", "m2").read().pretty(false)
        .getUrl ==> "/_cluster/state/m1,m2?pretty=false"
      api.`/`()._cluster.state.$("m1", "m2").read().getUrl ==> "/_cluster/state/m1,m2"

      clusters.`/_cluster/state/_all/$indexes`("i1", "i2").read().pretty(false)
        .getUrl ==> "/_cluster/state/_all/i1,i2?pretty=false"
      api.`/`()._cluster.state._all.$("i1", "i2").read().getUrl ==> "/_cluster/state/_all/i1,i2"

      clusters.`/_cluster/state/$metrics/$indexes`(Seq("m1", "m2"), Seq("i1", "i2")).read().pretty(false)
        .getUrl ==> "/_cluster/state/m1,m2/i1,i2?pretty=false"
      api.`/`()._cluster.state.$("m1", "m2").$("i1", "i2").read().getUrl ==> "/_cluster/state/m1,m2/i1,i2"

      // Misc cluster stuff

      clusters.`/_cluster/stats`().read().pretty(true).getUrl ==> "/_cluster/stats?pretty=true"
      api.`/`()._cluster.stats.read().getUrl ==> "/_cluster/stats"

      clusters.`/_cluster/pending_tasks`().read().pretty(false).getUrl ==> "/_cluster/pending_tasks?pretty=false"
      api.`/`()._cluster.pending_tasks.read().getUrl ==> "/_cluster/pending_tasks"

      clusters.`/_cluster/reroute`().sendS("TEST").pretty(true).explain(false).dry_run(true)
        .getUrl ==> "/_cluster/reroute?pretty=true&explain=false&dry_run=true"
      api.`/`()._cluster.reroute.sendS("TEST").getUrl ==> "/_cluster/reroute"

      clusters.`/_cluster/settings`().read().pretty(false).getUrl ==> "/_cluster/settings?pretty=false"
      clusters.`/_cluster/settings`().writeS("TEST").pretty(true).getUrl ==> "/_cluster/settings?pretty=true"

      // Node statistics

      clusters.`/_nodes/stats`().read().fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/stats?fields=f1,f2&groups=g1,g2&human=false"
      api.`/`()._nodes.stats.read().getUrl ==> "/_nodes/stats"

      clusters.`/_nodes/stats/$statsGroups`("sg1", "sg2").read().fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/stats/sg1,sg2?fields=f1,f2&groups=g1,g2&human=false"
      api.`/`()._nodes.stats.$("sg1", "sg2").read().getUrl ==> "/_nodes/stats/sg1,sg2"

      clusters.`/_nodes/$nodes/stats`("n1", "n2").read().fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/n1,n2/stats?fields=f1,f2&groups=g1,g2&human=false"
      api.`/`()._nodes.$("n1", "n2").stats.read().getUrl ==> "/_nodes/n1,n2/stats"

      clusters.`/_nodes/$nodes/stats/$statsGroups`(Seq("n1", "n2"), Seq("sg1", "sg2")).read().
        fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/n1,n2/stats/sg1,sg2?fields=f1,f2&groups=g1,g2&human=false"
      api.`/`()._nodes.$("n1", "n2").stats.$("sg1", "sg2").read().getUrl ==> "/_nodes/n1,n2/stats/sg1,sg2"

      // Node info

      clusters.`/_nodes`().read().human(true)
        .getUrl ==> "/_nodes?human=true"
      api.`/`()._nodes.read().getUrl ==> "/_nodes"

      clusters.`/_nodes/_all/$infoGroups`("i1", "i2").read().human(false).getUrl ==> "/_nodes/_all/i1,i2?human=false"
      api.`/`()._nodes._all.$("i1", "i2").read().getUrl ==> "/_nodes/_all/i1,i2"

      clusters.`/_nodes/$nodes`("n1", "n2").read().pretty(false).getUrl ==> "/_nodes/n1,n2?pretty=false"
      api.`/`()._nodes.$("n1", "n2").read().getUrl ==> "/_nodes/n1,n2"

      clusters.`/_nodes/$nodes/$infoGroups`(Seq("n1", "n2"), Seq("i1", "i2")).read().pretty(true)
        .getUrl ==> "/_nodes/n1,n2/i1,i2?pretty=true"
      api.`/`()._nodes.$("n1", "n2").$("i1", "i2").read().getUrl ==> "/_nodes/n1,n2/i1,i2"

      // Hot threads

      clusters.`/_nodes/hot_threads`().read()
        .human(true)
        .threads(3).interval("in").`type`("ty").ignore_idle_threads(true)
        .getUrl ==> "/_nodes/hot_threads?human=true&threads=3&interval=in&type=ty&ignore_idle_threads=true"
      api.`/`()._nodes.hot_threads.read().getUrl ==> "/_nodes/hot_threads"

      clusters.`/_nodes/$nodes/hot_threads`("n1", "n2").read()
        .human(false)
        .threads(2).interval("val").`type`("tpe").ignore_idle_threads(false)
        .getUrl ==> "/_nodes/n1,n2/hot_threads?human=false&threads=2&interval=val&type=tpe&ignore_idle_threads=false"
      api.`/`()._nodes.$("n1", "n2").hot_threads.read().getUrl ==> "/_nodes/n1,n2/hot_threads"

      // Task management

      clusters.`/_tasks`().read()
          .nodes("n1", "n2").actions("a1", "a2").wait_for_completion(true).timeout("1m")
          .flat_settings(true)
          .getUrl ==> "/_tasks?nodes=n1,n2&actions=a1,a2&wait_for_completion=true&timeout=1m&flat_settings=true"
      api.`/`()._tasks.read().getUrl ==> "/_tasks"

      clusters.`/_tasks/$taskId`("tid").read()
        .nodes("n1", "n2").actions("a1", "a2").wait_for_completion(false).timeout("10m")
        .flat_settings(false)
        .getUrl ==> "/_tasks/tid?nodes=n1,n2&actions=a1,a2&wait_for_completion=false&timeout=10m&flat_settings=false"
      api.`/`()._tasks.$("tid").read().getUrl ==> "/_tasks/tid"

      clusters.`/_tasks/_cancel`().send().node_id("n1", "n2").actions("a1", "a2").pretty(true)
        .getUrl ==> "/_tasks/_cancel?node_id=n1,n2&actions=a1,a2&pretty=true"
      api.`/`()._tasks._cancel.send().getUrl ==> "/_tasks/_cancel"

      clusters.`/_tasks/$taskId/_cancel`("tid").send().pretty(false)
        .getUrl ==> "/_tasks/tid/_cancel?pretty=false"
      api.`/`()._tasks.$("tid")._cancel.send().getUrl ==> "/_tasks/tid/_cancel"
    }
  }
}
