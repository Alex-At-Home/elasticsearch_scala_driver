package org.elastic.rest.scala.driver.json

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{JsonToStringHelper, _}
import io.circe._
import io.circe.jawn._
import io.circe.syntax._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.reflect.runtime.universe._
import scala.concurrent.Future
import scala.collection.JavaConverters._

/** Integration for CIRCE with REST drivers
  */
object CirceModule {

  val encoderRegistry =
    new java.util.concurrent.ConcurrentHashMap[String, Encoder[_]]().asScala
  val decoderRegistry =
    new java.util.concurrent.ConcurrentHashMap[String, Decoder[_]]().asScala

  /** JSON inputs */
  implicit val jsonToStringHelper = new JsonToStringHelper[Json] {
    override def fromJson(j: Json): String = j.noSpaces
  }

  /** JSON outputs */
  implicit class StringToJsonHelper(op: BaseDriverOp) {
    def execJ()(implicit driver: EsDriver): Future[io.circe.Json] = {
      driver.exec(op)
        .map(s => toJson(s))
    }
  }

  /** Typed inputs */
  implicit val typedToStringHelper = new TypedToStringHelper() {
    override def fromTyped[T](t: T)(implicit ct: WeakTypeTag[T]): String = t match {
      case custom: CustomTypedToString => custom.fromTyped
      case _ => t.asJson(
        encoderRegistry(ct.tpe.toString).asInstanceOf[Encoder[T]]
      ).noSpaces
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
        decode[T](s)(decoderRegistry(ct.tpe.toString).asInstanceOf[Decoder[T]])
          .leftMap({ err =>
            throw EsServerException(200, s"JSON serialization error: $err", Option(s)) }
          )
          .getOrElse(null.asInstanceOf[T])
      }
    }
  }

  // Utilities

  /** Utility method to convert string to JSON
    *
    * @param s The JSON string to parse
    * @return Parsed string
    */
  private def toJson(s: String): Json = {
    parse(s)
      .leftMap({ err =>
        throw EsServerException(200, s"JSON serialization error: $err", Option(s)) }
      )
      .getOrElse(Json.Null)
  }
}
