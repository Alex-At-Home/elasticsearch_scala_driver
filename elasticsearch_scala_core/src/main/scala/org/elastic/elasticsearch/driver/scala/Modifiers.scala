package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.ElasticsearchBase._

/**
  * A list of modifiers for the various Elasticsearch resources
  */
object Modifiers {

  // Generic modifiers, used in lots of places

  sealed trait Modifier
  /**
    * Represents a resource that can have the modifier ?pretty
    * (Controls the format of the response if returned as a string, else ignored)
    */
  trait Pretty extends Modifier { self: BaseDriverOp =>
    /**
      * Returns a resource with modifier ?pretty
      * (Controls the format of the response if returned as a string, else ignored)
      * @param b The prettiness
      * @return The updated driver operation
      */
    def `?pretty=`(b: Boolean) = self.withModifier(s"?pretty=$b")

    /**
      * Returns a resource with modifier ?pretty
      * (Controls the format of the response if returned as a string, else ignored)
      * @return The updated driver operation
      */
    def `?pretty` = `?pretty=`(true)
  }
  /**
    * Represents a resource that can have the modifier ?routing
    * (Forces the request to go to a specific node in the cluster)
    */
  trait Routing extends Modifier { self: BaseDriverOp =>
    /**
      * Represents a resource that can have the modifier ?routing
      * (Forces the request to go to a specific node in the cluster)
      * @param node The node to which to restrict the request
      * @return The updated driver operation
      */
    def `?routing=`(node: String) = self.withModifier(s"?routing=$node")
  }

  /**
    * TODO
    */
  trait Version extends Modifier { self: BaseDriverOp =>
    def `?version=`(v: Int) = self.withModifier(s"?version=$v")
  }
  /**
    * TODO
    */
  trait OpType extends Modifier { self: BaseDriverOp =>
    def `?op_type=`(opType: String) = self.withModifier(s"?op_type=$opType")
  }
  /**
    * TODO
    */
  trait Parent extends Modifier { self: BaseDriverOp =>
    def `?parent=`(parent: String) = self.withModifier(s"?parent=$parent")
  }
  /**
    * TODO
    */
  trait Timeout extends Modifier { self: BaseDriverOp =>
    def `?timeout=`(timeout: String) = self.withModifier(s"?timeout=$timeout")
  }
  /**
    * TODO
    */
  trait Source extends Modifier { self: BaseDriverOp =>
    def `?source=`(b: Boolean) = self.withModifier(s"?source=$b")
  }
  /**
    * TODO
    */
  trait SourceInclude extends Modifier { self: BaseDriverOp =>
    def `?source_include=`(fields: String*) = self.withModifier(s"?source_include=${fields.mkString(",")}")
  }
  /**
    * TODO
    */
  trait SourceExclude extends Modifier { self: BaseDriverOp =>
    def `?source_exclude=`(fields: String*) = self.withModifier(s"?source_exclude=${fields.mkString(",")}")
  }
  /**
    * TODO
    */
  trait Fields extends Modifier { self: BaseDriverOp =>
    def `?fields=`(fields: String*) = self.withModifier(s"?fields=${fields.mkString(",")}")
  }
  /**
    * TODO
    */
  trait Refresh extends Modifier { self: BaseDriverOp =>
    def `?refresh=`(b: Boolean) = self.withModifier(s"?refresh=$b")
    def `?refresh` = `?refresh=`(true)
  }

  // Search modifiers - URI version

  /**
    * The query string (maps to the query_string query)
    */
  trait Query extends Modifier { self: BaseDriverOp =>
    /**
      * The query string (maps to the query_string query)
      * @param query The query string (maps to the query_string query)
      */
    def `?q=`(query: String) = self.withModifier(s"?q=$query}")
  }

  /**
    * The default field to use when no field prefix is defined within the query.
    */
  trait DefaultField extends Modifier { self: BaseDriverOp =>
    /**
      * The default field to use when no field prefix is defined within the query.
      * @param defaultField The default field to use when no field prefix is defined within the query
      */
    def `?df=`(defaultField: String) = self.withModifier(s"?df=$defaultField")
  }


  // Search modifiers - "with data" version

  // Misc other modifiers

  /**
    * TODO
    */
  trait Conflict extends Modifier { self: BaseDriverOp =>
    def `?conflict=`(op: String) = self.withModifier(s"?conflict=$op")
  }

}
