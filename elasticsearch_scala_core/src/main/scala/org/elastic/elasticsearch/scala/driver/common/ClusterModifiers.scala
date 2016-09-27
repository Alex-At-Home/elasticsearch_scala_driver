package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.ClusterModifiers._
import org.elastic.elasticsearch.scala.driver.common.CommonModifiers._
import org.elastic.elasticsearch.scala.driver.common.CommonModifierGroups._
import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, Modifier}

/** Parameters for resources to monitor and manage top level Elasticsearch cluster attributes
  */
object ClusterModifiers {

  /** (modifier - see method for details) */
  trait WaitForStatus extends Modifier { self: BaseDriverOp =>
    /** One of `"green"`, `"yellow"` or `"red"`. Will wait (until the timeout provided) until the status of the cluster changes to
      * the one provided or better, i.e. green > yellow > red. By default, will not wait for any status.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Docs]]
      *
      * @param status "red", "yellow" or "green", the status to wait for
      */
    def wait_for_status(status: String): this.type = self.withModifier(this.getModifier(status))
  }

  /** (modifier - see method for details) */
  trait WaitForRelocatingShards extends Modifier { self: BaseDriverOp =>
    /** A number controlling to how many relocating shards to wait for. Usually will be `0` to indicate to wait
      * till all relocations have happened. Defaults to not wait.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Docs]]
      *
      * @param n The number of relocating shards to wait for (defaults to not wait; 0 to wait for all relocations)
      */
    def wait_for_relocating_shards(n: Integer): this.type = self.withModifier(this.getModifier(n))
  }

  /** (modifier - see method for details) */
  trait WaitForActiveShards extends Modifier { self: BaseDriverOp =>
    /** A number controlling to how many active shards to wait for. Defaults to not wait.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Docs]]
      *
      * @param n The number of active shards to wait for (defaults to not wait)
      */
    def wait_for_active_shards(n: Integer): this.type = self.withModifier(this.getModifier(n))
  }

  /** (modifier - see method for details) */
  trait WaitForNodes extends Modifier { self: BaseDriverOp =>
    /** The request waits until the specified number N of nodes is available
      * (See also string version, supports `>=N`, `<=N`, `>N` and `<N`).
      * Alternatively, it is possible to use `ge(N)`, `le(N)`, `gt(N)` and `lt(N)` notation.)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Docs]]
      *
      * @param n The number of active shards to wait for (defaults to not wait)
      */
    def wait_for_nodes(n: Integer): this.type = self.withModifier(this.getModifier(n))
    /** The request waits until the specified number N of nodes is available
      * Also supports `>=N`, `<=N`, `>N` and `<N`).
      * Alternatively, it is possible to use `ge(N)`, `le(N)`, `gt(N)` and `lt(N)` notation.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Docs]]
      *
      * @param n The number of active shards to wait for (defaults to not wait)
      */
    def wait_for_nodes(n: String): this.type = self.withModifier(this.getModifier(n))
  }

  /** (modifier - see method for details) */
  trait DryRun extends Modifier { self: BaseDriverOp =>
    /** Another option is to run the commands in dry_run (as a URI flag, or in the request body). This will cause the
      * commands to apply to the current cluster state, and return the resulting cluster after the commands
      * (and re-balancing) has been applied.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-reroute.html Docs]]
      *
      * @param b Whether to execute the command as a dry run
      */
    def dry_run(b: Boolean): this.type = self.withModifier(this.getModifier(b))
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
}
