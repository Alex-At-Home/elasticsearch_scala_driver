package org.elastic.elasticsearch.scala.driver.jvm

/**
  * Creates a command line shell to manipulate ES
  */
object ShellMain {

  /**
    * Main
    *
    * @param args The command line args:
    *             `--creds user[:password]`
    *             `--credsfile file`
    *             `--ssl`
    *             `--host host` (default: localhost)
    *             `--port port` (default: port)
    */
  def main(args: Array[String]): Unit = {
    val startup =
      """
        |      import scala.concurrent.ExecutionContext.Implicits.global
        |      import org.elastic.rest.scala.driver.RestResources._
        |      import org.elastic.rest.scala.driver.RestBase._
        |      import org.elastic.rest.scala.driver.utils.MockRestDriver
        |      import org.elastic.rest.scala.driver.json.CirceJsonModule._
        |      import org.elastic.rest.scala.driver.json.CirceTypeModule._
        |      import org.elastic.elasticsearch.scala.driver.jvm.ElasticsearchDriver
        |      import org.elastic.elasticsearch.scala.driver.versions.Versions._
        |      import org.elastic.elasticsearch.scala.driver.versions.Versions.latest
        |      import org.elastic.elasticsearch.scala.driver.common.DataModelCommon._
      """.stripMargin

    //TODO: command line processing
    //TODO: data model aggregate?

    ammonite.Main(predef = startup).run()
  }

}
