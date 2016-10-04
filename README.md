# Scala Elasticsearch REST driver   [![Build Status](https://travis-ci.org/Alex-At-Home/elasticsearch_scala_driver.svg?branch=master)](https://travis-ci.org/Alex-At-Home/elasticsearch_scala_driver)

## Overview

A Scala driver for Elasticsearch, built using [this REST client library](https://github.com/Alex-At-Home/rest_client_library), with the following attributes:
* Maps 1-1 to the [Elasticsearch REST API](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html): each resource is named after and maps to a single REST endpoint, with identically named parameters, etc
* Versioned - the same JAR can run multiple versions (currently: `2.3.x`)
* Runs in ScalaJS or JVM
* Reads and writes support strings, JSON, or types
   * "Bring your own JSON library" with a _very_ thin wrapper ([CIRCE](https://github.com/travisbrown/circe) supported out of the box)
   * The support for types is built in (using CIRCE). Some example types have been added, the intention is that users of the library (like me!) will add types as needed via PR, because it's so easy (see below).
* Easily extendible for modules and plugins, using the underlying [REST client library](https://github.com/Alex-At-Home/rest_client_library)
* Built on the [Elasticsearch REST driver](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html), or you can write a _very_ thin wrapper around your preferred HTTP client
* Built-in mocking support

## Example

```scala

//TODO
```

## Documentation

TODO - reference REST driver docs

TODO - gh docs

## Advanced topics

TODO mocking, adding types, adding new resources, changing JSON libraries

