package org.elastic.elasticsearch.scala.driver.jvm

import javax.net.ssl.SSLContext

import org.apache.http.HttpHost
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.impl.nio.reactor.IOReactorConfig
import org.apache.http.message.{BasicHeader, BasicHttpRequest}
import org.apache.http.protocol.BasicHttpContext
import org.elastic.rest.scala.driver.RestBase._
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}
import org.elasticsearch.client.{ResponseException, ResponseListener, RestClient}

import scala.concurrent.{Future, Promise}
import scala.concurrent.duration.Duration

//TODO: remove password from toString....

/** The Elasticsearch driver for the JVM
  * Run `start()` to provide a client that can be used to execute resource operations
  * @param hostPorts A list of endpoints in the format "host:port"
  * @param ssl If true (default: false) then will attempt to connect over SSL
  * @param connectTimeout The connect timeout (human readable format, eg "1 second")
  * @param socketTimeout The socket timeout (human readable format, eg "10 seconds")
  * @param retryTimeout The retry timeout (human readable format, eg "10 seconds")
  * @param numThreads The number of threads
  * @param basicAuth Basic authentication pair (user, password)
  * @param defaultHeaders A set of default headers applied to every request
  * @param advancedConfig Advanced configuration for the underlying REST client, expressed as a mapping
  *                       from `HttpAsyncClientBuilder ` to itself, enabled the chained `setXxx(...)` calls
  *                       to be performed
  */
case class ElasticsearchDriver
  (hostPorts: List[String] = List("localhost:9200"),
   ssl: Boolean = false,
   connectTimeout: String = "1 second",
   socketTimeout: String = "10 seconds",
   retryTimeout: String = "10 seconds",
   numThreads: Int = 1,
   basicAuth: Option[(String, String)] = None,
   defaultHeaders: List[String] = List(),
   advancedConfig: List[HttpAsyncClientBuilder => HttpAsyncClientBuilder] = List())
{
  /** Creates a new driver with different or appended host:ports
    *
    * @param newHostPorts The new host:port
    * @param overwrite Whether to overwrite the existing settings, or append to them
    * @return A new copy of the driver with the updated settings
    */
  def withNewHostPorts(newHostPorts: List[String], overwrite: Boolean = true): ElasticsearchDriver = {
    val newConfig =
      if (overwrite) newHostPorts
      else hostPorts ++ newHostPorts
    this.copy(hostPorts = newConfig)
  }

  /** Change the SSL status
    *
    * @param ssl Whether to connect over SSL
    * @return A new copy of the driver with the updated settings
    */
  def withSsl(ssl: Boolean): ElasticsearchDriver = {
    this.copy(ssl = ssl)
  }

  /** Change the connect timeout
    *
    * @param newConnectTimeout A connect timeout (in human readable format)
    * @return A new copy of the driver with the updated settings
    */
  def withConnectTimeout(newConnectTimeout: String): ElasticsearchDriver = this.copy(connectTimeout = newConnectTimeout)

  /** Change the socket timeout
    *
    * @param newSocketTimeout A socket timeout (in human readable format)
    * @return A new copy of the driver with the updated settings
    */
  def withSocketTimeout(newSocketTimeout: String): ElasticsearchDriver = this.copy(socketTimeout = newSocketTimeout)

  /** Change the retry timeout
    *
    * @param newRetryTimeout A retry timeout (in human readable format)
    * @return A new copy of the driver with the updated settings
    */
  def withRetryTimeout(newRetryTimeout: String): ElasticsearchDriver = this.copy(retryTimeout = newRetryTimeout)

  /** Set basic auth
    *
    * @param user The username
    * @param pass The password
    * @return A new copy of the driver with the updated settings
    */
  def withBasicAuth(user: String, pass: String): ElasticsearchDriver = this.copy(basicAuth = Some(user, pass))

  /** Remove basic auth (if present)
    *
    * @return A new copy of the driver with the updated settings
    */
  def withoutBasicAuth(): ElasticsearchDriver = this.copy(basicAuth = None)

  /** Change the number of threads
    *
    * @param numThreads The new number of threads
    * @return A new copy of the driver with the updated settings
    */
  def withThreads(numThreads: Int): ElasticsearchDriver = this.copy(numThreads = numThreads)

  /** Change the default headers to be applied to each request
    *
    * @param newDefaultHeaders The new set of default headers to replace/append
    * @param overwrite Whether to overwrite the existing settings, or append to them
    * @return A new copy of the driver with the updated settings
    */
  def withDefaultHeaders(newDefaultHeaders: List[String], overwrite: Boolean = true)
    : ElasticsearchDriver =
  {
    val newConfig =
      if (overwrite) newDefaultHeaders
      else defaultHeaders ++ newDefaultHeaders
    this.copy(defaultHeaders = newConfig)
  }

  /** Changes the set of advanced configuration options applied to the underlying REST driver
    *
    * @param newAdvancedConfig Some advanced configuration options (eg for SSL)
    * @param overwrite Whether to overwrite the existing settings, or append to them
    * @return A new copy of the driver with the updated settings
    */
  def withAdvancedConfig
    (newAdvancedConfig: List[HttpAsyncClientBuilder => HttpAsyncClientBuilder], overwrite: Boolean = true)
    : ElasticsearchDriver =
    {
      val newConfig =
        if (overwrite) newAdvancedConfig
        else advancedConfig ++ newAdvancedConfig
      this.copy(advancedConfig = newConfig)
    }

  /** Starts the driver (if not already started)
    *
    * @return A started ES driver
    */
  def start(): StartedElasticsearchDriver = new StartedElasticsearchDriver(this)
}

