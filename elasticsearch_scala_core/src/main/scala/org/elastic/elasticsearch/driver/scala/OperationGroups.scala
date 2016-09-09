package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.ModifierGroups._
import org.elastic.rest.scala.driver.RestBase
import org.elastic.rest.scala.driver.RestBase._

/**
  * The different read/write/delete operations mixed with the
  * modifier groups that define what actions can be taken on
  * the Elasticsearch resource DSL
  */
object OperationGroups {

  // Readable

  /**
    * A readable that only allows the standard `?pretty` and `?human` modifiers
    */
  trait SimpleReadable extends EsReadable[PrettyDriverOp] { self: RestResource =>
    override def read() = PrettyDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable that allows the standard and `?verbose` modifiers
    */
  trait VerboseSimpleReadable extends EsReadable[PrettyAndVerboseDriverOp] { self: RestResource =>
    override def read() = PrettyAndVerboseDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable that allows the standard and `?routing` modifiers
    */
  trait RoutableSimpleReadable extends EsReadable[PrettyAndRoutingDriverOp] { self: RestResource =>
    override def read() = PrettyAndRoutingDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable that allows the standard and `?routing` modifiers
    */
  trait FieldsSimpleReadable extends EsReadable[PrettyAndFieldsDriverOp] { self: RestResource =>
    override def read() = PrettyAndFieldsDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable that allows many modifiers
    */
  trait FullyModifiableReadable extends EsReadable[FullyModifiableReadDriverOp] { self: RestResource =>
    override def read() = FullyModifiableReadDriverOp(self, RestBase.GET, None, List(), List())
  }

  /**
    * A readable for a search shards query
    */
  trait QuerySearchShardsReadable extends EsReadable[QuerySearchShardsDriverOp] { self: RestResource =>
    override def read() = QuerySearchShardsDriverOp(self, RestBase.GET, None, List(), List())
  }

  /**
    * A readable for "URI-like" queries
    */
  trait QueryUriReadable extends EsReadable[QueryUriDriverOp] { self: RestResource =>
    override def read() = QueryUriDriverOp(self, RestBase.GET, None, List(), List())
  }

  /**
    * A readable for "URI-like" counting queries
    */
  trait QueryCountUriReadable extends EsReadable[QueryCountDriverOp] { self: RestResource =>
    override def read() = QueryCountDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable for "URI-like" other query types
    */
  trait QueryMiscUriReadable extends EsReadable[QueryMiscDriverOp] { self: RestResource =>
    override def read() = QueryMiscDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable for explain "URI-like" queries
    */
  trait QueryExplainUriReadable extends EsReadable[QueryExplainDriverOp] { self: RestResource =>
    override def read() = QueryExplainDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable for search field stats ("URI-like")
    */
  trait SearchFieldStatsReadable extends EsReadable[SearchFieldStatsDriverOp] { self: RestResource =>
    override def read() = SearchFieldStatsDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable for index statistics
    */
  trait IndexStatsReadable extends EsReadable[IndexStatsDriverOp] { self: RestResource =>
    override def read() = IndexStatsDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable for index recovery resources
    */
  trait IndexRecoveryReadable extends EsReadable[IndexRecoveryDriverOp] { self: RestResource =>
    override def read() = IndexRecoveryDriverOp(self, RestBase.GET, None, List(), List())
  }
  /**
    * A readable for retrieving information about shards
    */
  trait ShardStoreReadable extends EsReadable[ShardStoreDriverOp] { self: RestResource =>
    override def read() = ShardStoreDriverOp(self, RestBase.GET, None, List(), List())
  }

  // Readable with data

  /**
    * A readable with data that only allows the standard `?pretty` and `?human` modifiers
    */
  trait SimpleWithDataReadable extends WithDataEsReadable[PrettyDriverOp] { self: RestResource =>
    override def read(body: String) = PrettyDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable that allows the standard and `?routing` modifiers
    */
  trait RoutableSimpleWithDataReadable extends WithDataEsReadable[PrettyAndRoutingDriverOp] { self: RestResource =>
    override def read(body: String) = PrettyAndRoutingDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable with data that allows many modifiers
    */
  trait FullyModifiableWithDataReadable extends WithDataEsReadable[FullyModifiableReadDriverOp] { self: RestResource =>
    override def read(body: String) = FullyModifiableReadDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable for standard "with data" queries
    */
  trait QueryWithDataReadable extends WithDataEsReadable[QueryDriverOp] { self: RestResource =>
    override def read(body: String) = QueryDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable for standard "with data" counting queries
    */
  trait QueryCountWithDataReadable extends WithDataEsReadable[QueryCountDriverOp] { self: RestResource =>
    override def read(body: String) = QueryCountDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable for standard "with data", other query types
    */
  trait QueryMiscWithDataReadable extends WithDataEsReadable[QueryMiscDriverOp] { self: RestResource =>
    override def read(body: String) = QueryMiscDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable for standard "with data", explain query type
    */
  trait QueryExplainWithDataReadable extends WithDataEsReadable[QueryExplainDriverOp] { self: RestResource =>
    override def read(body: String) = QueryExplainDriverOp(self, RestBase.GET, Some(body), List(), List())
  }
  /**
    * A readable for search field stats (send the fields in the JSON body)
    */
  trait SearchFieldStatsWithDataReadable extends WithDataEsReadable[SearchFieldStatsDriverOp] { self: RestResource =>
    override def read(body: String) = SearchFieldStatsDriverOp(self, RestBase.GET, Some(body), List(), List())
  }

