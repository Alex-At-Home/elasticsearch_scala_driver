package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestResources.{RestWritable, _}
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.elasticsearch.scala.driver.common.ApiModelNavigationTree._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.elasticsearch.scala.driver.common.ClusterModifierGroups._

//TODO - 2.3 -> latest

/** Resources to monitor and manage Elasticsearch top-level cluster attributes
  */
trait ApiModelCluster {

  // 4) Cluster operations

  // 4.1) Cluster health
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html

  /** The cluster health API allows to get a very simple status on the health of the cluster.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html Docs]]
    */
  case class `/_cluster/health`()
    extends `tree:/_cluster/health`
    with RestReadable[ClusterHealthParams]
    with RestResource

  /** The cluster health API allows to get a very simple status on the health of the cluster.
    * (Restricted to the specified indexes)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html Docs]]
    * @param indexes The indexes to which to restrict the health status request
    */
  case class `/_cluster/health/$indexes`(indexes: String*)
    extends RestReadable[ClusterHealthParams]
      with RestResource

  // 4.2) Cluster state
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-state.html

  /** The cluster state API allows to get a comprehensive state information of the whole cluster
    * (all metrics, all indexes)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-state.html Docs]]
    */
  case class `/_cluster/state`()
    extends `tree:/_cluster/state`
      with RestReadable[StandardParams]
      with RestResource

  /** The cluster state API allows to get a comprehensive state information of the whole cluster
    * (Specified metrics, all indexes)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-state.html Docs]]
    * @param metrics A comma separated list of `"version"`, `"master_node"`, `"nodes"`, `"routing_table"`, `"metadata"`,
    *                `"blocks"`
    */
  case class `/_cluster/state/$metrics`(metrics: String*)
    extends `tree:/_cluster/state/$metrics`
      with RestReadable[StandardParams]
      with RestResource

