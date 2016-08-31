package org.elastic.elasticsearch.scala.driver

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsResource}
import scala.language.experimental.macros
import scala.reflect.macros.Context

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
    private def opImpl(_resource: EsResource, _op: String, _body: Option[String], _mods: List[String]): D =
      macro MacroOperatable.materializeOpImpl[D]

    /**
      * Creates a driver operation
      *
      * @return The driver opertion
      */
    //def read(): D = opImpl(self, ElasticsearchBase.GET, None, List())
  }

  /**
    * The base readable type where the reply is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsReadable[D <: BaseDriverOp] {
    private def opImpl(_resource: EsResource, _op: String, _body: Option[String], _mods: List[String]): D =
      macro MacroOperatable.materializeOpImpl[D]

    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def read(body: String): D
  }

  /**
    * The base writable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritable[D <: BaseDriverOp] {
    private def opImpl(_resource: EsResource, _op: String, _body: Option[String], _mods: List[String]): D =
      macro MacroOperatable.materializeOpImpl[D]

    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def write(body: String): D
  }

  /**
    * The base deletable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsDeletable[D <: BaseDriverOp] {
    private def opImpl(_resource: EsResource, _op: String, _body: Option[String], _mods: List[String]): D =
      macro MacroOperatable.materializeOpImpl[D]

    /**
      * Creates a driver operation
      *
      * @return The driver operation
      */
    def delete(): D
  }

  /**
    * The base deletable type where the delete is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsDeletable[D <: BaseDriverOp] {
    private def opImpl(_resource: EsResource, _op: String, _body: Option[String], _mods: List[String]): D =
      macro MacroOperatable.materializeOpImpl[D]

    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver operation
      */
    def delete(body: String): D
  }

  trait MacroOperatable[T <: BaseDriverOp] {
    def opImpl(_resource: EsResource, _op: String, _body: Option[String], _mods: List[String]): T =
    macro MacroOperatable.materializeOpImpl[T]
  }
  object MacroOperatable {
    def materializeOpImpl[T <: BaseDriverOp]
    (c: Context)
    (_resource: c.Expr[EsResource], _op: c.Expr[String], _body: c.Expr[Option[String]], _mods: c.Expr[List[String]])
    (implicit T: c.WeakTypeTag[T])
    : c.Expr[T] =
    {
      import c.universe._
      c.Expr[T] {
        q"""
            case class Internal(resource: EsResource, op: String, body: Option[String], mods: List[String]) extends $T {
              def withModifier(m: String): this.type = Internal(resource, op, body, m :: mods).asInstanceOf[this.type]
            }
            Internal(resource, op, body, mods)
      """ }
    }
  }


}
