# Scala Elasticsearch REST driver

TODO

# Scala REST driver

## Overview

This is a small library designed to make it easy to build clients/drivers for (JSON) REST-based services:
* Almost exclusively declarative, no client-specific business logic
* Optional typing, natively supports strings and "bring-your-own-JSON" (`*`)
   * (`*`) This currently comes with some caveats, see below under TODO_LINK
* Versionable
* "Bring-your-own-HTTP-client"

Note that the underlying idea is that these clients map very tightly to the REST endpoints exposed by the target service. This has two implications:
* To understand a service, you only need to understand the REST interface (vs both the REST interface and the scala/java/whatever API)
   * The counterpoint is that you could argue that manipulating REST-like resources is not a very idiomatic (/good) way of writing code, which is fair. If the examples below don't convince you this is a good idea, then this is not the library for you, which is fine!
* Taking this approach makes it far easier to define and maintain the API, which is of course the whole point of this library!

## Some examples

TODO
