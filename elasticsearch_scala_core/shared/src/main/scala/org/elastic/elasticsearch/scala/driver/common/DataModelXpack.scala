package org.elastic.elasticsearch.scala.driver.common

import org.elastic.rest.scala.driver.RestBaseImplicits.CustomTypedToString

/** Typed data model for Xpack operations
  */
trait DataModelXpack {

  /** Additional data model for Marvel config */
  object MarvelConfig {

    sealed trait MarvelExporterConfig extends CustomTypedToString

    /** Configures Marvel to send monitoring data to a remote Elasticsearch cluster
      * (recommended for production)
      * @param host The base URL of the target cluster (eg "http://cluster:9200" or "https://cluster:9244")
      * @param auth The username/password pair, for authr
      */
    case class MarvelExporterHttpConfig(host: String, auth: Option[(String, String)])
      extends MarvelExporterConfig
    {
      override def fromTyped = {
        val authBlock = auth.map { case (u, p) => s""" , "auth": { "username": "$u", "password": "$p" } """ }
        s"""{ "type": "http", "enabled": true, "host": "$host" ${authBlock.getOrElse("")} } """
      }
    }
    /** Configure Marvel to send to itself */
    case class MarvelExporterLocalConfig() extends MarvelExporterConfig {
      override def fromTyped = {
        s"""{ "type": "local", "enabled": true } """
      }
    }
    /** Configure Marvel to stop sending data */
    case class MarvelExporterDisabledConfig() extends MarvelExporterConfig {
      override def fromTyped = {
        s"""{ "type": "local", "enabled": false } """
      }
    }
  }

  /** Typed configuration for Marvel
    * @param exporters A list of exporters that send monitoring data to this or other clusters
    * @param interval A human readable period defining how often stats are set (defaults to 10s)
    */
  case class MarvelConfig
    (
      exporters: Map[String, MarvelConfig.MarvelExporterConfig],
      interval: Option[String]
    )
    extends CustomTypedToString
  {
    override def fromTyped = {

      val exporterString = exporters match {
        case m: Map[_, _] if m.isEmpty  => ""
        case _ =>
          exporters.map { case (k, v) =>
            s""" "marvel.agent.exporters.$k": ${v.fromTyped} """
          }.mkString(" , ") + " , "
      }
      val intervalString =  s""" "marvel.agent.collection.interval": "${interval.getOrElse("10s")}" """

      s"""{ "persistent": { $exporterString $intervalString } }"""
    }
  }

}
object DataModelXpack extends DataModelXpack
