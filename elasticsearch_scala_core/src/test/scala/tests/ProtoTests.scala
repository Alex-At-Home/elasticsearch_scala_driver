package tests


import org.elastic.elasticsearch.driver.scala.ApiModel_common
import org.elastic.elasticsearch.driver.scala.Modifiers.{Human, Pretty}
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsResource}
import utest._

object ProtoTests extends TestSuite{

  trait Internal extends BaseDriverOp with Pretty with Human
//  case class Macro() extends MacroOperatable[Internal] with EsResource

  //////////////////////////////////////////////////////////////////////////////////////

  def tests = TestSuite {
    'test1 {

      ApiModel_common.`/`().$("t")._analyze
      ApiModel_common.`/_flush`

      import ApiModel_common._
      `/`()._aliases.location

//      println("??1 " + Macro().opImpl(null, null, null, List()))
//      println("??2 " + Macro().opImpl(null, null, null, List()).`?human=`(true))
//      println("??3 " + Macro().opImpl(null, null, null, List()).`?human=`(true).`?pretty`)
    }
  }
}
