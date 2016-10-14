package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, Modifier, Param}

/** Modifiers used in the XPack API
  */
object XpackModifiers {

  /** (modifier - see method for details) */
  trait Usernames extends Modifier { self: BaseDriverOp =>
    /** To evict selected users, specify the usernames parameter
      * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-clear-cache-rest Docs]]
      *
      * @param users The usernames for which to clear the security cache
      * @return The updated driver operation
      */
    @Param def usernames(users: String*): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Acknowledge extends Modifier { self: BaseDriverOp =>
    /** If the license you are installing does not support all of the features available with your previous license,
      * you will be notified in the response. To complete the license installation, you must resubmit the license
      * update request and set the acknowledge parameter to true to indicate that you are aware of the changes.
      * [[https://www.elastic.co/guide/en/shield/current/license-management.html#installing-license Docs]]
      *
      * @param b Acknowledgement override
      * @return The updated driver operation
      */
    @Param def acknowledge(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait MasterTimeout extends Modifier { self: BaseDriverOp =>
    /** When updating a watch while it is executing, the put action will block and wait for the watch execution to
      * finish. Depending on the nature of the watch, in some situations this can take a while. For this reason,
      * the put watch action is associated with a timeout that is set to 10 seconds by default. You can control
      * this timeout by passing in the master_timeout parameter.
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#_timeouts Docs]]
      *
      * @param timeout The human readable (eg "10s") timeout string
      * @return The updated driver operation
      */
    @Param def master_timeout(timeout: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Active extends Modifier { self: BaseDriverOp =>
    /** When adding a watch you can also define its initial active state. You do that by setting the active parameter.
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-put-watch-active-state Docs]]
      *
      * @param b Whether to start the watch in active mode
      * @return The updated driver operation
      */
    @Param def active(b: Boolean): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Metric extends Modifier { self: BaseDriverOp =>
    /** Specify the metric in watcher for which to get statistics, one of
      * "queued_watches", "current_watches", "executing_watches", "_all"
      * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#_current_executing_watches_metric Docs]]
      *
      * @param metric The metric in watcher for which to get statistics, one of
      *               "queued_watches", "current_watches", "executing_watches", "_all"
      * @return The updated driver operation
      */
    @Param def metric(metric: String): this.type = Modifier.Body
  }


}
/** Collections of modifiers used in the XPack API
  */
object XpackModifierGroups {
  import XpackModifiers._

  /** Params for the Security/Shield clear cache resource */
  trait SecurityClearCacheParams extends Usernames with BaseDriverOp

  /** Params for the license installation */
  trait LicenseParams extends Acknowledge with BaseDriverOp

  /** Params for watcher writes */
  trait WatcherWriteParams extends MasterTimeout with Active with BaseDriverOp

  /** Params for watcher deletes */
  trait WatcherDeleteParams extends MasterTimeout with BaseDriverOp

  /** Params for watcher acks */
  trait WatcherAckParams extends MasterTimeout with BaseDriverOp

  /** Parms for metric*/
  trait MetricParams extends Metric with BaseDriverOp
}

