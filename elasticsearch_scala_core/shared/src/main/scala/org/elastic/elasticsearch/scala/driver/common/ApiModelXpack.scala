package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestResources.{RestReadable, _}
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.elasticsearch.scala.driver.common.XpackModifierGroups._

/**
  * API for Xpack extensions - Monitoring (formerly Marvel), Security (formerly Shield),
  * Licensing, Snapshots, Watcher, Graph
  */
trait ApiModelXpack {

  //TODO navigation tree

  // 1) Marvel/Monitoring
  // https://www.elastic.co/guide/en/marvel/current/configuration.html

  //TODO this needs a data model (or maybe have an embedded mode?)

  /** Reads or writes the Marvel configuration
    * Note that if not using the typed version of this API, the JSON object is just written to
    * `/_cluster/settings` ie is not Marvel specific
    * [[https://www.elastic.co/guide/en/marvel/current/configuration.html Docs]]
    */
  case class `/_cluster/settings#marvel`()
    extends RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestResource
  {
    override lazy val location: String = "/_cluster/settings"
  }

  // 2) Shield/Security
  // https://www.elastic.co/guide/en/shield/current/shield-rest.html

  /** The Info API returns basic information about the Shield installation.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html Docs]]
    */
  case class `/_shield`()
    extends RestReadable[StandardParams]
    with RestResource

  /** The Authenticate API enables you to submit a request with a basic auth header to authenticate a user and
    * retrieve information about the authenticated user. Returns a 401 status code if the user cannot be authenticated.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html Docs]]
    */
  case class `/_shield/authenticate`()
    extends RestReadable[StandardParams]
      with RestResource

  /** The Clear Cache API evicts users from the user cache. You can completely clear the cache or evict specific users.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-clear-cache-rest Docs]]
    * [[https://www.elastic.co/guide/en/shield/current/controlling-user-cache.html (Cache information)]]
    * @param realms The list of realms to cache evict
    */
  case class `/_shield/realm/$realms/_clear_cache`(realms: String*)
    extends RestNoDataSendable[SecurityClearCacheParams]
      with RestResource

