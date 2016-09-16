package org.elastic.rest.scala.driver

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.runtime.universe._

//TODO have a version of exec/S/J that waits N seconds and returns the future

/**
  * The base operations for the Elasticsearch DSL
  */
object RestBase {

  /** The exception type generated by REST errors
    *
    * @param code The HTTP error code
    * @param message A message summarizing the error (may be empty if no summary can be inferred)
    * @param body The body of the error, if present
    */
  case class RestServerException(code: Int, message: String, body: Option[String]) extends Exception(message)

  /** The exception generated by bad inputs
    *
    * @param message A message summarizing the error (may be empty if no summary can be inferred)
    */
  case class RestRequestException(message: String) extends Exception(message)

  /** A trait of `BaseDriverOp` that indicates the typed return type of an operation
    *
    * @tparam T The type of the operation return
    */
  trait TypedOperation[T] { self: BaseDriverOp =>

    /**
      * Evidence for the type of the operation
      */
    protected implicit val ct: WeakTypeTag[T]

    /** Actually executes the operation
      *
      * @param stringToTypedHelper An implicit helper to convert the op return to a type
      * @param driver The driver which executes the operation
      * @param ec The execution context for futures
      * @return A future containing the result of the operation as a type
      */
    def exec
      ()
      (implicit stringToTypedHelper: StringToTypedHelper,
       driver: RestDriver,
       ec: ExecutionContext
      )
      : Future[T] =
        self.execS().map(stringToTypedHelper.toType(_)(ct))
  }

  /** Case classes that want a custom overwrite should inherit this trait and implement
    * `fromTyped`, bypasses needing a JSON library with an overridden serializer etc etc
    */
  trait CustomTypedToString {
    /** Converts self to JSON string
      *
      * @return self as JSON string
      */
    def fromTyped: String
  }

  /** Classes that want a custom overwrite as return types should inherit this trait
    * and implement `toType`, typically still use a JSON library, eg to wrap a JSON element
    *  and provide helpers
    */
  trait CustomStringToTyped {
    //TODO: not sure how to do this? Assume there is a ctor that takes a pure string
  }

  /** A trait to be implemented and used as an implicit to define how to go from a typed object
    * (eg case class) to a string, normally via JSON unless derived from `CustomTypedToString`
    * To handle `CustomTypedToString`, `fromTyped` should check for `T` being an instance of that
    * and handle it separately
    */
  trait TypedToStringHelper {
    /** Helper to convert from a typed (Case class) object to a string, see above
      * remarks about handling `T` that is inherited from `CustomTypedToString`
      *
      * @param t Typed object
      * @tparam T The type
      * @return The JSON string representing `t`
      */
    def fromTyped[T](t: T)(implicit ct: WeakTypeTag[T]): String
  }

  /** A trait to be implemented and used as an implicit to indicate how to go from a
    * JSON string (ie a return from an operation) to a typed (case class) object
    * Note that the overridden `toType` should check `ct.tpe <:< CustomStringToTyped` and
    * simply return `x.asInstanceOf[CustomStringToTyped].toType(s)` in such cases
    */
  trait StringToTypedHelper {

    /** Helper to convert from a JSON string to a typed (case class) object
      *
      * @param s String return
      * @param ct The type tag associated with the type
      * @tparam T The desired type of the return operation
      * @return An object of type `T`
      */
    def toType[T](s: String)(implicit ct: WeakTypeTag[T]): T
  }

  /**
    * A trait to be implemented and used as an implicit to define how to go from JSON to string
    * (defaults to `j.toString`)
 *
    * @tparam J The json object type in this library
    */
  trait JsonToStringHelper[J] {
    /** Creates a String from the JSON object of the registered (via implicit) JSON lib
      * (probably can normally be `j.toString`, which is therefore left in as a default)
      *
      * @param j The JSON object
      * @return The JSON
      */
    def fromJson(j: J): String = j.toString
  }

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
    * The base type for the different REST drivers, eg:
    * - Mock
    * - JVM version, based on native ES driver
    * - JS version, based on TODO
    */
  trait RestDriver {
    /** Executes the designated operation
      *
      * @param baseDriverOp The operation to execute
      * @return A future returning the raw reply or throws `RequestException(code, body, message)`
      */
    def exec(baseDriverOp: BaseDriverOp): Future[String]
  }

