package org.elastic.elasticsearch.scala.driver

import scala.concurrent.ExecutionContext

/**
  * The base operations for the Elasticsearch DSL
  */
object ElasticsearchBase {

  /**
    * Parent type for Modifiers to resources (representing URL parameters)
    */
  trait Modifier {
    /**
      * Creates the key=val to append to the URL
      *
      * @param mod The value of the modifier
      * @return A string in the format "$key=$val"
      */
    def getModifier(mod: Any): String = {
      val methodName = Thread.currentThread().getStackTrace.apply(3).getMethodName
      val paramVal = mod match {
        case s: Seq[_] => s.mkString(",")
        case toStr: AnyRef => toStr.toString
      }
      s"$methodName=$paramVal"
    }
  }

  /**
    * The base type for the different Elasticsearch drivers, eg:
    * - Mock
    * - JVM version, based on native ES driver
    * - JS version, based on TODO
    */
  trait EsDriver

  val DELETE = "DELETE"
  val GET = "GET"
  val HEAD = "HEAD"
  val POST = "POST"
  val PUT = "PUT"

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
      *
      * @param m The new modifier
      * @return The updated driver operation
      */
    def withModifier(m: String): this.type

    /**
      * Add a generic string modifier to the driver operation
      *
      * @param m The new modifier
      * @return The updated driver operation
      */
    def m(m: String): this.type = withModifier(m)

    //TODO execJ via pimp from the desired library (support scalajs)
    def execS(implicit driver: EsDriver, ec: ExecutionContext): String = null

    /**
      * Retrieves the URL for the operation on the resource with the modifiers
      * @return The URL for the operation on the resource with the modifiers
      */
    def getUrl: String = resource.location + mods.headOption.map(_ => "?").getOrElse("") + mods.mkString("&")
  }

  /**
    * The base ES resource, all the case classes should be derived from this
    */
  trait EsResource { self: Product =>

    /**
      * Internal implementation to retrieve the location of the resource, the first time `location` is accessed
      * Replaces $xxx with the correspodning case class parameter, in order
      *
      * @return The location of the resource
      */
    private[this] def locationImpl: String = {
      def formatVal(a: Any) = a match {
        case s: Seq[_] => s.mkString(",")
        case toStr: AnyRef => toStr.toString
      }
      val locationTemplate = productPrefix.replace("`", "")
      val splits = locationTemplate.split("/")
      if (splits.length > 0) {
        splits.foldLeft(("", 0)) { case ((acc, i), v) =>
          v.headOption
            .filter(_ == '$')
            .map(_ => (acc + "/" + formatVal(productElement(i)), i + 1))
            .getOrElse((acc + "/" + v, i))
        }._1.tail
      }
      else locationTemplate
    }
    /**
      * The location of the resource, generated from the classname
      * by substituting in the field names
      */
    lazy val location = locationImpl
  }

  ///////////////////////////////////////////////////////////////////

  //TODO remove everything under here once refactor is complete

  /**
    * The base readable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsReadable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @return The driver opertion
      */
    def read(): D
  }

  /**
    * The base readable type where the reply is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsReadable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def read(body: String): D
  }

  /**
    * The base writable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritable[D <: BaseDriverOp] {

    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def write(body: String): D
  }

  /**
    * The base deletable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsDeletable[D <: BaseDriverOp] {

    def delete(): D
  }

  /**
    * The base deletable type where the delete is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsDeletable[D <: BaseDriverOp] {
    /**
      * Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def delete(body: String): D
  }
}