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
   * The counterpoint is that you could argue that manipulating REST-like resources is not a very idiomatic (/good) way of writing code, which is fair. If the examples below don't convince you this is a good idea, then this is may not be the library for you, which is fine!
* Taking this approach makes it far easier to define and maintain the API, which is of course the whole point of this library!

## Some examples

Let's say you have a fairly standard REST service:
* `POST` JSON to `/database/users` to create a new user
* `GET` `/database/users/<<userId>>` to get a user, with (eg) the modifier `?pretty=true` used to return prettified JSON
* `PUT` JSON to `/database/users/<<userId>>` to update a user
* `DELETE` `/database/users/<<userId>>` to delete a user
* `POST` with no body to `/database/users/_synchronize` to delete expired users (or whatever)  

Then you would declare this API using `scala_rest_driver` as follows:

```
object ApiModel {
  import org.elastic.rest.scala.RestBase._
  import org.elastic.rest.scala.RestResources._

  case class `/database/users`() 
    extends RestSendable[BaseDriverOp]
    with RestResource

  case class `/database/users/$userId`(userId: String) 
    extends RestReadable[PrettyModifierGroup]
    with RestWritable[BaseDriverOp]
    with RestDeletable[BaseDriverOp]
    with  RestResource

  case class `/database/users/_synchronize`()
    extends RestNoDataWritable[BaseDriverOp]
    with RestResource

  trait PrettyModifierGroup extends PrettyModifier with BaseDriverOp
  trait PrettyModifier extends Modifier {
    def pretty(b: Boolean) = ???
  }
}
```

And that's it! Then you can use it as follows:

```
  import ApiModel
  
  val implicit driver = ??? // see below
  val createRequest = `/database/users`.send(""" { "name": "Alex" } """)
  val createReply: Future[String] = createRequest.execS() //or execJ to get JSON
  // {"name":"Alex"}
  val getRequest = `/database/users/$userId`("Alex").pretty(true)
  val getReply = createReply.flatMap(_ => getRequest.execS())
  // {\n\t"name": "Alex"\n}
```

TODO
