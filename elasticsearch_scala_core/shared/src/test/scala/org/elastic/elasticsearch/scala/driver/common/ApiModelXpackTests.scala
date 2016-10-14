package org.elastic.elasticsearch.scala.driver.common

import utest._
import org.elastic.rest.scala.driver.RestBase._

object ApiModelXpackTests extends TestSuite {

  val tests = this {
    "Basic checking for all the xpack resources" - {

      object api extends ApiModelCommon
      object xpack extends ApiModelXpack

      //TODO
    }
  }
}
