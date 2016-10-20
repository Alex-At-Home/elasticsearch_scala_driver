# Scala Elasticsearch REST driver [![Build Status](https://travis-ci.org/Alex-At-Home/elasticsearch_scala_driver.svg?branch=master)](https://travis-ci.org/Alex-At-Home/elasticsearch_scala_driver) [![Scala.js](http://scala-js.org/assets/badges/scalajs-0.6.8.svg)](http://scala-js.org)

## Overview

A Scala driver for Elasticsearch, built using [this REST client library](https://github.com/Alex-At-Home/rest_client_library), with the following attributes:
* Maps 1-1 to the [Elasticsearch REST API](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html): each resource is named after and maps to a single REST endpoint, with identically named parameters, etc
* Versioned - the same JAR can run multiple versions (currently: `2.3.x`)
* Read and write strings, JSON, or types
   * "Bring your own JSON library" with a _very_ thin wrapper ([CIRCE](https://github.com/travisbrown/circe) supported out of the box)
   * The support for types is built in (using ([CIRCE](https://github.com/travisbrown/circe), via a slightly less thin wrapper). Some example types have been added, the intention is that users of the library (like me!) will add types as needed via PR, because it's so easy (see below).
* Easily extensible for modules and plugins, using the underlying [REST client library](https://github.com/Alex-At-Home/rest_client_library)
* Built on the [Elasticsearch REST driver](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html), or you can write a _very_ thin wrapper around your preferred HTTP client
* Runs in [Scala.JS](https://www.scala-js.org/) as well as in the JVM
* Built-in mocking support

## Examples

Run `sbt elasticsearch_scala_shell/console`, and then:

```scala
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.RestBaseImplicits._
import org.elastic.rest.scala.driver.RestResources._
import org.elastic.elasticsearch.scala.driver.versions.Versions.latest
import org.elastic.elasticsearch.scala.driver.jvm.ElasticsearchDriver
import scala.concurrent.ExecutionContext.Implicits.global

implicit val client = (new ElasticsearchDriver()).withUrls("http://localhost:9200").withBasicAuth("user", "password").start() 

// Resource-style syntax, async request, string format
latest.`/_nodes/stats`().read().execS()
//Future containing """({"cluster_name":"<<CLUSTER_NAME>>","nodes":{"<<NODE1_ID>>":{"timestamp":1476827049189...

import org.elastic.rest.scala.driver.json.CirceJsonModule._

// Hierarchical-style syntax, sync request, JSON format
latest()._nodes.stats.read().resultJ()
// Success(io.circe.Json({"cluster_name":"<<CLUSTER_NAME>>","nodes":{"<<NODE1_ID>>":{"timestamp":1476827049189...

import org.elastic.rest.scala.driver.json.CirceTypeModule._

// Hierarchical-style syntax, async request, typed outputs
latest().read().exec()
// Future containing 
// ElasticsearchInfo(<<HOST_NAME>>,<<CLUSTER_NAME>>,VersionInfo(2.3.4,<<BUILD_HASH>>,2016-06-30T11:24:31Z,false,5.5.0),You Know, for Search)))

//TODO use result here? or do i have another typed example?

// typed input - bulk indexing operations

//TODO bulk indexing example

// Resource-style syntax, sync request, typed input - custom view over settings

//TODO import for (Xpack) data model here?

val monitorConfig = MarvelConfig(Map("test"->MarvelExporterLocalConfig()), interval = Some("300s"))

//(example deserialization):
monitorConfig.fromTyped
// """ { "persistent": {  "marvel.agent.exporters.test": { "type": "local", "enabled": true }   ,   "marvel.agent.collection.interval": "300s"  } } """

latest.`/_cluster/settings#marvel.agent`().write(monitorConfig).resultJ()

```

## Import the elasticsearch driver

Add the following to your `build.sbt` or `Build.scala` (assuming running in the JVM not ScalaJS):
* `val es_driver = ProjectRef(uri("https://github.com/Alex-At-Home/elasticsearch_scala_driver#2.3.0", "elasticsearch_scala_java_clientJVM")`
   * (or whatever version/branch is desired after the `#`)
* `.dependsOn(es_driver)` to any projects that will use the driver.

For ScalaJS it is similar, except the following 2 projects are required:
* `val es_driver = ProjectRef(uri("https://github.com/Alex-At-Home/elasticsearch_scala_driver#2.3.0", "elasticsearch_scala_coreJS")`
* `val rest_client = ProjectRef(uri("https://github.com/Alex-At-Home/rest_client_library#1.0.0", "rest_http_clientJS")`

More differences when using ScalaJS are provided below.

## Documentation

* [Combined Documentation for the REST driver and Elasticsearch resources](https://alex-at-home.github.io/elasticsearch_scala_driver/current/index.html)
* [The top-level REST client documentation, contains more examples of using the generic portions of the API](https://github.com/Alex-At-Home/rest_client_library/blob/master/README.md)

## Advanced topics

TODO mocking, adding types, adding new resources, changing JSON libraries

### Building documentation

```bash
sbt doc/doc
```

Generates combined documentation for the REST client and Elasticsearch driver.
