package org.elastic.rest.scala.driver.util

import org.elastic.rest.scala.driver.ResourceOperations._
import org.elastic.rest.scala.driver.RestBase._

/**
  * Useful sample REST resources for testing
  */
object SampleResources {

  /** No modifiers are supported for these parameters */
  trait NoParams extends BaseDriverOp

  /** Read-only root URI
    *
    */
  case class `/`() extends RestResource
    with RestReadable[NoParams]
  {
    override lazy val location = "/"
  }

  /** Allows for generic access to the ES client - any URI string, any operation, and any modifier
    *
    * @param uri The resource name (including the leading '/')
    */
  case class `/$resource`(uri: String) extends RestResource
    with RestReadable[NoParams] with RestWithDataReadable[NoParams] with RestCheckable[NoParams]
    with RestSendable[NoParams] with RestWritable[NoParams] with RestNoDataWritable[NoParams]
    with RestDeletable[NoParams] with RestWithDataDeletable[NoParams]
  {
    override lazy val location = uri
  }

  //TODO have typed versions
}
