package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.ElasticsearchBase._
import org.elastic.elasticsearch.driver.scala.ModifierGroups._

/**
  * The different read/write/delete operations mixed with the
  * modifier groups that define what actions can be taken on
  * the Elasticsearch resource DSL
  */
object OperationGroups {

  // Readable

  /**
    * A readable that only allows the `?pretty` modifier
    */
  trait SimpleReadable extends EsReadable[PrettyDriverOp] { self: EsResource =>
    override def read() = PrettyDriverOp(self, GET, None, List())
  }
  /**
    * A readable that allows the `?pretty` and `?routing` modifier
    */
  trait RoutableSimpleReadable extends EsReadable[PrettyAndRoutingDriverOp] { self: EsResource =>
    override def read() = PrettyAndRoutingDriverOp(self, GET, None, List())
  }
  /**
    * A readable that allows the `?pretty` and `?routing` modifier
    */
  trait FieldsSimpleReadable extends EsReadable[PrettyAndFieldsDriverOp] { self: EsResource =>
    override def read() = PrettyAndFieldsDriverOp(self, GET, None, List())
  }
  /**
    * A readable that allows many modifiers
    */
  trait FullyModifiableReadable extends EsReadable[FullyModifiableReadDriverOp] { self: EsResource =>
    override def read() = FullyModifiableReadDriverOp(self, GET, None, List())
  }

  /**
    * A readable for a search shards query
    */
  trait QuerySearchShardsReadable extends EsReadable[QuerySearchShardsDriverOp] { self: EsResource =>
    override def read() = QuerySearchShardsDriverOp(self, GET, None, List())
  }

  /**
    * A readable for "URI-like" queries
    */
  trait QueryUriReadable extends EsReadable[QueryUriDriverOp] { self: EsResource =>
    override def read() = QueryUriDriverOp(self, GET, None, List())
  }

  /**
    * A readable for "URI-like" counting queries
    */
  trait QueryCountUriReadable extends EsReadable[QueryCountDriverOp] { self: EsResource =>
    override def read() = QueryCountDriverOp(self, GET, None, List())
  }
  /**
    * A readable for "URI-like" other query types
    */
  trait QueryMiscUriReadable extends EsReadable[QueryMiscDriverOp] { self: EsResource =>
    override def read() = QueryMiscDriverOp(self, GET, None, List())
  }
  /**
    * A readable for explain "URI-like" queries
    */
  trait QueryExplainUriReadable extends EsReadable[QueryExplainDriverOp] { self: EsResource =>
    override def read() = QueryExplainDriverOp(self, GET, None, List())
  }
  /**
    * A readable for search field stats ("URI-like")
    */
  trait SearchFieldStatsReadable extends EsReadable[SearchFieldStatsDriverOp] { self: EsResource =>
    override def read() = SearchFieldStatsDriverOp(self, GET, None, List())
  }

  // Readable with data

  /**
    * A readable with data that only allows the `?pretty` modifier
    */
  trait SimpleWithDataReadable extends WithDataEsReadable[PrettyDriverOp] { self: EsResource =>
    override def read(body: String) = PrettyDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable that allows the `?pretty` and `?routing` modifier
    */
  trait RoutableSimpleWithDataReadable extends WithDataEsReadable[PrettyAndRoutingDriverOp] { self: EsResource =>
    override def read(body: String) = PrettyAndRoutingDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable with data that allows many modifiers
    */
  trait FullyModifiableWithDataReadable extends WithDataEsReadable[FullyModifiableReadDriverOp] { self: EsResource =>
    override def read(body: String) = FullyModifiableReadDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable for standard "with data" queries
    */
  trait QueryWithDataReadable extends WithDataEsReadable[QueryDriverOp] { self: EsResource =>
    override def read(body: String) = QueryDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable for standard "with data" counting queries
    */
  trait QueryCountWithDataReadable extends WithDataEsReadable[QueryCountDriverOp] { self: EsResource =>
    override def read(body: String) = QueryCountDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable for standard "with data", other query types
    */
  trait QueryMiscWithDataReadable extends WithDataEsReadable[QueryMiscDriverOp] { self: EsResource =>
    override def read(body: String) = QueryMiscDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable for standard "with data", explain query type
    */
  trait QueryExplainWithDataReadable extends WithDataEsReadable[QueryExplainDriverOp] { self: EsResource =>
    override def read(body: String) = QueryExplainDriverOp(self, GET, Some(body), List())
  }
  /**
    * A readable for search field stats (send the fields in the JSON body)
    */
  trait SearchFieldStatsWithDataReadable extends WithDataEsReadable[SearchFieldStatsDriverOp] { self: EsResource =>
    override def read(body: String) = SearchFieldStatsDriverOp(self, GET, Some(body), List())
  }

