package org.elastic.elasticsearch.scala.driver

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsResource}
import org.elastic.elasticsearch.scala.driver.utils.MacroUtils

import scala.language.experimental.macros

/**
  * Contains the base operations that, associated with the modifiers, can be executed against the
  * ES resources
  */
object ResourceOperations {

  /**
    * The base readable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsReadable[D <: BaseDriverOp] { self: EsResource =>
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType(ElasticsearchBase.GET)
    def read(): D = macro MacroUtils.materializeOpImpl[D]
  }

  /**
    * The base readable type where the reply is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsReadable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType(ElasticsearchBase.GET)
    def read(body: String): D = macro MacroUtils.materializeOpImpl_Body[D]
  }

  /**
    * The base writable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType(ElasticsearchBase.PUT)
    def write(body: String): D = macro MacroUtils.materializeOpImpl_Body[D]
  }

  /**
    * The base deletable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsDeletable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType(ElasticsearchBase.DELETE)
    def delete(): D = macro MacroUtils.materializeOpImpl[D]
  }

  /**
    * The base deletable type where the delete is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsDeletable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType(ElasticsearchBase.DELETE)
    def delete(body: String): D = macro MacroUtils.materializeOpImpl_Body[D]
  }
}
