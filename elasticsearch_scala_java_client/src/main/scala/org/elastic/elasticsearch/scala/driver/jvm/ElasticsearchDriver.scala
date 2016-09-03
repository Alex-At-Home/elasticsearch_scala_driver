package org.elastic.elasticsearch.scala.driver.jvm

import org.apache.http.HttpHost
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.impl.nio.reactor.IOReactorConfig
import org.apache.http.message.BasicHeader
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.{BaseDriverOp, EsDriver, RequestException}
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}
import org.elasticsearch.client.{ResponseListener, RestClient, RestClientBuilder}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration.Duration

/** The Elasticsearch driver for the JVM
  * Run `start()` to provide a client that can be used to execute resource operations
  * @param hostPort host:port
  * @param connectTimeout The connect timeout (human readable format, eg "1 second")
  * @param socketTimeout The socket timeout (human readable format, eg "10 seconds")
  * @param retryTimeout The retry timeout (human readable format, eg "10 seconds")
  * @param numThreads The number of threads
  * @param basicAuth Basic authentication pair (user, password)
  * @param defaultHeaders A set of default headers applied to every request
  * @param advancedConfig Advanced configuration for the underlying REST client
  */
case class ElasticsearchDriver
  (hostPort: String = "localhost:9200",
   connectTimeout: String = "1 second",
   socketTimeout: String = "10 seconds",
   retryTimeout: String = "10 seconds",
   numThreads: Int = 1,
   basicAuth: Option[(String, String)] = None,
   defaultHeaders: List[String] = List(),
   advancedConfig: List[RestClientBuilder.HttpClientConfigCallback] = List())
{
  /** Creates a new drive with a changed URL
    *
    * @param newHostPort The new host:port
    * @return A new copy of the driver with the updated settings
    */
  def withHostPort(newHostPort: String): ElasticsearchDriver = this.copy(hostPort = newHostPort)

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
    (newAdvancedConfig: List[RestClientBuilder.HttpClientConfigCallback], overwrite: Boolean = true)
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

/** A started Elasticsearch Driver that can execute resource operations
  */
class StartedElasticsearchDriver(esDriver: ElasticsearchDriver) extends EsDriver {

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
    * @param ec Implicit execution context (not needed here but needed for the override)
    * @return A future returning the raw reply or throws `RequestException(code, body, message)`
    */
  override def exec(baseDriverOp: BaseDriverOp)(implicit ec: ExecutionContext): Future[String] = {
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
          promise.failure(RequestException(code, errorMessage, messageBody))
        }
      }
      override def onFailure(ex: Exception) = promise.failure(ex)
    }
    val headers = (baseDriverOp.headers ++ esDriver.defaultHeaders).map { s =>
      val decomp = s.split(":", 2)
      new BasicHeader(decomp(0), decomp(1))
    }
    restClient.performRequest(baseDriverOp.op, baseDriverOp.resource.location, responseCallback, headers:_*)
    promise.future
  }

  /** The underlying Elasticsearch REST client */
  private [this] val restClient: RestClient = {
    // Build the client is startNow is set:
    val client = for {
      _ <- Some()
      // URL
      Array(host, port) = esDriver.hostPort.split(":")
      fromUrl <- Some(RestClient.builder(new HttpHost(host, port.toInt)))

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

      fromNumThreads <- Some(fromRetryTimeout).filter(_ => esDriver.numThreads > 1).orElse {
        Some(fromRetryTimeout.setHttpClientConfigCallback(new HttpClientConfigCallback {
          override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
            httpClientBuilder.setDefaultIOReactorConfig(
              IOReactorConfig.custom().setIoThreadCount(esDriver.numThreads).build())
        }))
      }

      // Basic auth
      fromBasicAuth <- esDriver.basicAuth.map { auth => {
        val credentialsProvider = new BasicCredentialsProvider()
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth._1, auth._2))
        fromNumThreads.setHttpClientConfigCallback(new HttpClientConfigCallback {
          override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
        })
      }
      }.orElse(Some(fromNumThreads))

      // Advanced config
      fromAdvancedConfig <- Some(fromBasicAuth).map { builder =>
        esDriver.advancedConfig.foldLeft(builder) { (acc, v) => acc.setHttpClientConfigCallback(v) }
      }
    } yield fromAdvancedConfig

    client.map(_.build()).get
  }
}

