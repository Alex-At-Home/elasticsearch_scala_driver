package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestBase._

/**
  * Modifiers for Elasticearch resources - apply across most API categories
  */
object CommonModifiers {

  // Control over the rendering of the reply strings

  /** (modifier - see method for details) */
  trait Pretty extends Modifier { self: BaseDriverOp =>
    /** Controls the format of the response (newlines/indendation) if returned as a string, else ignored
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/common-options.html#_pretty_results]]
      *
      * @param b The prettiness (newlines/indendation)
      * @return The updated driver operation
      */
    def pretty(b: Boolean = true): this.type = self.withModifier(this.getModifier(b))
  }

  /** (modifier - see method for details) */
  trait Human extends Modifier { self: BaseDriverOp =>
    /** Controls returning statistics as human readable strings instead of numbers
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/common-options.html#_human_readable_output]]
      *
      * @param b Whether to return statistics as human readable stats
      * @return The updated driver operation
      */
    def human(b: Boolean = true): this.type = self.withModifier(this.getModifier(b))
  }

  /** (modifier - see method for details) */
  trait Case extends Modifier { self: BaseDriverOp =>
    /** Controls returning JSON in `camelCase` instead of `snake_case`
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/common-options.html#_result_casing]]
      *
      * @param b Whether to return JSON in camel case
      * @return The updated driver operation
      */
    def `case`(b: Boolean = true): this.type = self.withModifier(this.getModifier(b))
  }

  /** (modifier - see method for details) */
  trait FilterPath extends Modifier { self: BaseDriverOp =>
    /** Removes unspecified fields from the JSON path
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/common-options.html#_response_filtering]]
      *
      * @param filterPath A list of fields to include (supports `*` and `**` for wildcarding)
      * @return The updated driver operation
      */
    def filter_path(filterPath: String*): this.type = self.withModifier(this.getModifier(filterPath))
  }

  /** (modifier - see method for details) */
  trait FlatSettings extends Modifier { self: BaseDriverOp =>
    /** Controls whether to return fields in flat mode (the fields themselves are returned in dot notation)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/common-options.html#_flat_settings]]
      *
      * @param b Whether to return the settings in flat mode
      * @return The updated driver operation
      */
    def flat_settings(b: Boolean = true): this.type = self.withModifier(this.getModifier(b))
  }

  // Control over write operations

  /** (modifier - see method for details) */
  trait Source extends Modifier { self: BaseDriverOp =>
    /** Allows to post bodies for `POST` or `PUT` in the query params
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/common-options.html#_request_body_in_query_string]]
      *
      * NOTE: not using this since it's more for libs that don't support bodies
      *
      * @param node The body to `POST`/`PUT`
      * @return The updated driver operation
      */
    def source(node: String): this.type = self.withModifier(this.getModifier(node))
  }

  /** (modifier - see method for details) */
  trait Routing extends Modifier { self: BaseDriverOp =>
    /** Represents a resource that can have the modifier ?routing
      * (Forces the request to go to a specific node in the cluster)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#index-routing Docs]]
      *
      * @param node The node to which to restrict the request
      * @return The updated driver operation
      */
    def routing(node: String): this.type = self.withModifier(this.getModifier(node))
  }
  /** (modifier - see method for details) */
  trait Version extends Modifier { self: BaseDriverOp =>
    /** . This will control the version of the document the operation is intended to be executed against.
      *  [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#index-versioning Docs]]
      *
      * @param v The version of the doc against which to apply the operation
      * @return The updated driver operation
      */
    def version(v: Int): this.type = self.withModifier(this.getModifier(v))
  }
  /** (modifier - see method for details) */
  trait OpType extends Modifier { self: BaseDriverOp =>
    /** The index operation also accepts an op_type that can be used to force a create operation,
      * eg allowing for "put-if-absent" behavior.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#operation-type Docs]]
      *
      * @param opType The operation type
      * @return The updated driver operation
      */
    def op_type(opType: String): this.type = self.withModifier(this.getModifier(opType))
  }
  /** (modifier - see method for details) */
  trait Parent extends Modifier { self: BaseDriverOp =>
    /** A child document can be indexed by specifying its parent when indexing
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#parent-children Docs]]
      *
      * @param parent The parent document id
      * @return The updated driver operation
      */
    def parent(parent: String): this.type = self.withModifier(this.getModifier(parent))
  }
  /** (modifier - see method for details) */
  trait Timeout extends Modifier { self: BaseDriverOp =>
    /** A search timeout, bounding the search request to be executed within the specified time value and bail with the
      * hits accumulated up to that point when expired. Defaults to no timeout.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#timeout Docs]]
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Cluster Health]]
      *
      * @param timeout The timeout
      * @return The updated driver operation
      */
    def timeout(timeout: String): this.type = self.withModifier(this.getModifier(timeout))
  }

  // Control the details of returned documents

