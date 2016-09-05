package org.elastic.elasticsearch.scala.driver

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._
import org.elastic.elasticsearch.scala.driver.utils.MacroUtils

import scala.language.experimental.macros

/**
  * Contains the base operations that, associated with the modifiers, can be executed against the
  * ES resources
  */
object ResourceOperations {

  /** The base typed readable operation
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam I The type (case class) of the return operation
    */
  trait EsReadableT[D <: BaseDriverOp, I] extends EsReadable[D] { self: EsResource =>
    //TODO get working, test, and add to other resources
    /**
      * Creates a typed driver operation
      *
      * @return The typed driver operation
      */
    @MacroUtils.OpType("GET")
    override def read(): D with TypedOperation[I] = macro MacroUtils.materializeOpImpl_TypedInput[D, I]
  }

  /** The base readable type (untyped)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsReadable[D <: BaseDriverOp] { self: EsResource =>
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    def read(): D = macro MacroUtils.materializeOpImpl[D]
  }

  /** The base check (HEAD) type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsCheckable[D <: BaseDriverOp] { self: EsResource =>
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType("HEAD")
    def check(): D = macro MacroUtils.materializeOpImpl[D]
  }

  /** The base readable type where the reply is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWithDataReadable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    def read(body: String): D = macro MacroUtils.materializeOpImpl_Body[D]

    /**
      * Creates a driver operation
      *
      * @param body The JSON data to write to the resource
      * @param jsonToStringHelper The implicit per-JSON-lib to generate
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    def read[J](body: J)(implicit jsonToStringHelper: JsonToStringHelper[J]): D =
      macro MacroUtils.materializeOpImpl_JBody[D, J]
  }

  /** The base writable resource (typed input, untyped output)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritableTU[D <: BaseDriverOp, I] extends EsWritable[D] {
    //TODO add to other resources
    /**
      * Creates a driver operation
      *
      * @param body The typed data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("PUT")
    def write(body: I)(implicit typeToStringHelper: TypedToStringHelper): D =
      macro MacroUtils.materializeOpImpl_CBody[D, I]
  }
  /** The base writable resource (untyped in both input and output)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The String data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("PUT")
    def write(body: String): D = macro MacroUtils.materializeOpImpl_Body[D]

    /**
      * Creates a driver operation
      *
      * @param body The JSON data to write to the resource
      * @param jsonToStringHelper The implicit per-JSON-lib to generate
      * @return The driver operation
      */
    @MacroUtils.OpType("PUT")
    def write[J](body: J)(implicit jsonToStringHelper: JsonToStringHelper[J]): D =
      macro MacroUtils.materializeOpImpl_JBody[D, J]
  }

  /** The base writable type, for cases where no data is written
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsNoDataWritable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType("PUT")
    def write(): D = macro MacroUtils.materializeOpImpl[D]
  }

  /** The base deletable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsDeletable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType("DELETE")
    def delete(): D = macro MacroUtils.materializeOpImpl[D]
  }

  /** The base deletable type where the delete is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWithDataDeletable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("DELETE")
    def delete(body: String): D = macro MacroUtils.materializeOpImpl_Body[D]

    /**
      * Creates a driver operation
      *
      * @param body The JSON data to write to the resource
      * @param jsonToStringHelper The implicit per-JSON-lib to generate
      * @return The driver operation
      */
    @MacroUtils.OpType("DELETE")
    def delete[J](body: J)(implicit jsonToStringHelper: JsonToStringHelper[J]): D =
      macro MacroUtils.materializeOpImpl_JBody[D, J]
  }
}
