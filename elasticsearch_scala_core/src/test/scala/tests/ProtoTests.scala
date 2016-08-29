package tests


import org.elastic.elasticsearch.driver.scala.ElasticsearchBase.{BaseDriverOp, _}
import org.elastic.elasticsearch.driver.scala.Modifiers.{Human, Pretty}
import utest._

object ProtoTests extends TestSuite{

  trait Internal extends BaseDriverOp with Pretty with Human
  case class Macro() extends MacroOperatable[Internal] with EsResource

  //////////////////////////////////////////////////////////////////////////////////////

  def tests = TestSuite {
    'test1 {
      println("??1 " + Macro().opImpl(null, null, null, List()))
      println("??2 " + Macro().opImpl(null, null, null, List()).`?human=`(true))
      println("??3 " + Macro().opImpl(null, null, null, List()).`?human=`(true).`?pretty`)
    }
  }
}
