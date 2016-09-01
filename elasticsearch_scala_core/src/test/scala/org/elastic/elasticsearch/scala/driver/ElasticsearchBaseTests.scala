package org.elastic.elasticsearch.scala.driver

import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{EsResource, Modifier}
import utest._

object ElasticsearchBaseTests extends TestSuite {

  val tests = this {
    "Check modifiers work" - {

      case class ModTest() extends Modifier {
        def getStringModifier(s: String): String = this.getModifier(s)

        def getBooleanModifier(b: Boolean): String = this.getModifier(b)

        def getListModifier(ss: List[String]): String = this.getModifier(ss)
      }
      assert(ModTest().getStringModifier("x") == "getStringModifier=x")
      assert(ModTest().getBooleanModifier(true) == "getBooleanModifier=true")
      assert(ModTest().getListModifier(List("x", "y")) == "getListModifier=x,y")
    }
    "Check the location generator works" - {
      case class `/`() extends EsResource
      case class `/$list`(list: Seq[String]) extends EsResource
      case class `/test/$variable/test2/$anotherVariable`
        (variable: String, anotherVariable: String) extends EsResource
      case class `/test/$list`(ss: String*) extends EsResource

      assert(`/`().location == "/")
      assert(`/$list`(Seq("a", "b")).location == "/a,b")
      assert(`/test/$variable/test2/$anotherVariable`("a", "b").location == "/test/a/test2/b")
      assert(`/test/$list`("a", "b").location == "/test/a,b")
    }
  }
}
