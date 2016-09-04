package org.elastic.elasticsearch.scala.driver.jvm

import colossus._
import core._
import service._
import protocols.http._
import UrlParsing._
import HttpMethod._
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elastic.elasticsearch.driver.utils.ServerUtils
import org.elastic.elasticsearch.scala.driver.versions.Versions
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback
import utest._
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ElasticsearchDriverTests extends TestSuite {

  val tests = this {
    "Test ElasticsearchDriver builder operations" - {
      val driver = ElasticsearchDriver()
      val base = ElasticsearchDriver(List("localhost:9200"), "1 second", "10 seconds", "10 seconds", 1, None, List())
      driver ==> base
      driver.withNewHostPorts(List("host1:9999")) ==> base.copy(hostPorts = List("host1:9999"))
      driver.withNewHostPorts(List("host2:8888"), overwrite = false) ==>
        base.copy(hostPorts = List("localhost:9200", "host2:8888"))
      driver.withConnectTimeout("2 seconds") ==> base.copy(connectTimeout = "2 seconds")
      driver.withSocketTimeout("3 seconds") ==> base.copy(socketTimeout = "3 seconds")
      driver.withRetryTimeout("4 seconds") ==> base.copy(retryTimeout = "4 seconds")
      driver.withBasicAuth("u", "p") ==> base.copy(basicAuth = Some(("u", "p")))
      driver.withBasicAuth("u", "p").withoutBasicAuth() ==> base
      driver.withThreads(3) ==> base.copy(numThreads = 3)

      val h1 = "x-header1: val1"
      val h2 = "x-header2: val2"
      val baseDriverWithHeader = driver.withDefaultHeaders(List(h1), overwrite = false)
      baseDriverWithHeader ==> base.copy(defaultHeaders = List(h1))
      baseDriverWithHeader.withDefaultHeaders(List(h2), overwrite = true) ==> base.copy(defaultHeaders = List(h2))
      baseDriverWithHeader.withDefaultHeaders(List(h2), overwrite = false) ==> base.copy(defaultHeaders = List(h1, h2))

      // Advanced settings:
      val a = new HttpClientConfigCallback {
        val credentialsProvider = new BasicCredentialsProvider()
        override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
          httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
      }
      val b = new HttpClientConfigCallback {
        val credentialsProvider = new BasicCredentialsProvider()
        override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
          httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
      }
      val basePlusConfig = driver.withAdvancedConfig(List(a), overwrite = true)
      basePlusConfig ==> base.copy(advancedConfig = List(a))
      basePlusConfig.withAdvancedConfig(List(b), overwrite = true) ==> base.copy(advancedConfig = List(b))
      basePlusConfig.withAdvancedConfig(List(b), overwrite = false) ==> base.copy(advancedConfig = List(a, b))
    }
    "Can read/write to/from an HTTP server" - {

      // Just handle the "/" resource
      class TestService(context: ServerContext) extends HttpService(context) {
        //TODO: check headers
        def handle = {
          case request @ Get on Root if request.head.query.isEmpty =>
            Callback.successful(request.ok("rx:/"))
          case request @ Get on Root if request.head.query.isDefined =>
            Callback.successful(request.ok(s"rx:/${request.head.query.get}"))
        }
      }

      val (server, port) = ServerUtils.createTestServer(new TestService(_))

      try {
        val driver =
          ElasticsearchDriver()
            .withNewHostPorts(List(s"localhost:$port"))
            .withDefaultHeaders(List("x-test: test"))
            .start()

        // Basic check
        {
          val futureResult = driver.exec(Versions.latest.`/`().read())
          val retVal = Await.result(futureResult, Duration("1 second"))
          retVal ==> "rx:/"
        }
        // URL params
        {
          val futureResult = driver.exec(Versions.latest.`/`().read().pretty(true))
          val retVal = Await.result(futureResult, Duration("1 second"))
          retVal ==> "rx:/pretty=true"
        }

        //TODO check body

        //TODO check errors
      }
      finally {
        server.die()
      }
      //TODO
    }
    //TODO: check auth / timeout / headers(x2)
  }
}
