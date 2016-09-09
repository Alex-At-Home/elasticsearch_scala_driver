package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, Modifier}

/**
  * Modifiers for Elasticearch resources
  */
object CommonModifiers {

  // Control over the rendering of the reply strings

  /** (modifier - see method for details) */
  trait Pretty extends Modifier { self: BaseDriverOp =>
    /** Controls the format of the response if returned as a string, else ignored
      * @param b The prettiness
      * @return The updated driver operation
      */
    def pretty(b: Boolean = true): this.type = self.withModifier(this.getModifier(b))
  }

  // Control over write operations

  /** (modifier - see method for details) */
  trait Routing extends Modifier { self: BaseDriverOp =>
    /** Represents a resource that can have the modifier ?routing
      * (Forces the request to go to a specific node in the cluster)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#index-routing Docs]]
      * @param node The node to which to restrict the request
      * @return The updated driver operation
      */
    def routing(node: String): this.type = self.withModifier(this.getModifier(node))
  }
  /** (modifier - see method for details) */
  trait Version extends Modifier { self: BaseDriverOp =>
    /** . This will control the version of the document the operation is intended to be executed against.
      *  [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#index-versioning Docs]]
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
      * @param opType The operation type
      * @return The updated driver operation
      */
    def op_type(opType: String): this.type = self.withModifier(this.getModifier(opType))
  }
  /** (modifier - see method for details) */
  trait Parent extends Modifier { self: BaseDriverOp =>
    /** A child document can be indexed by specifying its parent when indexing
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#parent-children Docs]]
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
      * @param b Whether to include the _source field of the document in the return
      * @return The updated driver operation
      */
    def _source(b: Boolean): this.type = self.withModifier(this.getModifier(b))
  }
  /** (modifier - see method for details) */
  trait SourceInclude extends Modifier { self: BaseDriverOp =>
    /** Which fields from the _source object to include
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering Docs]]
      * @param fields Which fields from the _source object to include
      * @return The updated driver operation
      */
    def _source_include(fields: String*): this.type = self.withModifier(this.getModifier(fields))
    /** Which fields from the _source object to include
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering Docs]]
      * @param fields Which fields from the _source object to include
      * @return The updated driver operation
      */
    def _source(fields: String*): this.type = self.withModifier(this.getModifier(fields))
  }
  /** (modifier - see method for details) */
  trait SourceExclude extends Modifier { self: BaseDriverOp =>
    /** Which fields from the _source object to exclude
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering Docs]]
      * @param fields Which fields from the _source object to include
      * @return The updated driver operation
      */
    def _source_exclude(fields: String*): this.type = self.withModifier(this.getModifier(fields))
  }
  /** (modifier - see method for details) */
  trait Fields extends Modifier { self: BaseDriverOp =>
    /** Which fields from the document index to include
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#get-fields Docs]]
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
      * @param op If set to "proceed" then doesn't abort on version conflicts
      * @return The updated driver operation
      */
    def conflict(op: String): this.type = self.withModifier(this.getModifier(op))
  }

}

/**
  * Common groupings of modifiers
  */
object CommonModifierGroups {

  //TODO: support common modifiers from here:
  //https://www.elastic.co/guide/en/elasticsearch/reference/current/common-options.html

  import CommonModifiers._

  /** No modifiers are supported for these parameters */
  trait NoParams extends BaseDriverOp

  /** The standard set available to all ES resource */
  trait StandardParams extends Pretty with BaseDriverOp

  /** Parameters controlling index operations */
  trait IndexWriteParams extends Version with OpType with Routing with Parent with Timeout with StandardParams

  /** Parameters controlling delete operations */
  trait IndexDeleteParams extends OpType with Routing with Parent with Timeout with StandardParams

  /** Parameters controlling the data returned from document access */
  trait DocumentReadParams extends SourceBase with SourceInclude with SourceExclude with Fields with StandardParams

  //TODO: this has changed in 2.4
  /** Parameters controlling the data returned from document access */
  trait UpdateByQueryParams extends Conflict with StandardParams

  /** Standard + routing parameters */
  trait RoutingStandardParams extends Routing with StandardParams

  /** Standard + field parameters */
  trait FieldStandardParams extends Fields with StandardParams
}