  // Writable

  /**
    * A writable that only allows the standard `?pretty` and `?human` modifiers
    */
  trait SimpleWritable extends EsWritable[PrettyDriverOp] { self: RestResource =>
    override def write(body: String) = PrettyDriverOp(self, RestBase.PUT, Some(body), List(), List())
  }
  /**
    * A writable that allows the standard and `?conflict` modifiers
    */
  trait ConflictSimpleWritable extends EsWritable[PrettyAndConflictDriverOp] { self: RestResource =>
    override def write(body: String) = PrettyAndConflictDriverOp(self, RestBase.PUT, Some(body), List(), List())
  }
  /**
    * A writable that allows many modifiers
    */
  trait FullyModifiableWritable extends EsWritable[FullyModifiableReadDriverOp] { self: RestResource =>
    override def write(body: String) = FullyModifiableReadDriverOp(self, RestBase.PUT, Some(body), List(), List())
  }

  // Writable with no data

  /**
    * A writable with no data and only the standard `?pretty` and `?human` modifiers
    */
  trait SimpleNoDataWritable { self: RestResource =>
    def write() = PrettyDriverOp(self, RestBase.POST, None, List(), List())
  }

  /**
    * A writable with no data and only the standard modifiers
    */
  trait OpenCloseIndexesNoDataWritable { self: RestResource =>
    def write() = OpenCloseIndexesDriverOp(self, RestBase.POST, None, List(), List())
  }

  /**
    * A writable with no data and only the standard modifiers
    */
  trait FlushNoDataWritable { self: RestResource =>
    def write() = FlushDriverOp(self, RestBase.POST, None, List(), List())
  }

  /**
    * A writable with no data and only the standard modifiers
    */
  trait ForceMergeNoDataWritable { self: RestResource =>
    def write() = ForceMergeDriverOp(self, RestBase.POST, None, List(), List())
  }

  /**
    * A writable with no data and only the standard modifiers
    */
  trait UpgradeNoDataWritable { self: RestResource =>
    def write() = UpgradeDriverOp(self, RestBase.POST, None, List(), List())
  }

  // Deletable

  /**
    * A deletable that only allows the standard `?pretty` and `?human` modifiers
    */
  trait SimpleDeletable extends EsDeletable[PrettyDriverOp] { self: RestResource =>
    /**
      * Creates a driver operation
      *
      * @return The driver opertion
      */
    override def delete() = PrettyDriverOp(self, RestBase.DELETE, None, List(), List())
  }
  /**
    * A deletable that allows many modifiers
    */
  trait FullyModifiableDeletable extends EsDeletable[FullyModifiableDeleteDriverOp] { self: RestResource =>
    override def delete() = FullyModifiableDeleteDriverOp(self, RestBase.DELETE, None, List(), List())
  }

  // Deletable with data

  /**
    * A deletable with data that only allows the standard `?pretty` and `?human` modifiers
    */
  trait SimpleWithDataDeletable extends WithDataEsDeletable[PrettyDriverOp] { self: RestResource =>
    override def delete(body: String) = PrettyDriverOp(self, RestBase.DELETE, Some(body), List(), List())
  }
  /**
    * A deletable with data that allows many modifiers
    */
  trait FullyModifiableWithDataDeletable extends WithDataEsDeletable[FullyModifiableDeleteDriverOp] { self: RestResource =>
    override def delete(body: String) = FullyModifiableDeleteDriverOp(self, RestBase.DELETE, Some(body), List(), List())
  }

  // Misc

  /**
    * A completely generic URL resource
    *
    * @param rawResource The URL (minus the leading /)
    */
  class RawOperatableResource(rawResource: String)
    {
//    override def location = rawResource
  }

  /**
    * Allows a simple check (HEAD) on a resource
    */
  trait SimpleCheckable { self: RestResource =>
    def check() = PrettyDriverOp(self, HEAD, None, List(), List())
  }
}
