package org.elastic.rest.scala.driver.json

import io.circe._
import io.circe.jawn._
import org.elastic.rest.scala.driver.RestBase._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Integration for CIRCE with REST drivers
  * to decide which JSON lib to use for typed on a case by case....
  * TODO: OK new plan, will just split into CirceJsonModule and CirceTypeModule, you need
  */
object CirceJsonModule {

  /** JSON inputs */
  implicit val jsonToStringHelper = new JsonToStringHelper[Json] {
    override def fromJson(j: Json): String = j.noSpaces
  }

  /** JSON outputs */
  implicit class StringToJsonHelper(op: BaseDriverOp) {
    def execJ()(implicit driver: RestDriver): Future[io.circe.Json] = {
      driver.exec(op)
        .map(s => toJson(s))
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
        throw RestServerException(200, s"JSON serialization error: $err", Option(s)) }
      )
      .getOrElse(Json.Null)
  }
}
