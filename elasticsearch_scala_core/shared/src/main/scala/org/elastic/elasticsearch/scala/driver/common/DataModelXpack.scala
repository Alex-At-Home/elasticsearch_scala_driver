package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestBaseImplicits.CustomTypedToString
import org.elastic.rest.scala.driver.utils.NoJsonHelpers.{SimpleObjectDescription => obj}
import org.elastic.rest.scala.driver.utils.NoJsonHelpers.SimpleObjectDescription

/** Typed data model for Xpack operations
  */
trait DataModelXpack {

  /** Additional data model for Marvel config */
  object MarvelConfig {

    /** The authentication settings for remote Marvel monitoring
      * @param username The Shield username (should have the "marvel" roles)
      * @param password The raw Shield password
      */
    case class MarvelAuth(username: String, password: String) extends CustomTypedToString {
      @SimpleObjectDescription("obj",
        obj.Field("username"),
        obj.Field("password")
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** The base trait for the different Marvel exporter types */
    sealed trait MarvelExporterConfig extends CustomTypedToString

    /** Configures Marvel to send monitoring data to a remote Elasticsearch cluster
      * (recommended for production)
      * @param host The base URL of the target cluster (eg "http://cluster:9200" or "https://cluster:9244")
      * @param auth The username/password pair, for authr
      */
    case class MarvelExporterHttpConfig(host: String, auth: Option[MarvelAuth])
      extends MarvelExporterConfig
    {
      @SimpleObjectDescription("obj",
        obj.Constant("type", "http"),
        obj.Constant("enabled", true),
        obj.Field("host"),
        obj.Field("auth")
      )
      override def fromTyped: String = obj.AutoGenerated
    }
    /** Configure Marvel to send to itself */
    case class MarvelExporterLocalConfig() extends MarvelExporterConfig {
      @SimpleObjectDescription("obj",
        obj.Constant("type", "local"),
        obj.Constant("enabled", true)
      )
      override def fromTyped: String = obj.AutoGenerated
    }
    /** Configure Marvel to stop sending data */
    case class MarvelExporterDisabledConfig() extends MarvelExporterConfig {
      @SimpleObjectDescription("obj",
        obj.Constant("type", "local"),
        obj.Constant("enabled", false)
      )
      override def fromTyped: String = obj.AutoGenerated
    }
  }

  /** Typed configuration for Marvel
    * @param exporters A list of exporters that send monitoring data to this or other clusters
    * @param interval A human readable period defining how often stats are set (defaults to 10s)
    */
  case class MarvelConfig
    (
      exporters: Map[String, MarvelConfig.MarvelExporterConfig],
      interval: Option[String] = Some("10s")
    )
    extends CustomTypedToString
  {
    @SimpleObjectDescription("obj",
      obj.SimpleObject("persistent")(
        obj.Field("interval", "marvel.agent.collection."),
        obj.KeyValues("exporters", "marvel.agent.exporters.")()
      )
    )
    override def fromTyped: String =  obj.AutoGenerated
  }
}
object DataModelXpack extends DataModelXpack
