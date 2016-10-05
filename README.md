# Scala Elasticsearch REST driver   [![Build Status](https://travis-ci.org/Alex-At-Home/elasticsearch_scala_driver.svg?branch=master)](https://travis-ci.org/Alex-At-Home/elasticsearch_scala_driver)

## Overview

A Scala driver for Elasticsearch, built using [this REST client library](https://github.com/Alex-At-Home/rest_client_library), with the following attributes:
* Maps 1-1 to the [Elasticsearch REST API](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html): each resource is named after and maps to a single REST endpoint, with identically named parameters, etc
* Versioned - the same JAR can run multiple versions (currently: `2.3.x`)
* Reads and writes support strings, JSON, or types
   * "Bring your own JSON library" with a _very_ thin wrapper ([CIRCE](https://github.com/travisbrown/circe) supported out of the box)
   * The support for types is built in (using CIRCE). Some example types have been added, the intention is that users of the library (like me!) will add types as needed via PR, because it's so easy (see below).
* Easily extendible for modules and plugins, using the underlying [REST client library](https://github.com/Alex-At-Home/rest_client_library)
* Built on the [Elasticsearch REST driver](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html), or you can write a _very_ thin wrapper around your preferred HTTP client
* Built-in mocking support
* _(Currently does not run in ScalaJS, though that is a longer term goal)_

## Example

```scala
import org.elastic.elasticsearch.

// Resource-style syntax, async request, string format
latest.`/_node/stats`().read().execS()
//Future containing """{"_nodes": { "total": 1, ..."""

// Hierarchical-style syntax, sync request, JSON format
latest._node.stats.read().resultJ()
// Success(JsObject(_nodes=JsObject(total=1, ....

//TODO etc
```

## Documentation

* [Combined Documentation for the REST driver and Elasticsearch resources](https://alex-at-home.github.io/elasticsearch_scala_driver/current/index.html)
* [The top-level REST client documentation](https://github.com/Alex-At-Home/rest_client_library/blob/master/README.md)

## Advanced topics

TODO mocking, adding types, adding new resources, changing JSON libraries

### Building documentation

```bash
sbt doc/doc
```

Generates combined documentation for the REST client and Elasticsearch driver.
