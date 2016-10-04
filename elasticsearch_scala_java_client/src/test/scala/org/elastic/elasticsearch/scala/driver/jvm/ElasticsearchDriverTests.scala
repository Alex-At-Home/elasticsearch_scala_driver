package org.elastic.elasticsearch.scala.driver.jvm

import colossus._
import core._
import service._
import protocols.http._
import UrlParsing._
import HttpMethod._
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.message.BasicHeader
import org.elastic.elasticsearch.driver.utils.ServerUtils
import org.elastic.elasticsearch.scala.driver.versions.Versions
import org.elastic.rest.scala.driver.RestBase._
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback
import utest._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object ElasticsearchDriverTests extends TestSuite {

  val tests = this {
    "Test ElasticsearchDriver builder operations" - {
      val driver = ElasticsearchDriver()
      val base = ElasticsearchDriver(
        List("http://localhost:9200"), "5 seconds", "10 seconds", "10 seconds", 1, None, List())
      driver ==> base
      driver.withUrls("host1:9999") ==> base.copy(urls = List("host1:9999"))
      driver.withNewUrls(overwrite = false, "host2:8888") ==>
        base.copy(urls = List("http://localhost:9200", "host2:8888"))
      driver.withConnectTimeout("2 seconds") ==> base.copy(connectTimeout = "2 seconds")
      driver.withSocketTimeout("3 seconds") ==> base.copy(socketTimeout = "3 seconds")
      driver.withRetryTimeout("4 seconds") ==> base.copy(retryTimeout = "4 seconds")
      driver.withBasicAuth("u", "p") ==> base.copy(basicAuth = Some(("u", "p")))
      driver.withBasicAuth("u", "p").withoutBasicAuth() ==> base
      driver.withThreads(3) ==> base.copy(numThreads = 3)

      //Check toString override
      driver.withBasicAuth("u", "password").toString.contains("password") ==> false

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
    }
    "Can read/write to/from an HTTP server" - {

      // Just handle the "/" resource
      class TestService(context: ServerContext) extends HttpService(context) {
        def handle = {
          case request @ Get on Root if request.head.query.isEmpty =>
            val hasDefaultHeader = request.head.headers.firstValue("x-default").contains("test1")
            val hasRequestHeader = request.head.headers.firstValue("x-request").contains("test2")
            val basicAuth = request.head.headers.firstValue("Authorization").map(" " + _).getOrElse("")
            Callback.successful(request.ok(s"rx:/ $hasDefaultHeader $hasRequestHeader$basicAuth"))
          case request @ Get on Root if request.head.query.isDefined =>
            Callback.successful(request.ok(s"rx:/${request.head.query.get}"))
          case x @ _ =>
            Callback.failed(new Exception(s"Unexpected request: $x"))
        }
      }

      val (server, port) = ServerUtils.createTestServer(new TestService(_))

      try {
        val driver =
          ElasticsearchDriver()
            .withUrls(s"localhost:$port")
            .start()

        val driver2 =
          driver.createCopy
            .withDefaultHeaders(List("x-default: test1"))
            .start()

        // Basic check
        {
          val futureResult = driver.exec(Versions.latest.`/`().read())
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/ false false"
        }
        // Basic check (default header)
        {
          val futureResult = driver2.exec(Versions.latest.`/`().read())
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/ true false"
        }
        // custom headers
        {
          val futureResult = driver2.exec(
            Versions.latest.`/`().read().h("x-request: test2"))
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/ true true"
        }
        // URL params
        {
          val futureResult = driver.exec(Versions.latest.`/`().read().pretty(true))
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/pretty=true"
        }
        // Check errors
        {
          val futureResult =
            driver.exec(Versions.latest.`/$uri`("/not_present").read())
                .recover {
                  case ex: RestServerException => s"${ex.code}"
                }
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "404"
        }

        // Other driver options

        // Check that thread count at least doesn't break anything...
        {
          val threadDriver = driver.createCopy.withThreads(2).start()
          val futureResult = threadDriver.exec(Versions.latest.`/`().read())
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/ false false"
        }
        // Check SSL (should at make it fail!)
        {
          val sslDriver = driver.createCopy.withUrls(s"https://localhost:$port").start()
          val futureResult = sslDriver.exec(Versions.latest.`/`().read())
          val retVal = scala.util.Try { Await.ready(futureResult, Duration("5 seconds")) }
          retVal.isFailure ==> true
        }
        // Check basic auth:
        {
          val authDriver = driver.createCopy.withBasicAuth("user", "pass").start()
          val futureResult = authDriver.exec(Versions.latest.`/`().read())
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/ false false Basic dXNlcjpwYXNz"
        }
        // Check advanced options
        {
          import scala.collection.JavaConversions._
          val authDriver = driver.createCopy.withAdvancedConfig(
            List(client => client.setDefaultHeaders(List(new BasicHeader("x-default", "test1")))))
            .start()
          val futureResult = authDriver.exec(Versions.latest.`/`().read())
          val retVal = Await.result(futureResult, Duration("5 seconds"))
          retVal ==> "rx:/ true false"
        }

        // Method coverage
        Versions.latest.`/$uri`("test_index")
      }
      finally {
        server.die()
      }
    }
    //TODO: test all the timeouts
  }
}
