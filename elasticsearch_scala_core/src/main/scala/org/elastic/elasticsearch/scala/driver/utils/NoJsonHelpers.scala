package org.elastic.elasticsearch.scala.driver.utils

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{CustomTypedToString, EsRequestException, TypedToStringHelper}
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
      case _ => throw EsRequestException(s"Type ${t.getClass} not supported with JSON lib")
    }
  }
}