case class MutableStartedElasticsearchDriver(esDriver: ElasticsearchDriver) extends RestDriver {

  private var runningEsDriver = new StartedElasticsearchDriver(esDriver)

  /** Creates a new unstarted `ElasticsearchDriver` with these settings that can be reconfigured
    * @return An unstarted `ElasticsearchDriver` instance
    */
  def createCopy: ElasticsearchDriver = runningEsDriver.createCopy

  /** Executes the designated operation
    * @param baseDriverOp The operation to execute
    * @return A future returning the raw reply or throws `RequestException(code, body, message)`
    */
  override def exec(baseDriverOp: BaseDriverOp): Future[String] = runningEsDriver.exec(baseDriverOp)

  /** Change in-place this driver to connect to the new settings object
    * @param esDriver Contains a configuration set for an Elasticsearch connection
    */
  def changeSettings(esDriver: ElasticsearchDriver): Unit = {
    runningEsDriver = new StartedElasticsearchDriver(esDriver)
  }
}

/** A started Elasticsearch Driver that can execute resource operations
  */
class StartedElasticsearchDriver(esDriver: ElasticsearchDriver) extends RestDriver {

  /** Creates a new unstarted `ElasticsearchDriver` with these settings that can be reconfigured
    * @return An unstarted `ElasticsearchDriver` instance
    */
  def createCopy: ElasticsearchDriver = esDriver

  /** (HAS SIDE-EFFECTS) Closes the underlying reset client
    */
  def close(): Unit = restClient.close()

  /** (HAS SIDE-EFFECTS) Returns the underlying REST client (on wnich effectful operations can be applied)
    * @return The underlying REST client
    */
  def rawClient(): RestClient = restClient

  /** Executes the designated operation
    * @param baseDriverOp The operation to execute
    * @return A future returning the raw reply or throws `RequestException(code, body, message)`
    */
  override def exec(baseDriverOp: BaseDriverOp): Future[String] = {
    val promise = Promise[String]
    val responseCallback = new ResponseListener() {
      override def onSuccess(response: org.elasticsearch.client.Response) = {
        val code = response.getStatusLine.getStatusCode
        val messageBody = Option(response.getEntity).map(_.getContent).map(scala.io.Source.fromInputStream(_).mkString)
        if ((code >= 200) && (code < 300)) {
          promise.success(messageBody.orNull)
        }
        else {
          val errorMessage = response.getStatusLine.getReasonPhrase
          promise.failure(RestServerException(code, errorMessage, messageBody))
        }
      }
      override def onFailure(ex: Exception) = ex match {
        case esEx: ResponseException =>
          val response = esEx.getResponse.getStatusLine
          val errorMessage = response.getReasonPhrase
          promise.failure(RestServerException(response.getStatusCode, errorMessage, Some(ex.getMessage)))

        case _ =>
          promise.failure(ex)
      }
    }
    val headers = (baseDriverOp.headers ++ esDriver.defaultHeaders).map { s =>
      val decomp = s.split(":", 2)
      new BasicHeader(decomp(0), decomp(1))
    } ++ basicAuth

    restClient.performRequest(
      baseDriverOp.op,
      baseDriverOp.getUrl,
      responseCallback,
      headers:_*)
    promise.future
  }

