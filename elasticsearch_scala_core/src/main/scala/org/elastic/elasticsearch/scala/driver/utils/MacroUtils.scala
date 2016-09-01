package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsResource}

import scala.annotation.StaticAnnotation
import scala.reflect.macros._

/**
  * Contains scala macros
  */
object MacroUtils {

  class OpType(op: String) extends StaticAnnotation
  class Body(body: String) extends StaticAnnotation

  /**
    * The Macro implementation, allows for modifiers to be chained
    * Without this, needed two extra case classes for each combination of modifiers
    * (one extra class - the first case class can be replaced with a much simpler list
    *
    * @param c The macro context
    * @param T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl[T <: BaseDriverOp]
  (c: Context)()
  (implicit T: c.WeakTypeTag[T])
  : c.Expr[T] =
  {
    import c.universe._

    // Parameters:
    // (from: http://stackoverflow.com/questions/25127140/how-can-parameters-settings-be-passed-to-a-scala-macro/25219644#25219644)
    val opType = c.macroApplication.symbol.annotations.find(
      _.tree.tpe <:< typeOf[OpType]
    ).flatMap(
      _.tree.children.tail.collectFirst {
        case Literal(Constant(s: String)) => s
      }
    ).getOrElse(
      ElasticsearchBase.GET
    )
    val self = c.prefix

    c.Expr[T] {
      q"""
            case class Internal(resource: EsResource, op: String, body: Option[String], mods: List[String]) extends $T {
              def withModifier(m: String): this.type = Internal(resource, op, body, m :: mods).asInstanceOf[this.type]
            }
            Internal($self, $opType, None, List())
      """ }
  }

  /**
    * The Macro implementation, allows for modifiers to be chained
    * Without this, needed two extra case classes for each combination of modifiers
    * (one extra class - the first case class can be replaced with a much simpler list
    *
    * @param c The macro context
    * @param body The body to write to the resource (or None for pure reads)
    * @param T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl_Body[T <: BaseDriverOp]
  (c: Context)(body: c.Expr[String])
  (implicit T: c.WeakTypeTag[T])
  : c.Expr[T] =
  {
    import c.universe._

    // Parameters:
    // (from: http://stackoverflow.com/questions/25127140/how-can-parameters-settings-be-passed-to-a-scala-macro/25219644#25219644)
    val opType = c.macroApplication.symbol.annotations.find(
      _.tree.tpe <:< typeOf[OpType]
    ).flatMap(
      _.tree.children.tail.collectFirst {
        case Literal(Constant(s: String)) => s
      }
    ).getOrElse(
      ElasticsearchBase.GET
    )
    val self = c.prefix

    c.Expr[T] {
      q"""
            case class Internal(resource: EsResource, op: String, body: Option[String], mods: List[String]) extends $T {
              def withModifier(m: String): this.type = Internal(resource, op, body, m :: mods).asInstanceOf[this.type]
            }
            Internal($self, $opType, Some($body), List())
      """ }
  }

  /**
    * The Macro implementation, allows for modifiers to be chained
    * Without this, needed two extra case classes for each combination of modifiers
    * (one extra class - the first case class can be replaced with a much simpler list
    *
    * This version includes all the parameters explicitly (means it can't be called
    *
    * @param c The macro context
    * @param resource The resource on which to apply the operation
    * @param op The operation from `ElasticsearchBase` (`GET`, `HEAD`, etc)
    * @param body The body to write to the resource (or None for pure reads)
    * @param mods The list of modifiers to apply
    * @param T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl_AllParams[T <: BaseDriverOp]
  (c: Context)
  (resource: c.Expr[EsResource], op: c.Expr[String], body: c.Expr[Option[String]], mods: c.Expr[List[String]])
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
