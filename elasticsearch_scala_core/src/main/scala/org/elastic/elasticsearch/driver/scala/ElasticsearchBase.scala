package org.elastic.elasticsearch.driver.scala

import scala.concurrent.ExecutionContext

/**
  * The base operations for the Elasticsearch DSL
  */
object ElasticsearchBase {

  /**
    * The base type for the different Elasticsearch drivers, eg:
    * - Mock
    * - JVM version, based on native ES driver
    * - JS version, based on TODO
    */
  trait EsDriver

  //TODO: scaladoc for these
  val GET = "GET"
  val POST = "POST"
  val PUT = "PUT"
  val DELETE = "DELETE"

  /**
    * The base type for driver operations
    * gets mixed in with different traits derived from `Modifier`
    * Gets a concrete case class for each set of such `Modifier`s which are then
    * mixed into the the `EsResource` instances
    */
  trait BaseDriverOp {
    /**
      * The resource that is being operated upon
      */
    val resource: EsResource
    /**
      * The operation type if translated to REST
      */
    val op: String
    /**
      * If the operation involves writing data to the resource, this is that data
      */
    val body: Option[String]

    /**
      * The set of modifications, typically filled in by the `Modifier` traits
      * (but modifiers can also be manually generated using `withModifier`)
      */
    val mods: List[String]

    /**
      * Add a generic string modifier to the driver operation
      * (Has to be overridden by each group, always via:
      *  "override def withModifier(m: String): this.type = copy(mods = m :: mods)")
      * @param m The new modifier
      * @return The updated driver operation
      */
    def withModifier(m: String): this.type

    /**
      * Add a generic string modifier to the driver operation
      * @param m The new modifier
      * @return The updated driver operation
      */
    def ?(m: String): this.type = withModifier(m)

    //TODO execJ via pimp from the desired library (support scalajs)
    def execS(implicit driver: EsDriver, ec: ExecutionContext): String = null
  }


  /**
    * The base readable type
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsReadable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      * @return The driver opertion
      */
    def read(): D
  }

  /**
    * The base readable type where the reply is controlled by data written to the resource
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsReadable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def read(body: String): D
  }

  /**
    * The base writable type
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritable[D <: BaseDriverOp] {

    /**
      * Creates a driver operation
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def write(body: String): D
  }

  /**
    * The base deletable type
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsDeletable[D <: BaseDriverOp] {

    def delete(): D
  }

  /**
    * The base deletable type where the delete is controlled by data written to the resource
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsDeletable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def delete(body: String): D
  }

  /**
    * The base ES resource, all the case classes should be derived from this
    */
  trait EsResource {

    /**
      * The location of the resource, generated from the classname
      * by substituting in the field names
      */
    def location: String = "" //TODO
  }


  // Resource operations
  //TODO: typed reads
  //  trait TypedReadable[T] extends Readable { self =>
  //    override def read() = new TypedDriverOperation(None, op, self)
  //  }
  //  trait Readable { self =>
  //    val op: String = GET
  //    def read() = new DriverOperation(None, op, self)
  //  }


}
