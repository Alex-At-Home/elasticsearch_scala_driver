package org.elastic.rest.scala.driver.utils

import org.elastic.rest.scala.driver.RestBase.{CustomTypedToString, RestRequestException, TypedToStringHelper}

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

/**
  * Supports typed object for custom classes only, ie if no JSON library is used
  * for some reason
  */
object NoJsonHelpers {

  /**
    * Include this to support a typed API made entirely out of custom classes
    */
  implicit val NoJsonTypedToStringHelper = new TypedToStringHelper() {
    def fromTyped[T](t: T)(implicit ct: WeakTypeTag[T]): String = t match {
      case custom: CustomTypedToString => custom.fromTyped
      case _ => throw RestRequestException(s"Type ${t.getClass} not supported with JSON lib")
    }
  }

  /** Given a class with a single constructor taking a string,
    * creates an instance of the class
    *
    * @param s The input to the ctor
    * @param ct The weak type tag of the custom typed output object
    * @tparam T The type of the custom typed output object
    * @return An instnce of the type
    */
  def createCustomTyped[T](s: String)(implicit ct: universe.WeakTypeTag[T]): T = {
    val ctor =
      ct.tpe.members
        .filter(m => m.isMethod && m.asMethod.isPrimaryConstructor)
        .map(_.asMethod)
        .head

    val ctorMirror =
      currentMirror
        .reflectClass(ct.tpe.typeSymbol.asClass)
        .reflectConstructor(ctor)(s)

    ctorMirror.asInstanceOf[T]
  }

}
