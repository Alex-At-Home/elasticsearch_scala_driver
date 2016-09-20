package org.elastic.elasticsearch.scala.driver.common

import CommonModifiers._
import CommonModifierGroups._
import org.elastic.rest.scala.driver.RestBase._

/** Parameters for resources to monitor and manage indices in Elasticsearch
  */
object IndicesModifiers {

  // Opening and closing indexes

  /** (modifier - see method for details) */
  trait IgnoreUnavailable extends Modifier { self: BaseDriverOp =>
    /** It is possible to open and close multiple indices. An error will be thrown if the request explicitly refers
      * to a missing index. This behaviour can be disabled using the ignore_unavailable=true parameter.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html]]
      *
      * @param b Whether to error if any of the specified clusters are not available
      */
    def ignore_unavailable(b: Boolean): this.type = self.withModifier(this.getModifier(b))
  }

  // Index statistics

  /** (modifier - see method for details) */
  trait Groups extends Modifier { self: BaseDriverOp =>
    /**  You can include statistics for custom groups by adding an extra groups parameter (search operations can be
      * associated with one or more groups). The groups parameter accepts a comma separated list of group names.
      * Use _all to return statistics for all groups
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html#indices-stats]]
      *
      * @param groups The set of statistics groups for which to return statistics
      * @return The updated driver operation
      */
    def groups(groups: String*): this.type = self.withModifier(this.getModifier(groups))
  }

  /** (modifier - see method for details) */
  trait Types extends Modifier { self: BaseDriverOp =>
    /** The types over which to filter statistics
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html#indices-stats]]
      *
      * @param types The types over which to filter statistics
      * @return The updated driver operation
      */
    def types(types: String*): this.type = self.withModifier(this.getModifier(types))
  }

  /** (modifier - see method for details) */
  trait Level extends Modifier { self: BaseDriverOp =>
    /** Defines if field stats should be returned on a per index level or on a cluster wide level.
      * Valid values are indices and cluster (default).
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html#indices-stats]]
      *
      * @param level indices or cluster
      * @return The updated driver operation
      */
    def level(level: String): this.type = self.withModifier(this.getModifier(level))
  }
}

/** Common groupings of modifiers relating to search resources
  */
object IndicesModifierGroups {
  import IndicesModifiers._

  /** Parameters for resources for opening and closing indices */
  trait OpenCloseIndexesParams extends IgnoreUnavailable with StandardParams

  /** Parameters for getting index statistics parameters */
  trait IndexStatsParams extends Level with Groups with Types with StandardParams
}