  // Writable

  /**
    * A writable that only allows the `?pretty` modifier
    */
  trait SimpleWritable extends EsWritable[PrettyDriverOp] { self: EsResource =>
    override def write(body: String) = PrettyDriverOp(self, PUT, Some(body), List())
  }
  /**
    * A writable that allows the `?pretty` modifier and `?conflict` modifier
    */
  trait ConflictSimpleWritable extends EsWritable[PrettyAndConflictDriverOp] { self: EsResource =>
    override def write(body: String) = PrettyAndConflictDriverOp(self, PUT, Some(body), List())
  }
  /**
    * A writable that allows many modifiers
    */
  trait FullyModifiableWritable extends EsWritable[FullyModifiableReadDriverOp] { self: EsResource =>
    override def write(body: String) = FullyModifiableReadDriverOp(self, PUT, Some(body), List())
  }

  // Writable with no data

  /**
    * A writable with no data and only the ?pretty modifiers
    */
  trait SimpleNoDataWritable { self: EsResource =>
    def write() = PrettyDriverOp(self, POST, None, List())
  }

  /**
    * A writable with no data and only the ?pretty modifiers
    */
  trait OpenCloseIndexesNoDataWritable { self: EsResource =>
    def write() = OpenCloseIndexesDriverOp(self, POST, None, List())
  }

  // Deletable

  /**
    * A deletable that only allows the `?pretty` modifier
    */
  trait SimpleDeletable extends EsDeletable[PrettyDriverOp] { self: EsResource =>
    /**
      * Creates a driver operation
      * @return The driver opertion
      */
    override def delete() = PrettyDriverOp(self, DELETE, None, List())
  }
  /**
    * A deletable that allows many modifiers
    */
  trait FullyModifiableDeletable extends EsDeletable[FullyModifiableDeleteDriverOp] { self: EsResource =>
    override def delete() = FullyModifiableDeleteDriverOp(self, DELETE, None, List())
  }

  // Deletable with data

  /**
    * A deletable with data that only allows the `?pretty` modifier
    */
  trait SimpleWithDataDeletable extends WithDataEsDeletable[PrettyDriverOp] { self: EsResource =>
    override def delete(body: String) = PrettyDriverOp(self, DELETE, Some(body), List())
  }
  /**
    * A deletable with data that allows many modifiers
    */
  trait FullyModifiableWithDataDeletable extends WithDataEsDeletable[FullyModifiableDeleteDriverOp] { self: EsResource =>
    override def delete(body: String) = FullyModifiableDeleteDriverOp(self, DELETE, Some(body), List())
  }

  // Misc

  /**
    * A completely generic URL resource
    * @param rawResource The URL (minus the leading /)
    */
  class RawOperatableResource(rawResource: String)
    extends SimpleReadable with SimpleWithDataReadable
      with SimpleWritable with SimpleNoDataWritable
      with SimpleDeletable with SimpleWithDataDeletable
    with EsResource {
    override def location = rawResource
  }

  /**
    * Allows a simple check (HEAD) on a resource
    */
  trait SimpleCheckable { self: EsResource =>
    def write() = PrettyDriverOp(self, HEAD, None, List())
  }
}