  /** The Users API enables you to create, read, update, and delete users from the native realm. These users are
    * commonly referred to as native users. To use this API, you must have at least the manage_security cluster
    * privilege.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-users-rest Docs]]
    * @param username The username to manage
    */
  case class `/_shield/user/$username`(username: String)
    extends RestReadable[StandardParams]
      with RestSendable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** The Users API enables you to create, read, update, and delete users from the native realm. These users are
    * commonly referred to as native users. To use this API, you must have at least the manage_security cluster
    * privilege.
    * @param usernames The usernames to retrieve
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-users-rest Docs]]
    */
  case class `/_shield/user/$usernames`(usernames: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** The Users API enables you to create, read, update, and delete users from the native realm. These users are
    * commonly referred to as native users. To use this API, you must have at least the manage_security cluster
    * privilege.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-users-rest Docs]]
    */
  case class `/_shield/user`()
    extends RestReadable[StandardParams]
      with RestResource

  /** The Roles API enables you to add, remove, and retrieve roles in the native Shield realm. To use this API,
    * you must have at least the manage_security cluster privilege.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-roles-rest Docs]]
    * @param role The role to manage
    */
  case class `/_shield/role/$role`(role: String)
    extends RestReadable[StandardParams]
      with RestSendable[StandardParams]
      with RestDeletable[StandardParams]
      with RestResource

  /** The Roles API enables you to add, remove, and retrieve roles in the native Shield realm. To use this API,
    * you must have at least the manage_security cluster privilege.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-roles-rest Docs]]
    * @param roles The roles to retrieve
    */
  case class `/_shield/role/$roles`(roles: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** The Roles API enables you to add, remove, and retrieve roles in the native Shield realm. To use this API,
    * you must have at least the manage_security cluster privilege.
    * [[https://www.elastic.co/guide/en/shield/current/shield-rest.html#shield-roles-rest Docs]]
    */
  case class `/_shield/role`()
    extends RestReadable[StandardParams]
      with RestResource

  // 3) Licensing
  // https://www.elastic.co/guide/en/shield/current/license-management.html

  /** You can update your license at runtime without shutting down your nodes. License updates take effect immediately.
    * The license is provided as a JSON file that you install with the license API. You need cluster admin privileges
    * to install the license.
    * [[https://www.elastic.co/guide/en/shield/current/license-management.html Docs]]
    */
  case class `/_license`()
    extends RestReadable[StandardParams]
    with RestWritable[LicenseParams]
    with RestResource

  // 4) Snapshots
  // https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html

  /** The snapshot and restore module allows to create snapshots of individual indices or an entire cluster
    * into a remote repository like shared file system, S3, or HDFS. These snapshots are great for backups
    * because they can be restored relatively quickly but they are not archival because they can only be restored to
    * versions of Elasticsearch that can read the index.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html Docs]]
    *
    * @param snapshotName The name of the snapshot to manage
    */
  case class `/_snapshot/$snapshotName`(snapshotName: String)
    extends RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestResource

  /** The snapshot and restore module allows to create snapshots of individual indices or an entire cluster
    * into a remote repository like shared file system, S3, or HDFS. These snapshots are great for backups
    * because they can be restored relatively quickly but they are not archival because they can only be restored to
    * versions of Elasticsearch that can read the index.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html Docs]]
    *
    * @param snapshotNames The names of the snapshot to retrieve
    */
  case class `/_snapshot/$snapshotNames`(snapshotNames: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** The snapshot and restore module allows to create snapshots of individual indices or an entire cluster
    * into a remote repository like shared file system, S3, or HDFS. These snapshots are great for backups
    * because they can be restored relatively quickly but they are not archival because they can only be restored to
    * versions of Elasticsearch that can read the index.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html Docs]]
    */
  case class `/_snapshot`()
    extends RestReadable[StandardParams]
      with RestResource

  /** The snapshot and restore module allows to create snapshots of individual indices or an entire cluster
    * into a remote repository like shared file system, S3, or HDFS. These snapshots are great for backups
    * because they can be restored relatively quickly but they are not archival because they can only be restored to
    * versions of Elasticsearch that can read the index.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html Docs]]
    */
  case class `/_snapshot/_all`()
    extends RestReadable[StandardParams]
      with RestResource

  // 5) Watcher
  // https://www.elastic.co/guide/en/watcher/current/api-rest.html

  /** The watcher info API gives basic version information on the watcher plugin that is installed.
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html Docs]]
    */
  case class `/_watcher`()
    extends RestReadable[StandardParams]
      with RestResource

  /** Read, Write or Delete watches
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-put-watch Write Docs]]
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-get-watch Read Docs]]
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-delete-watch Delete Docs]]
    * @param watchName The name of the watch to manage
    */
  case class `/_watcher/watch/$watchName`(watchName: String)
    extends RestReadable[StandardParams]
      with RestWritable[WatcherWriteParams]
      with RestDeletable[WatcherDeleteParams]
      with RestResource

  /** The execute watch API forces the execution of a stored watch. It can be used to force execution of the watch
    * outside of its triggering logic, or to test the watch for debugging purposes.
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-execute-watch Docs]]
    * @param watchName The name of the watch to execute
    */
  case class `/_watcher/watch/$watchName/_execute`(watchName: String)
    extends RestSendable[StandardParams]
      with RestResource

  /** Acknowledging a watch enables you to manually throttle execution of the watch’s actions.
    * An action’s acknowledgement state is stored in the `_status.actions.id.ack.state structure`.
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-ack-watch Docs]]
    * @param watchName The name of the watch to acknowledge
    */
  case class `/_watcher/watch/$watchName/_ack`(watchName: String)
    extends RestNoDataWritable[WatcherAckParams]
      with RestResource

  /** A watch can be either active or inactive. This API enables you to activate a currently inactive watch.
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-activate-watch Docs]]
    * @param watchName The name of the watch to activate
    */
  case class `/_watcher/watch/$watchName/_activate`(watchName: String)
    extends RestNoDataWritable[StandardParams]
      with RestResource

  /** A watch can be either active or inactive. This API enables you to deactivate a currently active watch.
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-activate-watch Docs]]
    * @param watchName The name of the watch to deactivate
    */
  case class `/_watcher/watch/$watchName/_deactivate`(watchName: String)
    extends RestNoDataWritable[StandardParams]
      with RestResource

  /** The watcher stats API returns information on the aspects of watcher on your cluster.
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-stats Docs]]
    */
  case class `/_watcher/stats`()
    extends RestReadable[MetricParams]
      with RestResource

  /** The watcher stats API returns information on the aspects of watcher on your cluster.
    * This resource returns against one of the following metrics:
    * "queued_watches", "current_watches", "executing_watches", "_all"
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#_current_executing_watches_metric Docs]]
    * @param metric The metric stats to achieve
    */
  case class `/_watcher/stats/$metric`(metric: String)
    extends RestReadable[StandardParams]
      with RestResource

  /** The start watcher API starts the watcher service if the service is not already running
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-start Docs]]
    */
  case class `/_watcher/_start`()
    extends RestNoDataWritable[StandardParams]
      with RestResource

  /** The stop watcher API stops the watcher service if the service is running
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-stop Docs]]
    */
  case class `/_watcher/_stop`()
    extends RestNoDataWritable[StandardParams]
      with RestResource

  /** The restart watcher API stops, then starts the watcher service
    * [[https://www.elastic.co/guide/en/watcher/current/api-rest.html#api-rest-restart Docs]]
    */
  case class `/_watcher/_restart`()
    extends RestNoDataWritable[StandardParams]
      with RestResource

  // 6) Graph

  //TODO

}
