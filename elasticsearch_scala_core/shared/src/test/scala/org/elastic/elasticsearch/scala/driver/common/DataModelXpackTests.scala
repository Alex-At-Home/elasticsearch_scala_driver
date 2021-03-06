package org.elastic.elasticsearch.scala.driver.common

import utest._
import DataModelXpack._
import org.elastic.elasticsearch.scala.driver.common.DataModelXpack.MarvelConfig._
import org.elastic.rest.scala.driver.utils.NoJsonHelpers._
import org.elastic.rest.scala.driver.RestBase._
import io.circe.parser.parse

object DataModelXpackTests extends TestSuite {

  val tests = this {
    object xpack extends ApiModelXpack

    "Basic checking for all the xpack data model" - {

      val cfg0 = MarvelConfig(exporters = Map())

      xpack.`/_cluster/settings#marvel.agent`().write(cfg0).body.map(parse) ==> Some(
        parse("""{"persistent":{"marvel.agent.collection.interval":"10s"}}""")
      )

      val cfg1 = MarvelConfig(exporters = Map(), interval = None)

      xpack.`/_cluster/settings#marvel.agent`().write(cfg1).body.map(parse) ==> Some(
        parse("""{"persistent":{}}""")
      )

      val exporter1 = MarvelExporterLocalConfig()
      val exporter2 = MarvelExporterDisabledConfig()
      val exporter3 = MarvelExporterHttpConfig(host = "h1", auth = Some(MarvelAuth("u1", "p1")))
      val exporter4 = MarvelExporterHttpConfig(host = "h2", auth = None)

      val cfg2 = MarvelConfig(exporters = Map(
        "e1" -> exporter1,
        "e2" -> exporter2,
        "e3" -> exporter3,
        "e4" -> exporter4
      ), interval = Some("1s"))

      xpack.`/_cluster/settings#marvel.agent`().write(cfg2).body.map(parse) ==> Some(
        parse("""
          {
            "persistent":{
              "marvel.agent.exporters.e1":{"type":"local","enabled":true},
              "marvel.agent.exporters.e2":{"type":"local","enabled":false},
              "marvel.agent.exporters.e3":{"type":"http","enabled":true,"host":"h1","auth":{"username":"u1","password":"p1"}},
              "marvel.agent.exporters.e4":{"type":"http","enabled":true,"host":"h2"},
              "marvel.agent.collection.interval":"1s"
            }
          }
        """)
      )

      // (check this is valid JSON)
      parse(cfg1.fromTyped).isRight ==> true
      parse(cfg2.fromTyped).isRight ==> true
    }
  }
}
