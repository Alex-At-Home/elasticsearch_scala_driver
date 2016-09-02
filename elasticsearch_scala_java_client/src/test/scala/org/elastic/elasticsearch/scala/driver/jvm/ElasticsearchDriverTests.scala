package org.elastic.elasticsearch.scala.driver.jvm

import utest._

object ElasticsearchDriverTests extends TestSuite {

  val tests = this {
    "Some basic testing" - {
      val driver = ElasticsearchDriver()
      driver.withUrl("test")
    }
  }
}
