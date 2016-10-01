package org.elastic.elasticsearch.scala.driver.common

import utest._

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

      clusters.`/_cluster/reroute`().send("TEST").pretty(true).explain(false).dry_run(true)
        .getUrl ==> "/_cluster/reroute?pretty=true&explain=false&dry_run=true"
      api.`/`()._cluster.reroute.send("TEST").getUrl ==> "/_cluster/reroute"

      clusters.`/_cluster/settings`().read().pretty(false).getUrl ==> "/_cluster/settings?pretty=false"
      clusters.`/_cluster/settings`().write("TEST").pretty(true).getUrl ==> "/_cluster/settings?pretty=true"

      // Node statistics

      clusters.`/_nodes/stats`().read().fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/stats?fields=f1,f2&groups[g1,g2&human=false"
      api.`/`()._nodes.stats.read().getUrl ==> "/_nodes/stats"

      clusters.`/_nodes/stats/$statsGroups`("sg1", "sg2").read().fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/stats/sg1,sg2?fields=f1,f2&groups[g1,g2&human=false"
      api.`/`()._nodes.stats.$("sg1", "sg2").read().getUrl ==> "/_nodes/stats/sg1,sg2"

      clusters.`/_nodes/$nodes/stats`("n1", "n2").read().fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/n1,n2/stats?fields=f1,f2&groups[g1,g2&human=false"
      api.`/`()._nodes.$("n1", "n2").stats.read().getUrl ==> "/_nodes/n1,n2/stats"

      clusters.`/_nodes/$nodes/stats/$statsGroups`(Seq("n1", "n2"), Seq("sg1", "sg2")).read().
        fields("f1", "f2").groups("g1", "g2").human(false)
        .getUrl ==> "/_nodes/n1,n2/stats/sg1,sg2?fields=f1,f2&groups[g1,g2&human=false"
      api.`/`()._nodes.$("n1", "n2").stats.$("sg1", "sg2").read().getUrl ==> "/_nodes/n1,n2/stats/sg1,sg2"

      // Node info

      clusters.`/_nodes`().read().human(true)
        .getUrl ==> "/_nodes/stats?human=false"
      api.`/`()._nodes.stats.read().getUrl ==> "/_nodes/stats"

      //TODO
    }
  }
}