  /** The cluster state API allows to get a comprehensive state information of the whole
    * (all metrics, specified indexes)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-state.html Docs]]
    * @param indexes The indexes over which to restrict the state request
    */
  case class `/_cluster/state/_all/$indexes`(indexes: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** The cluster state API allows to get a comprehensive state information of the whole
    * (Specified metrics, specified indexes)
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-state.html Docs]]
    * @param metrics A comma separated list of `"version"`, `"master_node"`, `"nodes"`, `"routing_table"`, `"metadata"`,
    *                `"blocks"`
    * @param indexes The indexes over which to restrict the state request
    */
  case class `/_cluster/state/$metrics/$indexes`(metrics: Seq[String], indexes: Seq[String])
      extends RestReadable[StandardParams]
      with RestResource

  // 4.3) Cluster Stats
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-stats.html

  /** The Cluster Stats API allows to retrieve statistics from a cluster wide perspective.
    * The API returns basic index metrics (shard numbers, store size, memory usage) and information about the current
    * nodes that form the cluster (number, roles, os, jvm versions, memory usage, cpu and installed plugins).
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-stats.html Docs]]
    */
  case class `/_cluster/stats`()
      extends RestReadable[StandardParams]
      with RestResource

  // 4.4) Pending cluster tasks
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-pending.html

  /** The pending cluster tasks API returns a list of any cluster-level changes (e.g. create index, update mapping,
    * allocate or fail shard) which have not yet been executed.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-pending.html Docs]]
    */
  case class `/_cluster/pending_tasks`()
    extends RestReadable[StandardParams]
      with RestResource

  // 4.5) Cluster reroute
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-reroute.html

  /** The reroute command allows to explicitly execute a cluster reroute allocation command including specific
    * commands. For example, a shard can be moved from one node to another explicitly, an allocation can be canceled,
    * or an unassigned shard can be explicitly allocated on a specific node.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-reroute.html Docs]]
    */
  case class `/_cluster/reroute`()
    extends RestSendable[ClusterRerouteParams]
      with RestResource

  // 4.6) Update cluster settings
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-update-settings.html

  /** Allows to get/update cluster wide specific settings. Settings updated can either be persistent
    * (applied cross restarts) or transient (will not survive a full cluster restart).
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-update-settings.html Docs]]
    */
  case class `/_cluster/settings`()
    extends RestReadable[StandardParams]
      with RestWritable[StandardParams]
      with RestResource

  // 5) Node specific metadata

  // 5.1) Node statistics
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-stats.html

  /** The cluster nodes stats API allows to retrieve one or more (or all) of the cluster nodes statistics.
    * All statistics, all nodes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-stats.html Docs]]
    */
  case class `/_nodes/stats`()
    extends `tree:/_nodes/stats`
      with RestReadable[NodeStatsParams]
      with RestResource

  /** The cluster nodes stats API allows to retrieve one or more (or all) of the cluster nodes statistics.
    * Specified statistics groups, all nodes.
    * Groups: `indices`, `os`, `process`, `jvm`, `transport`, `http`, `fs`, `breaker` and `thread_pool`
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-stats.html Docs]]
    * @param statsGroups The statistics groups to obtain
    *                    Groups: `indices`, `os`, `process`, `jvm`, `transport`, `http`,
    *                    `fs`, `breaker` and `thread_pool`
    */
  case class `/_nodes/stats/$statsGroups`(statsGroups: String*)
    extends RestReadable[NodeStatsParams]
      with RestResource

  /** The cluster nodes stats API allows to retrieve one or more (or all) of the cluster nodes statistics.
    * All statistics, specified nodes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-stats.html Docs]]
    * @param nodes The list of nodes to which to restrict the stats request
    *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster.html (Node formats)]]
    */
  case class `/_nodes/$nodes/stats`(nodes: String*)
    extends `tree:/_nodes/$nodes/stats`
      with RestReadable[NodeStatsParams]
      with RestResource

  /** The cluster nodes stats API allows to retrieve one or more (or all) of the cluster nodes statistics.
    * Specified statistics groups, specified nodes.
    * Groups: `indices`, `os`, `process`, `jvm`, `transport`, `http`, `fs`, `breaker` and `thread_pool`
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-stats.html Docs]]
    * @param nodes The list of nodes to which to restrict the stats request
    *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster.html (Node formats)]]
    * @param statsGroups The statistics groups to obtain
    *                    Groups: `indices`, `os`, `process`, `jvm`, `transport`, `http`,
    *                    `fs`, `breaker` and `thread_pool`
    */
  case class `/_nodes/$nodes/stats/$statsGroups`(nodes: Seq[String], statsGroups: Seq[String])
    extends RestReadable[NodeStatsParams]
      with RestResource

  // 5.2) Nodes Info
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-info.html

  /** The cluster nodes info API allows to retrieve one or more (or all) of the cluster nodes information.
    * All info, all nodes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-info.html Docs]]
    */
  case class `/_nodes`()
    extends `tree:/_nodes`
      with RestReadable[StandardParams]
      with RestResource

  /** The cluster nodes info API allows to retrieve one or more (or all) of the cluster nodes information.
    * Specified info groups, all nodes.
    * Groups: `settings`, `os`, `process`, `jvm`, `thread_pool`, `transport`, `http` and `plugins`
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-info.html Docs]]
    * @param infoGroups The info groups to obtain
    *                    Groups: `settings`, `os`, `process`, `jvm`, `thread_pool`, `transport`,
    *                    `http` and `plugins`
    */
  case class `/_nodes/_all/$infoGroups`(infoGroups: String*)
    extends RestReadable[StandardParams]
      with RestResource

  /** The cluster nodes info API allows to retrieve one or more (or all) of the cluster nodes information.
    * All info, specified nodes
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-info.html Docs]]
    * @param nodes The list of nodes to which to restrict the stats request
    *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster.html (Node formats)]]
    */
  case class `/_nodes/$nodes`(nodes: String*)
    extends `tree:/_nodes/$nodes`
      with RestReadable[NodeStatsParams]
      with RestResource

  /** The cluster nodes info API allows to retrieve one or more (or all) of the cluster nodes information.
    * Specified info groups, specified nodes.
    * Groups: `settings`, `os`, `process`, `jvm`, `thread_pool`, `transport`, `http` and `plugins`
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-info.html Docs]]
    * @param nodes The list of nodes to which to restrict the stats request
    *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster.html (Node formats)]]
    * @param infoGroups The info groups to obtain
    *                    Groups: `settings`, `os`, `process`, `jvm`, `thread_pool`, `transport`,
    *                    `http` and `plugins`
    */
  case class `/_nodes/$nodes/$infoGroups`(nodes: Seq[String], infoGroups: Seq[String])
    extends RestReadable[NodeStatsParams]
      with RestResource

  // 5.3) Nodes hot threads
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-hot-threads.html

  /** An API allowing to get the current hot threads on each node in the cluster.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-hot-threads.html Docs]]
    */
  case class `/_nodes/hot_threads`()
      extends RestReadable[NodeHotThreadParams]
      with RestResource

  /** An API allowing to get the current hot threads on each node in the cluster.
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-nodes-hot-threads.html Docs]]
    * @param nodes The nodes over which to request the information
    *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster.html (Node formats)]]
    */
  case class `/_nodes/$nodes/hot_threads`(nodes: String*)
    extends RestReadable[NodeHotThreadParams]
      with RestResource

  // 5.4) Task management API
  // https://www.elastic.co/guide/en/elasticsearch/reference/2.3/tasks.html

  //TODO

}
object ApiModelCluster extends ApiModelCluster
