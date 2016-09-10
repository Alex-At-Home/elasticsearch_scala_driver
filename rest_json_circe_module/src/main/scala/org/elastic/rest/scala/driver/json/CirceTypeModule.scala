package org.elastic.rest.scala.driver.json

import io.circe.{Decoder, Encoder}
import io.circe.jawn._
import io.circe.syntax._
import org.elastic.rest.scala.driver.RestBase._

import scala.collection.JavaConverters._
import scala.reflect.runtime.universe._
import scala.reflect.runtime._

/** Integration for CIRCE with REST drivers
  * to decide which JSON lib to use for typed on a case by case....
  */
object CirceTypeModule {

  // These have to be at the front for some reason
  // See below for the implicits

  //TODO scaladocs

  val encoderRegistry: scala.collection.concurrent.Map[String, Encoder[_]] =
    new java.util.concurrent.ConcurrentHashMap[String, Encoder[_]]().asScala
  val decoderRegistry: scala.collection.concurrent.Map[String, Decoder[_]] =
    new java.util.concurrent.ConcurrentHashMap[String, Decoder[_]]().asScala

  def generatedXcoder[T]
  (picker: universe.Type => Boolean)
  (implicit ct: universe.WeakTypeTag[T]) = {
    val companionMirror =
      currentMirror.reflectModule(ct.tpe.typeSymbol.companion.asModule)

    ct.tpe.companion.members.toList
      .filter(_.isImplicit)
      .map(_.asMethod)
      .filter(_.isMethod)
      .filter(m => picker(m.returnType))
      .map { t => currentMirror
        .reflect(companionMirror.instance)
        .reflectMethod(t).apply()
      }
  }

  def getGeneratedDecoder[T](implicit ct: universe.WeakTypeTag[T]) = {
    generatedXcoder[T](t => t <:< typeOf[Decoder[_]])
      .head.asInstanceOf[Decoder[T]]
  }

  def getGeneratedEncoder[T](implicit ct: universe.WeakTypeTag[T]) = {
    generatedXcoder[T](t => t <:< typeOf[Encoder[_]])
      .head.asInstanceOf[Encoder[T]]
  }

  // Implicits

  /** Typed inputs */
  implicit val typedToStringHelper = new TypedToStringHelper() {
    override def fromTyped[T](t: T)(implicit ct: WeakTypeTag[T]): String = t match {
      case custom: CustomTypedToString => custom.fromTyped
      case _ =>
        //(lazily build a registry of encoders)
        val encoder = encoderRegistry
          .getOrElseUpdate(ct.tpe.toString, getGeneratedEncoder[T])

        t.asJson(encoder.asInstanceOf[Encoder[T]]).noSpaces
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
        //(lazily build a registry of decoders)
        val decoder = decoderRegistry
          .getOrElseUpdate(ct.tpe.toString, getGeneratedDecoder[T])

        decode[T](s)(decoder.asInstanceOf[Decoder[T]])
          .leftMap({ err =>
            throw RestServerException(200, s"JSON serialization error: $err", Option(s)) }
          ).toOption.get
      }
    }
  }
}