  /** (modifier - see method for details) */
  trait SourceBase extends Modifier { self: BaseDriverOp =>
    /** Whether to include the _source object of the document in the return
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html Docs]]
      *
      * @param b Whether to include the _source field of the document in the return
      * @return The updated driver operation
      */
    def _source(b: Boolean): this.type = self.withModifier(this.getModifier(b))
  }
  /** (modifier - see method for details) */
  trait SourceInclude extends Modifier { self: BaseDriverOp =>
    /** Which fields from the _source object to include
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering Docs]]
      *
      * @param fields Which fields from the _source object to include
      * @return The updated driver operation
      */
    def _source_include(fields: String*): this.type = self.withModifier(this.getModifier(fields))
    /** Which fields from the _source object to include
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering Docs]]
      *
      * @param fields Which fields from the _source object to include
      * @return The updated driver operation
      */
    def _source(fields: String*): this.type = self.withModifier(this.getModifier(fields))
  }
  /** (modifier - see method for details) */
  trait SourceExclude extends Modifier { self: BaseDriverOp =>
    /** Which fields from the _source object to exclude
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering Docs]]
      *
      * @param fields Which fields from the _source object to include
      * @return The updated driver operation
      */
    def _source_exclude(fields: String*): this.type = self.withModifier(this.getModifier(fields))
  }
  /** (modifier - see method for details) */
  trait Fields extends Modifier { self: BaseDriverOp =>
    /** Which fields from the document index to include
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-fields Docs]]
      *
      * @param fields Which fields from the document index to include
      * @return The updated driver operation
      */
    def fields(fields: String*): this.type = self.withModifier(this.getModifier(fields))
  }

  // Misc other modifiers

  /** (modifier - see method for details) */
  trait Conflict extends Modifier { self: BaseDriverOp =>
    /** If you want to simply count version conflicts not cause the _update_by_query to abort you can set
      * conflicts=proceed on the url
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html Docs]]
      *
      * @param op If set to "proceed" then doesn't abort on version conflicts
      * @return The updated driver operation
      */
    def conflict(op: String): this.type = self.withModifier(this.getModifier(op))
  }

  // Cluster/index statistics

  /** (modifier - see method for details) */
  trait Level extends Modifier { self: BaseDriverOp =>
    /** Defines if various stats (cluster health. fields) should be returned on a per index level or on a
      * cluster wide level.
      * Valid values are "shards", "indices "and "cluster" (default).
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Cluster Health Stats]]
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html#indices-stats Index Stats]]
      *
      * @param level "shards", "indices" or "cluster"
      * @return The updated driver operation
      */
    def level(level: String): this.type = self.withModifier(this.getModifier(level))
  }

  // Cluster/search modifiers

  /** (modifier - see method for details) */
  trait Local extends Modifier { self: BaseDriverOp =>
    /** A boolean value whether to read the cluster state locally in order to determine where shards are allocated
      * instead of using the Master node’s cluster state.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-shards.html#_all_parameters Search resources]]
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-health.html#request-params Cluster resources]]
      *
      * @param b Whether the cluster state is read locally
      * @return The updated driver operation
      */
    def local(b: Boolean): this.type = self.withModifier(this.getModifier(b))
  }

  /** (modifier - see method for details) */
  trait Explain extends Modifier { self: BaseDriverOp =>
    /**
      * For search - For each hit, contain an explanation of how scoring of the hits was computed.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html#_parameters_3]]
      *
      * For cluster stats - If the explain parameter is specified, a detailed explanation of why the commands could or
      * could not be executed is returned.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/cluster-reroute.html]]
      *
      * @param b For each hit, whether to return an explanation of the request.
      * @return The updated driver operation
      */
    def explain(b: Boolean): this.type = self.withModifier(this.getModifier(b))
  }

}

/**
  * Common groupings of modifiers
  */
object CommonModifierGroups {

  import CommonModifiers._

  /** No modifiers are supported for these parameters */
  trait NoParams extends BaseDriverOp

  /** The standard set available to all ES resource
    * See [[https://www.elastic.co/guide/en/elasticsearch/reference/current/common-options.html]]
    */
  trait StandardParams extends Pretty with Human with Case with FilterPath with FlatSettings with BaseDriverOp

  /** Parameters controlling index operations */
  trait IndexWriteParams extends Version with OpType with Routing with Parent with Timeout with StandardParams

  /** Parameters controlling delete operations */
  trait IndexDeleteParams extends OpType with Routing with Parent with Timeout with StandardParams

  /** Parameters controlling the data returned from document access */
  trait DocumentReadParams extends SourceBase with SourceInclude with SourceExclude with Fields with StandardParams

  //TODO: this has changed in 2.4, good test for versioning...
  /** Parameters controlling the data returned from document access */
  trait UpdateByQueryParams extends Conflict with StandardParams

  /** Standard + routing parameters */
  trait RoutingStandardParams extends Routing with StandardParams

  /** Standard + field parameters */
  trait FieldStandardParams extends Fields with StandardParams
}
