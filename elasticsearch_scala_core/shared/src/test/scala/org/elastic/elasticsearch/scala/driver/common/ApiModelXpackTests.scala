package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.rest.scala.driver.RestBase._

object ApiModelXpackTests extends TestSuite {

  val tests = this {
    "Basic checking for all the xpack resources" - {

      object api extends ApiModelCommon
      object xpack extends ApiModelXpack

      // Monitoring

      xpack.`/_cluster/settings#marvel.agent`().writeS("TEST").pretty(true).getUrl ==> "/_cluster/settings?pretty=true"
      api.`/`()._cluster.settings.`#marvel.agent`.writeS("TEST").getUrl ==> "/_cluster/settings"

      // Security

      xpack.`/_shield`().read().human(true).getUrl ==> "/_shield?human=true"
      api.`/`()._shield.read().getUrl ==> "/_shield"

      xpack.`/_shield/authenticate`().read().human(false).getUrl ==> "/_shield/authenticate?human=false"
      api.`/`()._shield.authenticate.read().getUrl ==> "/_shield/authenticate"

      xpack.`/_shield/realm/$realms/_clear_cache`("r1", "r2").send()
        .usernames("u1", "u2").pretty(true).getUrl ==> "/_shield/realm/r1,r2/_clear_cache?usernames=u1,u2&pretty=true"
      api.`/`()._shield.realm.$("r1", "r2")._clear_cache.send().getUrl ==> "/_shield/realm/r1,r2/_clear_cache"

      xpack.`/_shield/user/$username`("u").read().human(false).getUrl ==> "/_shield/user/u?human=false"
      xpack.`/_shield/user/$username`("u").sendS("TEST").human(true).getUrl ==> "/_shield/user/u?human=true"
      xpack.`/_shield/user/$username`("u").delete().getUrl ==> "/_shield/user/u"
      api.`/`()._shield.user.$("u").read().getUrl ==> "/_shield/user/u"
      api.`/`()._shield.user.$("u").sendS("TEST").getUrl ==> "/_shield/user/u"
      api.`/`()._shield.user.$("u").delete().getUrl ==> "/_shield/user/u"

      xpack.`/_shield/user/$usernames`("u1", "u2").read().pretty(true).getUrl ==> "/_shield/user/u1,u2?pretty=true"
      api.`/`()._shield.user.$("u1", "u2").read().getUrl ==> "/_shield/user/u1,u2"

      xpack.`/_shield/user`().read().pretty(false).getUrl ==> "/_shield/user?pretty=false"
      api.`/`()._shield.user.read().getUrl ==> "/_shield/user"

      xpack.`/_shield/role/$role`("u").read().human(false).getUrl ==> "/_shield/role/u?human=false"
      xpack.`/_shield/role/$role`("u").sendS("TEST").human(true).getUrl ==> "/_shield/role/u?human=true"
      xpack.`/_shield/role/$role`("u").delete().getUrl ==> "/_shield/role/u"
      api.`/`()._shield.role.$("u").read().getUrl ==> "/_shield/role/u"
      api.`/`()._shield.role.$("u").sendS("TEST").getUrl ==> "/_shield/role/u"
      api.`/`()._shield.role.$("u").delete().getUrl ==> "/_shield/role/u"

      xpack.`/_shield/role/$roles`("u1", "u2").read().pretty(true).getUrl ==> "/_shield/role/u1,u2?pretty=true"
      api.`/`()._shield.role.$("u1", "u2").read().getUrl ==> "/_shield/role/u1,u2"

      xpack.`/_shield/role`().read().pretty(false).getUrl ==> "/_shield/role?pretty=false"
      api.`/`()._shield.role.read().getUrl ==> "/_shield/role"

      // Licenses

      xpack.`/_license`().read().filter_path("f1").getUrl ==> "/_license?filter_path=f1"
      xpack.`/_license`().writeS("TEST")
        .acknowledge(true).flat_settings(true).getUrl ==> "/_license?acknowledge=true&flat_settings=true"
      api.`/`()._license.read().getUrl ==> "/_license"
      api.`/`()._license.writeS("TEST").getUrl ==> "/_license"

      // Snapshots

      xpack.`/_snapshot`().read().`case`(true).getUrl ==> "/_snapshot?case=true"
      api.`/`()._snapshot.read().getUrl ==> "/_snapshot"

      xpack.`/_snapshot/$snapshotRepo`("sr").read().`case`(false).getUrl ==> "/_snapshot/sr?case=false"
      xpack.`/_snapshot/$snapshotRepo`("sr").writeS("TEST")
        .`case`(true).verify(true).getUrl ==> "/_snapshot/sr?case=true&verify=true"
      api.`/`()._snapshot.$("sr").read().getUrl ==> "/_snapshot/sr"
      api.`/`()._snapshot.$("sr").writeS("TEST").getUrl ==> "/_snapshot/sr"

      xpack.`/_snapshot/$snapshotRepo/_verify`("sr").send().`case`(true).getUrl ==> "/_snapshot/sr/_verify?case=true"
      api.`/`()._snapshot.$("sr")._verify.send().getUrl ==> "/_snapshot/sr/_verify"

      xpack.`/_snapshot/$snapshotRepos`("sr1", "sr2").read().`case`(true).getUrl ==> "/_snapshot/sr1,sr2?case=true"
      api.`/`()._snapshot.$("sr1", "sr2").read().getUrl ==> "/_snapshot/sr1,sr2"

      xpack.`/_snapshot/_all`().read().`case`(true).getUrl ==> "/_snapshot/_all?case=true"
      api.`/`()._snapshot._all.read().getUrl ==> "/_snapshot/_all"

      xpack.`/_snapshot/$snapshotRepo/$snapshotName`("sr", "sn").read()
        .pretty(true).getUrl ==> "/_snapshot/sr/sn?pretty=true"
      xpack.`/_snapshot/$snapshotRepo/$snapshotName`("sr", "sn").writeS("TEST")
        .wait_for_completion(true).pretty(false).getUrl ==> "/_snapshot/sr/sn?wait_for_completion=true&pretty=false"
      xpack.`/_snapshot/$snapshotRepo/$snapshotName`("sr", "sn").delete()
        .pretty(false).getUrl ==> "/_snapshot/sr/sn?pretty=false"
      api.`/`()._snapshot.$("sr").$("sn").read().getUrl ==> "/_snapshot/sr/sn"
      api.`/`()._snapshot.$("sr").$("sn").writeS("TEST").getUrl ==> "/_snapshot/sr/sn"
      api.`/`()._snapshot.$("sr").$("sn").delete().getUrl ==> "/_snapshot/sr/sn"

      xpack.`/_snapshot/$snapshotRepo/$snapshotNames`("sr", "sn1", "sn2").read()
        .pretty(true).ignore_unavailable(true).getUrl ==> "/_snapshot/sr/sn1,sn2?pretty=true&ignore_unavailable=true"
      api.`/`()._snapshot.$("sr").$("sn1", "sn2").read().getUrl ==> "/_snapshot/sr/sn1,sn2"

      xpack.`/_snapshot/$snapshotRepo/$snapshotName/_restore`("sr", "sn").send()
        .pretty(true).getUrl ==> "/_snapshot/sr/sn/_restore?pretty=true"
      xpack.`/_snapshot/$snapshotRepo/$snapshotName/_restore`("sr", "sn").sendS("TEST")
        .pretty(false).getUrl ==> "/_snapshot/sr/sn/_restore?pretty=false"
      api.`/`()._snapshot.$("sr").$("sn")._restore.send().getUrl ==> "/_snapshot/sr/sn/_restore"
      api.`/`()._snapshot.$("sr").$("sn")._restore.sendS("TEST").getUrl ==> "/_snapshot/sr/sn/_restore"

      xpack.`/_snapshot/_status`().read().human(true).getUrl ==> "/_snapshot/_status?human=true"
      xpack.`/_snapshot/$snapshotRepos/_status`("sr1", "sr2").read()
        .human(false).getUrl ==> "/_snapshot/sr1,sr2/_status?human=false"
      xpack.`/_snapshot/$snapshotRepo/$snapshotNames/_status`("sr1", "sn1", "sn2").read()
        .pretty(false).getUrl ==> "/_snapshot/sr1/sn1,sn2/_status?pretty=false"
      api.`/`()._snapshot._status.read().getUrl ==> "/_snapshot/_status"
      api.`/`()._snapshot.$("sr")._status.read().getUrl ==> "/_snapshot/sr/_status"
      api.`/`()._snapshot.$("sr1", "sr2")._status.read().getUrl ==> "/_snapshot/sr1,sr2/_status"
      api.`/`()._snapshot.$("sr").$("sn")._status.read().getUrl ==> "/_snapshot/sr/sn/_status"
      api.`/`()._snapshot.$("sr").$("sn1", "sn2")._status.read().getUrl ==> "/_snapshot/sr/sn1,sn2/_status"

      // Watcher

      xpack.`/_watcher`().read().pretty(true).getUrl ==> "/_watcher?pretty=true"
      api.`/`()._watcher.read().getUrl ==> "/_watcher"

      xpack.`/_watcher/watch/$watchName`("w1").read().pretty(false).getUrl ==> "/_watcher/watch/w1?pretty=false"
      xpack.`/_watcher/watch/$watchName`("w1").writeS("TEST").pretty(false)
        .master_timeout("10m").active(false).getUrl ==> "/_watcher/watch/w1?pretty=false&master_timeout=10m&active=false"
      xpack.`/_watcher/watch/$watchName`("w1").delete().pretty(false)
        .master_timeout("10m").getUrl ==> "/_watcher/watch/w1?pretty=false&master_timeout=10m"
      api.`/`()._watcher.watch.$("w1").read().getUrl ==> "/_watcher/watch/w1"
      api.`/`()._watcher.watch.$("w1").writeS("TEST").getUrl ==> "/_watcher/watch/w1"
      api.`/`()._watcher.watch.$("w1").delete().getUrl ==> "/_watcher/watch/w1"

      xpack.`/_watcher/watch/$watchName/_execute`("w1").sendS("TEST").pretty(false)
        .getUrl ==> "/_watcher/watch/w1/_execute?pretty=false"
      api.`/`()._watcher.watch.$("w1")._execute.sendS("TEST").getUrl ==> "/_watcher/watch/w1/_execute"

      xpack.`/_watcher/watch/$watchName/_ack`("w1").write().pretty(false)
        .getUrl ==> "/_watcher/watch/w1/_ack?pretty=false"
      api.`/`()._watcher.watch.$("w1")._ack.write().getUrl ==> "/_watcher/watch/w1/_ack"

      xpack.`/_watcher/watch/$watchName/_activate`("w1").write().pretty(false)
        .getUrl ==> "/_watcher/watch/w1/_activate?pretty=false"
      api.`/`()._watcher.watch.$("w1")._activate.write().getUrl ==> "/_watcher/watch/w1/_activate"

      xpack.`/_watcher/watch/$watchName/_deactivate`("w1").write().pretty(false)
        .getUrl ==> "/_watcher/watch/w1/_deactivate?pretty=false"
      api.`/`()._watcher.watch.$("w1")._deactivate.write().getUrl ==> "/_watcher/watch/w1/_deactivate"

      xpack.`/_watcher/stats`().read().metric("m").pretty(true).getUrl ==> "/_watcher/stats?metric=m&pretty=true"
      api.`/`()._watcher.stats.read().getUrl ==> "/_watcher/stats"

      xpack.`/_watcher/stats/$metric`("m").read().pretty(true).getUrl ==> "/_watcher/stats/m?pretty=true"
      api.`/`()._watcher.stats.$("m").read().getUrl ==> "/_watcher/stats/m"

      xpack.`/_watcher/_start`().write().pretty(false).getUrl ==> "/_watcher/_start?pretty=false"
      api.`/`()._watcher._start.write().getUrl ==> "/_watcher/_start"
      xpack.`/_watcher/_stop`().write().pretty(false).getUrl ==> "/_watcher/_stop?pretty=false"
      api.`/`()._watcher._stop.write().getUrl ==> "/_watcher/_stop"
      xpack.`/_watcher/_restart`().write().pretty(false).getUrl ==> "/_watcher/_restart?pretty=false"
      api.`/`()._watcher._restart.write().getUrl ==> "/_watcher/_restart"
    }
  }
}
