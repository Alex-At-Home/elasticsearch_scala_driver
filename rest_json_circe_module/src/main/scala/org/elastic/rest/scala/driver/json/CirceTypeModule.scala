package org.elastic.rest.scala.driver.json

import io.circe.{Decoder, Encoder}
import io.circe.jawn._
import io.circe.syntax._
import org.elastic.rest.scala.driver.RestBase._
import scala.collection.JavaConverters._
import scala.reflect.runtime.universe._

/** Integration for CIRCE with REST drivers
  * to decide which JSON lib to use for typed on a case by case....
  * TODO: OK new plan, will just split into CirceJsonModule and CirceTypeModule, you need
  */
object CirceTypeModule {

  val encoderRegistry =
    new java.util.concurrent.ConcurrentHashMap[String, Encoder[_]]().asScala
  val decoderRegistry =
    new java.util.concurrent.ConcurrentHashMap[String, Decoder[_]]().asScala

  /** Typed inputs */
  implicit val typedToStringHelper = new TypedToStringHelper() {
    override def fromTyped[T](t: T)(implicit ct: WeakTypeTag[T]): String = t match {
      case custom: CustomTypedToString => custom.fromTyped
      case _ =>
        //TODO lazily build encoder registry
        t.asJson(encoderRegistry(ct.tpe.toString).asInstanceOf[Encoder[T]]).noSpaces
    }
  }

  /** Typed outputs */
  implicit val stringToTypedHelper = new StringToTypedHelper() {
    override def toType[T](s: String)(implicit ct: WeakTypeTag[T]): T = {
      if (ct.tpe <:< typeOf[CustomStringToTyped]) {
        //TODO will assume that the type has a constructor that takes an "s"
        null
      }.asInstanceOf[T]
      else { // normal cases
        //TODO lazily build decoder registry

        decode[T](s)(decoderRegistry(ct.tpe.toString).asInstanceOf[Decoder[T]])
          .leftMap({ err =>
            throw RestServerException(200, s"JSON serialization error: $err", Option(s)) }
          ).toOption.get
      }
    }
  }
}
