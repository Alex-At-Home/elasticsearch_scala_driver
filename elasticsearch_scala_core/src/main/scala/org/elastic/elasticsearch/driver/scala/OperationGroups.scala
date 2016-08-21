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
  trait RawReadable extends EsReadable[RawDriverOp] { self: EsResource =>
    override def read() = RawDriverOp(self, GET, None, List())
  }
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

  // Readable with data

  /**
    * A readable with data that only allows raw modifiers
    */
  trait RawWithDataReadable extends WithDataEsReadable[RawDriverOp] { self: EsResource =>
    override def read(body: String) = RawDriverOp(self, GET, Some(body), List())
  }
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

  // Writable

  /**
    * A writable that only allows generic modifiers
    */
  trait RawWritable extends EsWritable[RawDriverOp] { self: EsResource =>
    override def write(body: String) = RawDriverOp(self, PUT, Some(body), List())
  }
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

  // Deletable

  /**
    * A deletable that only allows raw modifiers
    */
  trait RawDeletable extends EsDeletable[RawDriverOp] { self: EsResource =>
    /**
      * Creates a driver operation
      * @return The driver opertion
      */
    override def delete() = RawDriverOp(self, DELETE, None, List())
  }
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
    * A deletable with data that only allows generic modifiers
    */
  trait RawWithDataDeletable extends WithDataEsDeletable[RawDriverOp] { self: EsResource =>
    override def delete(body: String) = RawDriverOp(self, DELETE, Some(body), List())
  }
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
    extends RawReadable with RawWithDataReadable
      with RawWritable
      with RawDeletable with RawWithDataDeletable
  with EsResource {
    override def location = rawResource
  }

}
