package org.elastic.elasticsearch.scala.driver.jvm

import org.apache.http.HttpHost
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.config.RequestConfig.Builder
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.impl.nio.reactor.IOReactorConfig
import org.elastic.elasticsearch.scala.driver.ElasticsearchBase.EsDriver
import org.elasticsearch.client.RestClientBuilder.{HttpClientConfigCallback, RequestConfigCallback}
import org.elasticsearch.client.{RestClient, RestClientBuilder}

import scala.concurrent.duration.Duration

/** The Elasticsearch driver for the JVM
  * @param hostPort host:port
  * @param connectTimeout The connect timeout (human readable format, eg "1 second")
  * @param socketTimeout The socket timeout (human readable format, eg "10 seconds")
  * @param retryTimeout The retry timeout (human readable format, eg "10 seconds")
  * @param numThreads The number of threads
  * @param basicAuth Basic authentication pair (user, password)
  * @param startNow Whether to start the driver (or not, allowing a fluent type builder using the `withXxx()`
  *                 modifiers, and then start()` at the end.
  */
case class ElasticsearchDriver
  (hostPort: String = "localhost:9200",
   connectTimeout: String = "1 second",
   socketTimeout: String = "10 seconds",
   retryTimeout: String = "10 seconds",
  numThreads: Int = 1,
  basicAuth: Option[(String, String)] = None,
  advancedConfig: List[RestClientBuilder.HttpClientConfigCallback],
  startNow: Boolean = true
  )
  extends EsDriver
{
  private [this] val restClient: Option[RestClient] = if (startNow) {
    // Build the client is startNow is set:
    val client = for {
      _ <- Some()
      // URL
      Array(host, port) = hostPort.split(":")
      fromUrl <- Some(RestClient.builder(new HttpHost(host, port.toInt)))

      // Connect and socker timeouts
      fromTimeouts <- Some(fromUrl).map { builder =>
        builder.setRequestConfigCallback(
          new RequestConfigCallback {
            override def customizeRequestConfig(requestConfigBuilder: Builder): Builder =
              requestConfigBuilder
                .setConnectTimeout(Duration(connectTimeout).toMillis.toInt)
                .setSocketTimeout(Duration(socketTimeout).toMillis.toInt)
          }
        )
      }

      // Retry timeout
      fromRetryTimeout <- Some(fromTimeouts).map(
        _.setMaxRetryTimeoutMillis(Duration(retryTimeout).toMillis.toInt))

      fromNumThreads <- Some(fromRetryTimeout).filter(_ => numThreads > 1).orElse {
        Some(fromRetryTimeout.setHttpClientConfigCallback(new HttpClientConfigCallback {
          override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
            httpClientBuilder.setDefaultIOReactorConfig(
              IOReactorConfig.custom().setIoThreadCount(numThreads).build())
        }))
      }

      // Basic auth
      fromBasicAuth <- basicAuth.map { auth => {
        val credentialsProvider = new BasicCredentialsProvider()
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth._1, auth._2))
        fromNumThreads.setHttpClientConfigCallback(new HttpClientConfigCallback {
          override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
        })
      }}.orElse(Some(fromNumThreads))

      // Advanced config
      fromAdvancedConfig <- Some(fromBasicAuth).map { builder =>
        advancedConfig.foldLeft(builder) { (acc, v) => acc.setHttpClientConfigCallback(v) }
      }
    }
    yield (fromAdvancedConfig)

    client.map(_.build())
  }
  else None

  /** Creates a new drive with a changed URL
    *
    * @param newHostPort The new host:port
    * @return A new copy of the driver with the updated settings
    */
  def withUrl(newHostPort: String): ElasticsearchDriver = this.copy(hostPort = newHostPort)

  /** Change the retry timeout
    *
    * @param newRetryTimeout
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
  def withThreads(numThreads: Int): ElasticsearchDriver = this.copy(basicAuth = None)

  /**
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
    * @return A new copy of the driver with the updated settings
    */
  def start(): ElasticsearchDriver = this.copy(startNow = true)
}
