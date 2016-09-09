package org.elastic.elasticsearch.driver.scala

import org.elastic.elasticsearch.driver.scala.Modifiers._
import org.elastic.rest.scala.driver.RestBase
import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, RestResource}

/**
  * Groups of modifiers used in the Elasticsearch DSL
  */
object ModifierGroups {

  /**
    * A driver op with no typed modifiers other than the standard `?pretty` ahd `?human`
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndRoutingDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Routing
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndFieldsDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Fields
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndConflictDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Conflict
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }
  /**
    * A driver op with no typed modifiers other than standard and `?verbose`
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class PrettyAndVerboseDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Verbose
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }
  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FullyModifiableReadDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with SourceBase with SourceInclude with SourceExclude with Fields
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FullyModifiableWriteDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Version with OpType with Routing with Parent with Timeout
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FullyModifiableDeleteDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Routing with Parent with Refresh with Timeout
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class QueryUriDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human
      with Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with DefaultOperator with Lenient with Explain
      with SourceQuery with SourceInclude with SourceExclude
      with Fields with Sort with TrackScores with Timeout with TerminateAfter
      with From with Size with SearchType
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class QueryDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human
      with Timeout with RequestCache with TerminateAfter
      with From with Size with SearchType
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class QuerySearchShardsDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Routing with Preference with Local
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class QueryCountDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human
      with Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with DefaultOperator with Lenient
      with TerminateAfter
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class QueryMiscDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human
      with Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with DefaultOperator with Lenient
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class QueryExplainDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human
      with Query with DefaultField with Analyzer with LowercaseExpandedTerms
      with AnalyzeWildcard with DefaultOperator with Lenient
      with SourceQuery with SourceInclude with SourceExclude
      with Fields with SourceBase
      with Routing with Parent with Preference
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class SearchFieldStatsDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Level with Fields
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class OpenCloseIndexesDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with IgnoreUnavailable
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class IndexStatsDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Level with Groups with Types
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class IndexRecoveryDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Detailed with ActiveOnly
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class ShardStoreDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with Status
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class FlushDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with WaitIfOngoing with Force
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class ForceMergeDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with MaxNumSegments with OnlyExpungeDeletes with Flush
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }

  /**
    * TODO
    * @param resource
    * @param op
    * @param body
    * @param mods
    */
  case class UpgradeDriverOp
  (resource: RestResource, op: String, body: Option[String], mods: List[String], headers: List[String])
    extends BaseDriverOp
      with Pretty with Human with OnlyAncientSegments
  {
    override def withModifier(m: String): this.type = copy(mods = m :: mods).asInstanceOf[this.type]
  }
}