  val basicAuth = esDriver.basicAuth.map { auth =>
    val userPass = new UsernamePasswordCredentials(auth._1, auth._2)
    new BasicScheme().authenticate(userPass, new BasicHttpRequest("GET", "/"), new BasicHttpContext())
  }.toList

    /** The underlying Elasticsearch REST client */
  private [this] val restClient: RestClient = {
    // Advanced building configuration
    val clientBuilderOps = for {
      noClientBuilder <- Some((clientBuilder: HttpAsyncClientBuilder) => clientBuilder)

      // SSL
      applySsl <- Some(noClientBuilder).map { chain =>
        if (esDriver.ssl) {
          chain andThen ((clientBuilder: HttpAsyncClientBuilder) => {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, null, null) //(need to get working as per https://issues.apache.org/jira/browse/HTTPCLIENT-1211)
            clientBuilder.setSSLContext(sslContext)
          })
        }
        else chain
      }

      // Thread count
      applyThreadCount <- Some(applySsl).map { chain =>
        if (esDriver.numThreads > 1) {
          chain andThen ((clientBuilder: HttpAsyncClientBuilder) =>
            clientBuilder.setDefaultIOReactorConfig(
              IOReactorConfig.custom().setIoThreadCount(esDriver.numThreads).build())
            )
        }
        else chain
      }

      // Basic auth, handled via headers above, can't get setDefaultCredentialsProvider to work

      // Advanced options
      applyAdvancedConfig <- Some(applyThreadCount).map { chain =>
        esDriver.advancedConfig.foldLeft(chain) { (acc, v) =>
          acc andThen v
        }
      }

    } yield applyAdvancedConfig

    // Build the client is startNow is set:
    val client = for {
      _ <- Some()
      // URL
      hostPorts = esDriver.hostPorts.map(_.split(":", 2)).map { case Array(host, port) =>
        new HttpHost(host, port.toInt, if (esDriver.ssl) "https" else "http")
      }
      fromUrl <- Some(RestClient.builder(hostPorts:_*))

      // SSL handled below, under Client builder ops

      // Connect and socket timeouts
      fromTimeouts <- Some(fromUrl).map { builder =>
        builder.setRequestConfigCallback(
          new RequestConfigCallback {
            override def customizeRequestConfig(requestConfigBuilder: Builder): Builder =
              requestConfigBuilder
                .setConnectTimeout(Duration(esDriver.connectTimeout).toMillis.toInt)
                .setSocketTimeout(Duration(esDriver.socketTimeout).toMillis.toInt)
          }
        )
      }

      // Retry timeout
      fromRetryTimeout <- Some(fromTimeouts).map(
        _.setMaxRetryTimeoutMillis(Duration(esDriver.retryTimeout).toMillis.toInt))

      // Thread count handled below, under Client builder ops

      // Basic auth handled below, under Client builder ops

      // Advanced config handled below, under Client builder ops

      // Client builder ops:

      finalConfig <- Some(fromRetryTimeout).map { currClient =>
        clientBuilderOps.map { ops =>
          currClient.setHttpClientConfigCallback(
            new HttpClientConfigCallback {
              override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder = {
                ops(httpClientBuilder)
              }
            }
          )
        }.getOrElse(currClient)
      }

    } yield finalConfig

    client.map(_.build()).get
  }
}


