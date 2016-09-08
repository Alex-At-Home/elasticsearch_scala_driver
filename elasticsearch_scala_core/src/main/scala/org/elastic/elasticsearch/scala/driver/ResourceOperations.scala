package org.elastic.elasticsearch.scala.driver

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._
import org.elastic.elasticsearch.scala.driver.utils.MacroUtils

import scala.language.experimental.macros

/**
  * Contains the base operations that, associated with the modifiers, can be executed against the
  * ES resources
  */
object ResourceOperations {

  //TODO: add the typed and untyped operations
  //TODO: add a set of Update operations (corresponds to POST)

  // Checking

  /** The base check (HEAD) resource (untyped)
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

  /** The base typed check (HEAD) resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam O The type (case class) of the return operation
    */
  trait EsCheckableT[D <: BaseDriverOp, O] extends EsCheckable[D] { self: EsResource =>
    //TODO: test case
    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    @MacroUtils.OpType("HEAD")
    override def check(): D = macro MacroUtils.materializeOpImpl_TypedOutput[D, O]
  }

  // Readable

  /** The base readable resource (untyped)
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

  /** The base typed readable resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam O The type (case class) of the return operation
    */
  trait EsReadableT[D <: BaseDriverOp, O] extends EsReadable[D] { self: EsResource =>
    /**
      * Creates a typed driver operation
      *
      * @return The typed driver operation
      */
    @MacroUtils.OpType("GET")
    override def read(): D with TypedOperation[O] = macro MacroUtils.materializeOpImpl_TypedOutput[D, O]
  }

  // Readable with Data

  /** The base (untyped) readable resource where the reply is controlled by data written to the resource
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

  /** The base readable resource where the reply is controlled by data written to the resource
    * (input untyped, output typed)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam O The type (case class) of the return operation
    */
  trait EsWithDataReadableUT[D <: BaseDriverOp, O] extends EsWithDataReadable[D] {
    //TODO: test case
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    override def read(body: String): D with TypedOperation[O] =
    macro MacroUtils.materializeOpImpl_Body_TypedOutput[D, O]

    /**
      * Creates a driver operation
      *
      * @param body The JSON data to write to the resource
      * @param jsonToStringHelper The implicit per-JSON-lib to generate
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    override def read[J](body: J)(implicit jsonToStringHelper: JsonToStringHelper[J]): D with TypedOperation[O] =
    macro MacroUtils.materializeOpImpl_JBody_TypedOutput[D, J, O]
  }

  /** The base (untyped) readable resource where the reply is controlled by data written to the resource
    * (input typed, output untyped)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam I The type of the input object
    */
  trait EsWithDataReadableTU[D <: BaseDriverOp, I] extends EsWithDataReadable[D] {
    //TODO: test case
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    def read(body: I)(implicit typeToStringHelper: TypedToStringHelper): D =
    macro MacroUtils.materializeOpImpl_CBody[D, I]
  }

  /** The base (untyped) readable resource where the reply is controlled by data written to the resource
    * (input typed, output typed)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam I The type of the input object
    * @tparam O The type of the output object
    */
  trait EsWithDataReadableTT[D <: BaseDriverOp, I, O] extends EsWithDataReadableUT[D, O] {
    //TODO: test case
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    def read(body: I)(implicit typeToStringHelper: TypedToStringHelper): D with TypedOperation[O] =
    macro MacroUtils.materializeOpImpl_CBody_TypedOutput[D, I, O]

    /**
      * Creates a driver operation
      *
      * @param body The JSON data to write to the resource
      * @param jsonToStringHelper The implicit per-JSON-lib to generate
      * @return The driver operation
      */
    @MacroUtils.OpType("GET")
    override def read[J](body: J)(implicit jsonToStringHelper: JsonToStringHelper[J]): D with TypedOperation[O] =
    macro MacroUtils.materializeOpImpl_JBody[D, J]
  }

  // Writable

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

  /** The base writable resource (typed input, untyped output)
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    * @tparam I The type of the input object
    */
  trait EsWritableTU[D <: BaseDriverOp, I] extends EsWritable[D] {
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

  // No date writable

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

  // Deletable

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

  // With Data Deletable

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
