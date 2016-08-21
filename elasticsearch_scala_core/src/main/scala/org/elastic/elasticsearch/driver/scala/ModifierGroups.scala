package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.ElasticsearchBase.{BaseDriverOp, EsResource}
import org.elastic.elasticsearch.driver.scala.Modifiers._

/**
  * Groups of modifiers used in the Elasticsearch DSL
  */
object ModifierGroups {

  /**
    * A driver op with no typed modifiers
    * @param resource The ES resource
    * @param op The operation to apply
    * @param body The body to apply, for operations with data
    * @param mods The current list of modifiers to apply to the operation
    */
  case class RawDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndRoutingDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty with Routing
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndFieldsDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty with Fields
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndConflictDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty with Conflict
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FullyModifiableReadDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty with Source with SourceInclude with SourceExclude with Fields
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FullyModifiableWriteDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty with Version with OpType with Routing with Parent with Timeout
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FullyModifiableDeleteDriverOp
  (resource: EsResource, op: String, body: Option[String], mods: List[String])
    extends BaseDriverOp
      with Pretty with Routing with Parent with Refresh with Timeout
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods)
  }

}
