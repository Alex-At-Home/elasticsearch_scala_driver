package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.BaseDriverOp

import scala.annotation.StaticAnnotation
import scala.reflect.macros._

/**
  * Contains scala macros
  */
object MacroUtils {

  class OpType(op: String) extends StaticAnnotation
  class Body(body: String) extends StaticAnnotation

  private def buildInternalClass[T]
    (c: whitebox.Context)
    (self: c.Expr[c.PrefixType], opType: String, body: c.Expr[Option[String]],
     modifiers: List[String], headers: List[String],
     ct: c.WeakTypeTag[T]) =
  {
    import c.universe._
    q"""
      case class Internal
      (resource: EsResource, op: String, body: Option[String], mods: List[String], headers: List[String])
        extends $ct
      {
        override def withModifier(m: String): this.type = Internal(resource, op, body, m :: mods, headers)
          .asInstanceOf[this.type]
        override def withHeader(h: String): this.type = Internal(resource, op, body, mods, h :: headers)
          .asInstanceOf[this.type]
      }
      Internal($self, $opType, $body, $modifiers, $headers)
    """
  }

  /**
    * The Macro implementation, allows for modifiers to be chained
    * Without this, needed two extra case classes for each combination of modifiers
    * (one extra class - the first case class can be replaced with a much simpler list
    *
    * @param c The macro context
    * @param ct The type evidence (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl[T <: BaseDriverOp]
  (c: whitebox.Context)()
  (implicit ct: c.WeakTypeTag[T])
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
      buildInternalClass[T](c)(self, opType, reify { None }, List(), List(), ct).asInstanceOf[c.Tree]
    }
  }

  /**
    * The Macro implementation, allows for modifiers to be chained
    * Without this, needed two extra case classes for each combination of modifiers
    * (one extra class - the first case class can be replaced with a much simpler list
    *
    * @param c The macro context
    * @param body The body to write to the resource (or None for pure reads)
    * @param ct The type evidence (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl_Body[T <: BaseDriverOp]
  (c: whitebox.Context)(body: c.Expr[String])
  (implicit ct: c.WeakTypeTag[T])
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
    val maybeBody = reify { Option(body.splice) }

    c.Expr[T] {
      buildInternalClass[T](c)(self, opType, maybeBody, List(), List(), ct)
        .asInstanceOf[c.Tree]
    }
  }
}
