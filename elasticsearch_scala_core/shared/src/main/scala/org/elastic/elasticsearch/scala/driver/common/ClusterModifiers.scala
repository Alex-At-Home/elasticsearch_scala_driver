package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.ClusterModifiers._
import org.elastic.elasticsearch.scala.driver.common.CommonModifiers._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, Modifier, Param}

/** Parameters for resources to monitor and manage top level Elasticsearch cluster attributes
  */
object ClusterModifiers {

  /** (modifier - see method for details) */
  trait WaitForStatus extends Modifier { 
    /** One of `"green"`, `"yellow"` or `"red"`. Will wait (until the timeout provided) until the status of the cluster changes to
      * the one provided or better, i.e. green > yellow > red. By default, will not wait for any status.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-health.html#request-params Docs]]
      *
      * @param status "red", "yellow" or "green", the status to wait for
      */
    @Param def wait_for_status(status: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait WaitForRelocatingShards extends Modifier { 
    /** A number controlling to how many relocating shards to wait for. Usually will be `0` to indicate to wait
      * till all relocations have happened. Defaults to not wait.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-health.html#request-params Docs]]
      *
      * @param n The number of relocating shards to wait for (defaults to not wait; 0 to wait for all relocations)
      */
    @Param def wait_for_relocating_shards(n: Integer): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait WaitForActiveShards extends Modifier { 
    /** A number controlling to how many active shards to wait for. Defaults to not wait.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-health.html#request-params Docs]]
      *
      * @param n The number of active shards to wait for (defaults to not wait)
      */
    @Param def wait_for_active_shards(n: Integer): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait WaitForNodes extends Modifier { 
    /** The request waits until the specified number N of nodes is available
      * (See also string version, supports `>=N`, `<=N`, `>N` and `<N`).
      * Alternatively, it is possible to use `ge(N)`, `le(N)`, `gt(N)` and `lt(N)` notation.)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-health.html#request-params Docs]]
      *
      * @param n The number of active shards to wait for (defaults to not wait)
      */
    @Param def wait_for_nodes(n: Integer): this.type = Modifier.Body
    /** The request waits until the specified number N of nodes is available
      * Also supports `>=N`, `<=N`, `>N` and `<N`).
      * Alternatively, it is possible to use `ge(N)`, `le(N)`, `gt(N)` and `lt(N)` notation.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-health.html#request-params Docs]]
      *
      * @param n The number of active shards to wait for (defaults to not wait)
      */
    @Param def wait_for_nodes(n: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait DryRun extends Modifier { 
    /** Another option is to run the commands in dry_run (as a URI flag, or in the request body). This will cause the
      * commands to apply to the current cluster state, and return the resulting cluster after the commands
      * (and re-balancing) has been applied.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-reroute.html Docs]]
      *
      * @param b Whether to execute the command as a dry run
      */
    @Param def dry_run(b: Boolean): this.type = Modifier.Body
  }

  // Nodes

  /** (modifier - see method for details) */
  trait Threads extends Modifier { 
    /** The number of hot threads to provide, defaults to 3.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-nodes-hot-threads.html Docs]]
      *
      * @param n number of hot threads to provide, defaults to 3.
      */
    @Param def threads(n: Int): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Interval extends Modifier { 
    /** The interval to do the second sampling of threads. Defaults to 500ms.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-nodes-hot-threads.html Docs]]
      *
      * @param interval the interval to do the second sampling of threads. Defaults to 500ms.
      */
    @Param def interval(interval: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Type extends Modifier { 
    /** The type to sample, defaults to `cpu`, but supports `wait` and `block`
      * to see hot threads that are in wait or block state.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-nodes-hot-threads.html Docs]]
      *
      * @param `type` The type to sample, defaults to `cpu`, but supports `wait` and `block`
      *               to see hot threads that are in wait or block state.
      */
    @Param def `type`(`type`: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait IgnoreIdleThreads extends Modifier { 
    /** If true, known idle threads (e.g. waiting in a socket select, or to get a task from an empty queue) are
      * filtered out. Defaults to true.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster-nodes-hot-threads.html Docs]]
      *
      * @param b Whether to ignore idle threads (defaults to true)
      */
    @Param def ignore_idle_threads(b: Boolean): this.type = Modifier.Body
  }

  // Task management

  /** (modifier - see method for details) */
  trait Nodes extends Modifier { 
    /** The nodes on which to filter the task info request
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/tasks.html Docs]]
      *
      * @param nodes The list of nodes to which to restrict the task info request
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster.html (Node formats)]]
      */
    @Param def nodes(nodes: String*): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait NodeId extends Modifier { 
    /** The nodes on which to filter the task cancel request
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/tasks.html Docs]]
      *
      * @param nodes The list of nodes to which to restrict the task cancel request
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster.html (Node formats)]]
      */
    @Param def node_id(nodes: String*): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait ParentTaskId extends Modifier { 
    /** For task info requests, selects on the task id of the "spawning" parent
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/tasks.html Docs]]
      *
      * @param taskId The id of the task that "spawned" children
      */
    @Param def parent_task_id(taskId: String): this.type = Modifier.Body
  }

  /** (modifier - see method for details) */
  trait Actions extends Modifier { 
    /** The actions on which to filter the task info request
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/tasks.html Docs]]
      *
      * @param actions The list of actions to which to restrict the task info/action request
      */
    @Param def actions(actions: String*): this.type = Modifier.Body
  }
}
/** Common groupings of modifiers relating to cluster resources
  */
object ClusterModifierGroups {

  /** Parameters controlling cluster health requests */
  trait ClusterHealthParams
    extends WaitForStatus
      with WaitForActiveShards with WaitForRelocatingShards
      with WaitForNodes
      with Local with Timeout with Level
      with StandardParams

  /** Parameters controlling the `_cluster/reroute` resource */
  trait ClusterRerouteParams extends Explain with DryRun with StandardParams

  /** Parameters controlling node statistics */
  trait NodeStatsParams extends Fields with Groups with StandardParams

  /** Parameters controlling the nodes' hot_threads resource */
  trait NodeHotThreadParams
    extends Threads with Interval with Type with IgnoreIdleThreads with StandardParams

  /** Parameters controlling task info requests */
  trait TaskInfoParams
    extends Nodes with Actions with WaitForCompletion with Timeout with StandardParams

  /** Parameters controlling task cancel requests */
  trait TaskCancelParams extends NodeId with Actions with StandardParams
}
