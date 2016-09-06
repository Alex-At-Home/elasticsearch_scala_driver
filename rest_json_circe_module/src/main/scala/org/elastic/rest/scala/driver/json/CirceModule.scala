package org.elastic.rest.scala.driver.json

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{JsonToStringHelper, _}
import io.circe._
import io.circe.generic.auto._
import io.circe.jawn._
import io.circe.syntax._

import scala.reflect.runtime.universe._
import scala.concurrent.Future

/** Integration for CIRCE with REST drivers
  */
object CirceModule {

  /** JSON inputs */
  implicit val jsonToStringHelper = new JsonToStringHelper[Json] {
    override def fromJson(j: Json): String = j.spaces2
  }

  /** JSON outputs */
  implicit class StringToJsonHelper(op: BaseDriverOp) {
    def execJ()(implicit driver: EsDriver): Future[io.circe.Json] =
      driver.exec(op)
        .map(s => toJson(s))
  }

  /** Typed inputs */
  implicit val typedToStringHelper = new TypedToStringHelper() {
    //TODO: need an encoder here, no idea to create one? (pass weak type tag around and then have macro?)
    override def fromTyped[T](t: T): String = t match {
      case custom: CustomTypedToString => custom.fromTyped
      case _ => t.asJson.toString()
    }
  }

  /** Typed outputs */
  implicit val stringToTypedHelper = new StringToTypedHelper() {
    override def toType[T](s: String)(implicit ct: WeakTypeTag[T]): T = {
      if (ct.tpe <:< typeOf[CustomStringToTyped]) {
        //TODO not sure how to do this? (macros)
        null
      }.asInstanceOf[T]
      else { // normal cases
        decode[T](s)
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
