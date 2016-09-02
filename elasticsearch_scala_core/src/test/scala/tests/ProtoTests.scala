package tests


import org.elastic.elasticsearch.driver.scala.ApiModel_common
import org.elastic.elasticsearch.driver.scala.Modifiers.{Human, Pretty}
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsResource}
import org.elastic.elasticsearch.scala.driver.ResourceOperations._
import org.elastic.elasticsearch.scala.driver.common.ApiModelCommon
import org.elastic.elasticsearch.scala.driver.versions.Versions
import utest._

object ProtoTests extends TestSuite{

  trait Internal extends BaseDriverOp with Pretty with Human
//  case class TestRead() extends EsReadable[Internal] with EsResource
  case class TestRead() extends EsReadable[Internal] with EsResource
  case class TestReadBody() extends EsWithDataReadable[Internal] with EsResource

  //////////////////////////////////////////////////////////////////////////////////////

  def tests = TestSuite {
    'test1 {

      ApiModelCommon.`/`().$("t")._analyze
      ApiModel_common.`/_flush`

      import ApiModel_common._
      import ApiModelCommon._
      `/`()._aliases.location

      //TODO: this should be Versions.latest()._alias.index("x").type("y").read()
      Versions.latest.`/`()._alias.$("x").read()
      Versions.latest()._alias.$("x").read()

      println("??1 " + TestRead().read())
      println("??2 " + TestReadBody().read("alex"))

//      println("??1 " + Macro().opImpl(null, null, null, List()))
//      println("??2 " + Macro().opImpl(null, null, null, List()).`?human=`(true))
//      println("??3 " + Macro().opImpl(null, null, null, List()).`?human=`(true).`?pretty`)
    }
  }
}
