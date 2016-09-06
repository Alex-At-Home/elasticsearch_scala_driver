package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._

import scala.annotation.StaticAnnotation
import scala.reflect.macros._

/**
  * Contains scala macros
  */
object MacroUtils {

  /** OpType macro
    * (from: http://stackoverflow.com/questions/25127140/how-can-parameters-settings-be-passed-to-a-scala-macro/25219644#25219644)
    *
    * @param op The operation (has to be specified as a string literal)
    */
  class OpType(op: String) extends StaticAnnotation

  /** Get the operation type from the @OpType macro
    * (from: http://stackoverflow.com/questions/25127140/how-can-parameters-settings-be-passed-to-a-scala-macro/25219644#25219644)
    *
    * @param c The context of the macro operation
    * @return The operation type
    */
  def getOpType(c: blackbox.Context): String = {
    import c.universe._

    c.macroApplication.symbol.annotations.find(
      _.tree.tpe <:< typeOf[OpType]
    ).flatMap(
      _.tree.children.tail.collectFirst {
        case Literal(Constant(s: String)) => s
        case x @ _ => c.abort(
          c.enclosingPosition, s"OpType method needs to be string literal, not $x")
      }
    ).getOrElse(
      c.abort(c.enclosingPosition,
        "Failed to get method, did you specify the @OpType(method) annotation?" +
          s" annotations = ${c.macroApplication.symbol.annotations}")
    )
  }

  /** The actual code in quasi-codes for building the header/param modifiable case class
    * (untyped return)
    *
    * @param c The context of the macro operation
    * @param self The `EsResource`
    * @param opType The operation type
    * @param body The body to post
    * @param modifiers The parameters/modifiers
    * @param headers The headers
    * @param ctt The evidence for the trait containing the list of available modifiers
    * @tparam T The trait containing the list of available modifiers
    * @return The macro code to inject
    */
  private def buildInternalClass[T]
    (c: blackbox.Context)
    (self: c.Expr[c.PrefixType], opType: String, body: c.Expr[Option[String]],
     modifiers: List[String], headers: List[String],
     ctt: c.WeakTypeTag[T]) =
  {
    import c.universe._
    q"""
      case class Internal
      (resource: EsResource, op: String, body: Option[String], mods: List[String], headers: List[String])
        extends $ctt
      {
        override def withModifier(m: String): this.type = Internal(resource, op, body, m :: mods, headers)
          .asInstanceOf[this.type]
        override def withHeader(h: String): this.type = Internal(resource, op, body, mods, h :: headers)
          .asInstanceOf[this.type]
      }
      Internal($self, $opType, $body, $modifiers, $headers)
    """
  }

  /** The actual code in quasi-codes for building the header/param modifiable case class
    * (untyped return)
    *
    * @param c The context of the macro operation
    * @param self The `EsResource`
    * @param opType The operation type
    * @param body The body to post
    * @param modifiers The parameters/modifiers
    * @param headers The headers
    * @param ctt The evidence for the trait containing the list of available modifiers
    * @tparam T The trait containing the list of available modifiers
    * @return The macro code to inject
    */
  private def buildInternalClass[T, C]
  (c: blackbox.Context)
  (self: c.Expr[c.PrefixType], opType: String, body: c.Expr[Option[String]],
   modifiers: List[String], headers: List[String],
   ctt: c.WeakTypeTag[T], ctc: c.WeakTypeTag[C]) =
  {
    import c.universe._

    q"""
      case class Internal
      (resource: EsResource, op: String, body: Option[String], mods: List[String], headers: List[String])
        extends $ctt with TypedOperation[$ctc]
      {
        override val ct: scala.reflect.runtime.universe.WeakTypeTag[$ctc] =
          scala.reflect.runtime.universe.weakTypeTag[$ctc]

        override def withModifier(m: String): this.type = Internal(resource, op, body, m :: mods, headers)
          .asInstanceOf[this.type]
        override def withHeader(h: String): this.type = Internal(resource, op, body, mods, h :: headers)
          .asInstanceOf[this.type]

          //TODO: add typed exec
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
    (c: blackbox.Context)()
    (implicit ct: c.WeakTypeTag[T])
    : c.Expr[T] =
  {
    import c.universe._

    val opType = getOpType(c)
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
    * @param ctt The operation type evidence (combination of `Modifier` classes and `BaseDriverOp`)
    * @param ctc The return type evidence (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl_TypedInput[T <: BaseDriverOp, C]
  (c: blackbox.Context)()
  (implicit ctt: c.WeakTypeTag[T], ctc: c.WeakTypeTag[C])
  : c.Expr[T with TypedOperation[C]] =
  {
    import c.universe._

    val opType = getOpType(c)
    val self = c.prefix

    c.Expr[T with TypedOperation[C]] {
      buildInternalClass[T, C](c)(self, opType, reify { None }, List(), List(), ctt, ctc)
        .asInstanceOf[c.Tree]
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
  def materializeOpImpl_JBody[T <: BaseDriverOp, J]
    (c: blackbox.Context)(body: c.Expr[J])
    (jsonToStringHelper: c.Expr[JsonToStringHelper[J]])
    (implicit ct: c.WeakTypeTag[T])
    : c.Expr[T] =
  {
    import c.universe._

    val opType = getOpType(c)
    val self = c.prefix
    val maybeBody = reify { Option(jsonToStringHelper.splice.fromJson(body.splice)) }

    c.Expr[T] {
      buildInternalClass[T](c)(self, opType, maybeBody, List(), List(), ct)
        .asInstanceOf[c.Tree]
    }
  }

  /**
    * The Macro implementation, allows for modifiers to be chained
    * Without this, needed two extra case classes for each combination of modifiers
    * (one extra class - the first case class can be replaced with a much simpler list
    *
    * @param c The macro context
    * @param body The body to write to the resource (or None for pure reads)
    * @param ctt The operation type evidence (combination of `Modifier` classes and `BaseDriverOp`)
    * @param ctc The body type evidence (combination of `Modifier` classes and `BaseDriverOp`)
    * @tparam T The type (combination of `Modifier` classes and `BaseDriverOp`)
    * @return A chainable version of the `BaseDriverOp` mixed with T
    */
  def materializeOpImpl_CBody[T <: BaseDriverOp, C]
  (c: blackbox.Context)(body: c.Expr[C])
  (typeToStringHelper: c.Expr[TypedToStringHelper])
  (implicit ctt: c.WeakTypeTag[T], ctc: c.WeakTypeTag[C])
  : c.Expr[T] =
  {
    import c.universe._

    val opType = getOpType(c)
    val self = c.prefix
    val maybeBody = reify { Option(typeToStringHelper.splice.fromTyped[C](body.splice)) }

    c.Expr[T] {
      buildInternalClass[T](c)(self, opType, maybeBody, List(), List(), ctt)
        .asInstanceOf[c.Tree]
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
    (c: blackbox.Context)(body: c.Expr[String])
    (implicit ct: c.WeakTypeTag[T])
    : c.Expr[T] =
  {
    import c.universe._

    val opType = getOpType(c)
    val self = c.prefix
    val maybeBody = reify { Option(body.splice) }

    c.Expr[T] {
      buildInternalClass[T](c)(self, opType, maybeBody, List(), List(), ct)
        .asInstanceOf[c.Tree]
    }
  }
}