  val DELETE = "DELETE"
  val GET = "GET"
  val HEAD = "HEAD"
  val POST = "POST"
  val PUT = "PUT"

  /** The base type for driver operations
    * gets mixed in with different traits derived from `Modifier`
    * Gets a concrete case class for each set of such `Modifier`s which are then
    * mixed into the the `RestResource` instances
    */
  trait BaseDriverOp {
    /** The resource that is being operated upon
      */
    val resource: RestResource
    /** The operation type if translated to REST
      */
    val op: String
    /** If the operation involves writing data to the resource, this is that data
      */
    val body: Option[String]

    /** The set of modifications, typically filled in by the `Modifier` traits
      * (but modifiers can also be manually generated using `withModifier`)
      */
    val mods: List[String]

    /** A set of per operation headers added to the request
      */
    val headers: List[String]

    /** Add a generic string modifier to the driver operation
      *
      * @param m The new modifier
      * @return The updated driver operation
      */
    protected def withModifier(m: String): this.type

    /** Add a generic string header to the driver operation
      *
      * @param h The new header
      * @return The updated driver operation
      */
    protected def withHeader(h: String): this.type = null //TODO: remove this "=null" once the old modifiers have been removed

    /** Add a generic string modifier to the driver operation
      *
      * @param m The new modifier
      * @return The updated driver operation
      */
    def m(m: String): this.type = withModifier(m)

    /** Add a generic string header to the driver operation
      *
      * @param h The new header
      * @return The updated driver operation
      */
    def h(h: String): this.type = withHeader(h)

    /**
      * Actually executes the operation
 *
      * @param driver The driver which executes the operation
      * @return A future containing the result of the operation as a string
      */
    def execS()(implicit driver: RestDriver): Future[String] = driver.exec(this)

    /** Retrieves the URL (including params) for the operation on the resource with the modifiers
 *
      * @return The URL (including params) for the operation on the resource with the modifiers
      */
    def getUrl: String = resource.location + mods.headOption.map(_ => "?").getOrElse("") + mods.reverse.mkString("&")

    /** Retrieves the URL (no params) for the operation on the resource with the modifiers
 *
      * @return The URL (no params) for the operation on the resource with the modifiers
      */
    def getPath: String = resource.location + mods.headOption.map(_ => "?").getOrElse("") + mods.reverse.mkString("&")
  }
  object BaseDriverOp {
    /** Extractor for `BaseDriverOp`
      *
      * @param op The operation to decompose
      * @return The 5-tupe
      */
    def unapply(op: BaseDriverOp) = Some((op.resource, op.op, op.body, op.mods, op.headers))
  }

  /** The base ES resource, all the case classes should be derived from this
    */
  trait RestResource { self: Product =>

    /** Internal implementation to retrieve the location of the resource, the first time `location` is accessed
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
      if (splits.nonEmpty) {
        splits.foldLeft(("", 0)) { case ((acc, i), v) =>
          v.headOption
            .filter(_ == '$')
            .map(_ => (acc + "/" + formatVal(productElement(i)), i + 1))
            .getOrElse((acc + "/" + v, i))
        }._1.tail
      }
      else locationTemplate
    }
    /** The location of the resource, generated from the classname
      * by substituting in the field names
      */
    lazy val location = locationImpl
  }

  ///////////////////////////////////////////////////////////////////

  //TODO remove everything under here once refactor is complete

  /** The base readable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsReadable[D <: BaseDriverOp] {
    /** Creates a driver operation
      *
      * @return The driver opertion
      */
    def read(): D
  }

  /** The base readable type where the reply is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsReadable[D <: BaseDriverOp] {
    /** Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def read(body: String): D
  }

  /** The base writable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsWritable[D <: BaseDriverOp] {

    /** Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def write(body: String): D
  }

  /** The base deletable type
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait EsDeletable[D <: BaseDriverOp] {

    def delete(): D
  }

  /** The base deletable type where the delete is controlled by data written to the resource
    *
    * @tparam D The group of modifier operations supported mixed into the `BaseDriverOp`
    */
  trait WithDataEsDeletable[D <: BaseDriverOp] {
    /** Creates a driver operation
      *
      * @param body The data to write to the resource
      * @return The driver opertion
      */
    def delete(body: String): D
  }
}