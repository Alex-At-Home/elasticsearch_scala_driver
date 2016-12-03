package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.SearchModifiers.MultiSearchDeclaration
import org.elastic.elasticsearch.scala.driver.utils.BulkUtils
import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, Constant, ToStringAnyVal}
import org.elastic.rest.scala.driver.RestBaseImplicits.CustomTypedToString
import org.elastic.rest.scala.driver.utils.NoJsonHelpers.{SimpleObjectDescription => obj}
import org.elastic.rest.scala.driver.utils.NoJsonHelpers.SimpleObjectDescription

/** Value class for script languages, including the defaults */
case class ScriptLang(value: String) extends AnyVal with ToStringAnyVal[String]
/** Default provided script languages */
object ScriptLang {
  @Constant val groovy, expression, moustache = ToStringAnyVal.AutoGenerate[ScriptLang]
}
/** Value class for determining the sort order */
case class SortOrder(value: String) extends AnyVal with ToStringAnyVal[String]
/** Available sort orders */
object SortOrder {
  @Constant val asc, desc = ToStringAnyVal.AutoGenerate[SortOrder]
}

/** Data model types used by the search resources
  */
trait DataModelSearch {

  /** The base trait that all `_search` queries return - implemented in the different JSON modules */
  trait SearchResultsBase

  /** The base trait that query bodies should mix-in, this lets eg JSON modules supply their own (vs `QueryBody`) */
  trait QueryBodyBase extends CustomTypedToString

  /** A case class representing the query to execute
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-body.html Docs]]
    *
    * @param query The query term or terms that comprise the query
    * @param timeout A search timeout, bounding the search request to be executed within the specified time value and
    *                bail with the hits accumulated up to that point when expired. Defaults to no timeout.
    * @param from To retrieve hits from a certain offset. Defaults to 0.
    * @param size The number of hits to return. Defaults to 10. If you do not care about getting some hits back
    *             but only about the number of matches and/or aggregations, setting the value to 0 will help
    *             performance.
    * @param terminate_after The maximum number of documents to collect for each shard, upon reaching which the query
    *                        execution will terminate early. If set, the response will have a boolean field
    *                        terminated_early to indicate whether the query execution has actually terminated_early.
    *                        Defaults to no terminate_after.
    * @param sort A list of sort configurations to apply to docs matching `query`
    *             (see [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html Docs]])
    * @param track_scores When sorting on a field, scores are not computed. By setting track_scores to true,
    *                     scores will still be computed and tracked.
    *                     (see [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#_track_scores Docs]])
    * @param _source Allows to control how the _source field is returned with every hit.
    *                By default operations return the contents of the _source field unless you have used the fields
    *                parameter or if the _source field is disabled.
    *                If set to Left(false), will disable `_source` retrieval
    *                If set to a list of Strings, will apply them as wildcard patterns to control what parts of the
    *                `_source` to return
    *                [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-source-filtering.html Docs]]
    * @param fields The fields parameter is about fields that are explicitly marked as stored in the mapping, which is
    *               off by default and generally not recommended. Use source filtering instead to select subsets of
    *               the original source document to be returned.
    *               [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-fields.html Docs]]
    * @param script_fields Allows to return a script evaluation (based on different fields) for each hit
    *                      [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-script-fields.html Docs]]
    * @param fielddata_fields Allows to return the field data representation of a field for each hit.
    *                         It’s important to understand that using the fielddata_fields parameter will cause the
    *                         terms for that field to be loaded to memory (cached), which will result in more memory
    *                         consumption
    *                         [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-fielddata-fields.html Docs]]
    * @param post_filter The post_filter is applied to the search hits at the very end of a search request, after aggregations
    *                    have already been calculated. Allows for eg aggregations to work on a larger dataset than is
    *                    returned from the query
    *                    [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-post-filter.html Docs]]
    * @param highlight Allows to highlight search results on one or more fields. The implementation uses either the
    *                  lucene highlighter, fast-vector-highlighter or postings-highlighter.
    *                  [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html Docs]]
    * @param rescore Rescoring can help to improve precision by reordering just the top (eg 100 - 500) documents
    *                returned by the query and post_filter phases, using a secondary (usually more costly) algorithm,
    *                instead of applying the costly algorithm to all documents in the index.
    *                [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-rescore.html Docs]]
    * @param explain Enables explanation for each hit on how its score was computed
    *                [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-explain.html Docs]]
    * @param version Returns a version for each search hit.
    *                [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-version.html Docs]]
    * @param indices_boost Allows to configure different boost level per index when searching across more than one
    *                      indices. This is very handy when hits coming from one index matter more than hits coming
    *                      from another index (think social graph where each user has an index).
    *                      [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-index-boost.html Docs]]
    * @param min_score Exclude documents which have a _score less than the minimum specified in min_score
    *                  (Note, most times, this does not make much sense, but is provided for advanced use cases.)
    *                  [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-min-score.html Docs]]
    * @param aggregations A map of aggregation names vs definitions
    *                     [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-aggregations.html Docs]]
    * @param other_fields A map of other (eg currently unknown) parameters
    *                     (eg can currently be used to define a top-level inner hit definition
    *                     [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-inner-hits.html Example Docs]])
    */
  case class QueryBody
    (query: Option[qb.QueryElement] = None,
     timeout: Option[String] = None,
     from: Integer = 0,
     size: Integer = 10,
     terminate_after: Option[Integer] = None,
     sort: Seq[QueryBody.SortConfigBase] = Seq(),
     track_scores: Boolean = false,
     _source: Either[Boolean, Seq[String]] = Left(true),
     fields: Seq[String] = Seq(),
     script_fields: Map[String, QueryBody.ScriptFieldConfig] = Map(),
     fielddata_fields: Seq[String] = Seq(),
     post_filter: Option[qb.QueryElement] = None,
     highlight: Option[QueryBody.HighlightConfig] = None,
     rescore: Seq[QueryBody.RescoreConfig] = Seq(),
     explain: Boolean = false,
     version: Boolean = false,
     indices_boost: Map[String, Double] = Map(),
     min_score: Option[Double] = None,
     aggregations: Map[String, ab.AggregationElement] = Map(),
     other_fields: Map[String, QueryBody.QueryConfigBase] = Map()
    )
    extends QueryBodyBase
  {
    @SimpleObjectDescription("obj",
      obj.Field("query"),
      obj.Field("timeout"),
      obj.Field("size"),
      obj.Field("from"),
      obj.Field("terminate_after"),
      obj.MultiTypeField("sort"),
      obj.Field("track_scores"),
      obj.Field("_source"),
      obj.MultiTypeField("fields"),
      obj.MultiTypeField("script_fields"),
      obj.MultiTypeField("fielddata_fields"),
      obj.Field("post_filter"),
      obj.Field("highlight"),
      obj.MultiTypeField("rescore"),
      obj.Field("explain"),
      obj.Field("version"),
      obj.MultiTypeField("indices_boost"),
      obj.Field("min_score"),
      obj.MultiTypeField("aggregations"),
      obj.KeyValues("other_fields")()
    )
    override def fromTyped: String = obj.AutoGenerated
  }

  /** Contains sub-objects of the query body */
  object QueryBody {

    /** A base trait for arbitrary definition of JSON elements in the query body */
    trait QueryConfigBase

    /** A misc query config that can take a JSON object string (eg generated by `json.asString` from whatever JSON library
      * is being used, and will insert it into the sort array unchanged). Note that no validation is performed, ie
      * if the string is invalid JSON or not a valid query body config a runtime error will occur.
      * @param jsonStr String representation of a valid JSON object
      */
    case class RawQueryConfig(jsonStr: String) extends CustomTypedToString with QueryConfigBase {
      override def fromTyped: String = jsonStr
    }

    //////////////////////

    // Sorting

    /** The base trait for the different ways in which objects can be sorted (not sealed to allow for user extensions) */
    trait SortConfigBase

    /** The simplest sort configuration - sorts of the specified `field` in ascending order (or descending order if the
      * field is `_score`)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html Docs]]
      * @param field The field on which to sort (dot notation supported for nested fields)
      */
    case class SimpleSortConfig(field: String) extends CustomTypedToString with SortConfigBase {
      override def fromTyped: String = s""""$field""""
    }

    /** The full sort configration, with various options (defaulted where possible)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html Docs]]
      * @param field The field on which to sort (dot notation supported for nested fields)
      * @param order A string representing the order (should be "asc" or "desc")
      *              Defaults to `"desc"` if `field == "_score"`, `"asc"` otherwise.
      * @param mode The mode, should be one of "min", "max", "sum", "avg", "median"
      *             (See [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#_sort_mode_option Docs]])
      * @param nested_path For supporting sorting by fields that are inside one or more nested objects
      *                    Defines on which nested object to sort. The actual sort field must be a direct field inside
      *                    this nested object. When sorting by nested field, this field is mandatory.
      *                    (See [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#nested-sorting Docs]])
      * @param nested_filter For supporting sorting by fields that are inside one or more nested objects
      *                      A filter that the inner objects inside the nested path should match with in order for its
      *                      field values to be taken into account by sorting.
      *                      Common case is to repeat the query / filter inside the nested filter or query.
      *                      By default no nested_filter is active.
      *                      (See [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#nested-sorting Docs]])
      * @param missing The missing parameter specifies how docs which are missing the field should be treated: The
      *                missing value can be set to `"_last"`, `"_first"`, or a custom value (that will be used for
      *                missing docs as the sort value).
      * @param unmapped_type By default, the search request will fail if there is no mapping associated with a field.
      *                      The unmapped_type option allows to ignore fields that have no mapping and not sort by them.
      *                      The value of this parameter is used to determine what sort values to emit.
      */
    case class FullSortConfig
      (field: String,
       order: Option[SortOrder] = None,
       mode: Option[String] = None,
       nested_path: Option[String] = None,
       nested_filter: Option[qb.QueryElement] = None,
       missing: Option[String] = None,
       unmapped_type: Option[String] = None)
      extends CustomTypedToString with SortConfigBase
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("field")(
          obj.Field("order"),
          obj.Field("mode"),
          obj.Field("nested_path"),
          obj.Field("nested_filter"),
          obj.Field("missing"),
          obj.Field("unmapped_type")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    //TODO for geo have to use Raw for now (https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#geo-sorting)

    /** Allow to sort based on custom scripts
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#_script_based_sorting Docs]]
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/modules-scripting.html Inline vs File scripting]]
      * @param `type` The type of the script in terms of how it is treated for sorting: should be one of
      *               `"number"` or `"string"`
      * @param inline (Only one of `inline` or `file` should be specified) Lets the user enter a script directly into
      *                the query body
      * @param file (Only one of `inline` or `file` should be specified) Refers to a registered script file
      * @param lang The language (eg `groovy`, `expression`, `mustache`), defaults to `groovy`
      * @param params A generic set of parameters that are passed into the script
      * @param order The order in which the docs should be sorted according to the results of the script, should be one
      *              of `"asc"` or `"desc"`
      */
    case class ScriptSortConfig
      (`type`: String,
       inline:  Option[String] = None,
       file: Option[String] = None,
       lang: Option[ScriptLang] = None,
       params: Map[String, Any] = Map(),
       order: Option[SortOrder] = None)
      extends CustomTypedToString with SortConfigBase
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("_score")(
          obj.SimpleObject(
            obj.Field("`type`"),
            obj.Field("order"),
            obj.SimpleObject("script")(
              obj.Field("inline"),
              obj.Field("file"),
              obj.Field("lang"),
              obj.MultiTypeField("params")
            )
          )
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** A sort config that can take a JSON object string (eg generated by `json.asString` from whatever JSON library
      * is being used, and will insert it into the sort array unchanged). Note that no validation is performed, ie
      * if the string is invalid JSON or not a valid sort config a runtime error will occur.
      * Eg until it has an element definition, this can be used to build `geo` sorting:
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-sort.html#geo-sorting Geo Sorting Docs]]
      * @param jsonStr String representation of a valid JSON object
      */
    case class RawSortConfig(jsonStr: String) extends CustomTypedToString with SortConfigBase {
      override def fromTyped: String = jsonStr
    }

    //////////////////////

    // Sorting

    /** Allows to return a script evaluation (based on different fields) for each hit
      * Script fields can work on fields that are not stored (my_field_name in the above case), and allow to return
      * custom values to be returned (the evaluated value of the script).
      * Script fields can also access the actual _source document indexed and extract specific elements to be returned
      * from it (can be an "object" type).
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-script-fields.html Docs]]
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/modules-scripting.html Inline vs File scripting]]
      * @param inline (Only one of `inline` or `file` should be specified) Lets the user enter a script directly into
      *                the query body
      * @param file (Only one of `inline` or `file` should be specified) Refers to a registered script file
      * @param lang The language (eg `groovy`, `expression`, `mustache`), defaults to `groovy`
      * @param params A generic set of parameters that are passed into the script
      * @param order The order in which the docs should be sorted according to the results of the script, should be one
      *              of `"asc"` or `"desc"`
      */
    case class ScriptFieldConfig
      (inline:  Option[String] = None,
       file: Option[String] = None,
       lang: Option[ScriptLang] = None,
       params: Map[String, Any] = Map(),
       order: Option[SortOrder] = None)
      extends CustomTypedToString with SortConfigBase
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("script")(
          obj.Field("inline"),
          obj.Field("file"),
          obj.Field("lang"),
          obj.MultiTypeField("params")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    //////////////////////

    // Highlighting

    /** The per field configuration for highlighting
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html Docs]]
      * @param `type` The type field allows to force a specific highlighter type. This is useful for instance when
      *               needing to use the plain highlighter on a field that has term_vectors enabled.
      *               The allowed values are: `"plain"`, `"postings"` and `"fvh"`.
      *               [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_force_highlighter_type Docs]]
      * @param force_source Forces the highlighting to highlight fields based on the source even if fields are stored
      *                     separately. Defaults to false.
      *                     [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_force_highlighter_type Docs]]
      * @param number_of_fragments Each field highlighted can control the size of the highlighted fragment in
      *                            characters (defaults to 100), and the maximum number of fragments to return
      *                            (defaults to 5).
      *                            [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_highlighted_fragments  Docs]]
      * @param fragment_size Each field highlighted can control the size of the highlighted fragment in
      *                      characters (defaults to 100), and the maximum number of fragments to return
      *                      (defaults to 5).
      *                      The fragment_size is ignored when using the postings highlighter, as it outputs sentences
      *                      regardless of their length.
      *                      [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_highlighted_fragments  Docs]]
      * @param no_match_size In the case where there is no matching fragment to highlight, the default is to not return
      *                      anything. Instead, we can return a snippet of text from the beginning of the field by
      *                      setting no_match_size (default 0) to the length of the text that you want returned.
      * @param highlight_query It is also possible to highlight against a query other than the search query by setting
      *                        highlight_query. This is especially useful if you use a rescore query because those are
      *                        not taken into account by highlighting by default. Elasticsearch does not validate that
      *                        highlight_query contains the search query in any way so it is possible to define it
      *                        so legitimate query results aren’t highlighted at all. Generally it is better to
      *                        include the search query in the highlight_query.
      *                        [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_highlight_query Docs]]
      * @param pre_tags By default, the highlighting will wrap highlighted text in `<em>` and `</em>`.
      *                 This can be controlled by setting `pre_tags` and `post_tags`
      *                 [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#tags Docs]]
      * @param post_tags By default, the highlighting will wrap highlighted text in `<em>` and `</em>`.
      *                 This can be controlled by setting `pre_tags` and `post_tags`
      *                 [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#tags Docs]]
      * @param matched_fields The Fast Vector Highlighter can combine matches on multiple fields to highlight a single
      *                       field using matched_fields. This is most intuitive for multifields that analyze the same
      *                       string in different ways. All matched_fields must have term_vector set to
      *                       with_positions_offsets but only the field to which the matches are combined is loaded
      *                       so only that field would benefit from having store set to yes.
      *                       [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#matched-fields Docs]]
      */
    case class FieldHighlightConfig
      (`type`: Option[String] = None,
       force_source: Boolean = false,
       number_of_fragments: Option[Int] = None,
       fragment_size: Option[Int] = None,
       no_match_size: Option[Int] = None,
       highlight_query: Option[qb.QueryElement] = None,
       pre_tags: Seq[String] = Seq(),
       post_tags: Seq[String] = Seq(),
       matched_fields: Seq[String] = Seq()
      )
      extends CustomTypedToString
    {
      @SimpleObjectDescription("obj",
        obj.Field("`type`"),
        obj.Field("force_source"),
        obj.Field("number_of_fragments"),
        obj.Field("fragment_size"),
        obj.Field("no_match_size"),
        obj.Field("highlight_query"),
        obj.MultiTypeField("pre_tags"),
        obj.MultiTypeField("post_tags"),
        obj.MultiTypeField("matched_fields")
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** Allows to highlight search results on one or more fields. The implementation uses either the lucene
      * highlighter, fast-vector-highlighter or postings-highlighter.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html Docs]]
      * @param fields A map of document fields to which to apply highlighting, associated with
      * @param order If specified (only value currently supported is `"score"`) then controls how highlighted
      *              fields are ordered (by default - their order in the field)
      * @param require_field_match require_field_match can be set to false which will cause any field to be highlighted
      *                            regardless of whether the query matched specifically on them. The default behaviour
      *                            is true, meaning that only fields that hold a query match will be highlighted.
      *
      *
      * @param `type` (Global applied by default - unless overridden - to all fields) The type field allows to force a specific highlighter type. This is useful for instance when
      *               needing to use the plain highlighter on a field that has term_vectors enabled.
      *               The allowed values are: `"plain"`, `"postings"` and `"fvh"`.
      *               [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_force_highlighter_type Docs]]
      * @param force_source (Global applied by default - unless overridden - to all fields) Forces the highlighting to highlight fields based on the source even if fields are stored
      *                     separately. Defaults to false.
      *                     [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_force_highlighter_type Docs]]
      * @param number_of_fragments (Global applied by default - unless overridden - to all fields) Each field highlighted can control the size of the highlighted fragment in
      *                            characters (defaults to 100), and the maximum number of fragments to return
      *                            (defaults to 5).
      *                            [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_highlighted_fragments  Docs]]
      * @param fragment_size (Global applied by default - unless overridden - to all fields) Each field highlighted can control the size of the highlighted fragment in
      *                      characters (defaults to 100), and the maximum number of fragments to return
      *                      (defaults to 5).
      *                      The fragment_size is ignored when using the postings highlighter, as it outputs sentences
      *                      regardless of their length.
      *                      [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_highlighted_fragments  Docs]]
      * @param no_match_size (Global applied by default - unless overridden - to all fields) In the case where there is no matching fragment to highlight, the default is to not return
      *                      anything. Instead, we can return a snippet of text from the beginning of the field by
      *                      setting no_match_size (default 0) to the length of the text that you want returned.
      * @param highlight_query (Global applied by default - unless overridden - to all fields) It is also possible to highlight against a query other than the search query by setting
      *                        highlight_query. This is especially useful if you use a rescore query because those are
      *                        not taken into account by highlighting by default. Elasticsearch does not validate that
      *                        highlight_query contains the search query in any way so it is possible to define it
      *                        so legitimate query results aren’t highlighted at all. Generally it is better to
      *                        include the search query in the highlight_query.
      *                        [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#_highlight_query Docs]]
      * @param pre_tags (Global applied by default - unless overridden - to all fields) By default, the highlighting will wrap highlighted text in `<em>` and `</em>`.
      *                 This can be controlled by setting `pre_tags` and `post_tags`
      *                 [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#tags Docs]]
      * @param post_tags (Global applied by default - unless overridden - to all fields) By default, the highlighting will wrap highlighted text in `<em>` and `</em>`.
      *                   This can be controlled by setting `pre_tags` and `post_tags`
      *                   [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#tags Docs]]
      * @param matched_fields (Global applied by default - unless overridden - to all fields) The Fast Vector Highlighter can combine matches on multiple fields to highlight a single
      *                       field using matched_fields. This is most intuitive for multifields that analyze the same
      *                       string in different ways. All matched_fields must have term_vector set to
      *                       with_positions_offsets but only the field to which the matches are combined is loaded
      *                       so only that field would benefit from having store set to yes.
      *                       [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html#matched-fields Docs]]
      */
    case class HighlightConfig
      (fields: Map[String, FieldHighlightConfig],
       order: Option[String] = None,
       require_field_match: Boolean = true,
       // These are the global overrides:
       `type`: Option[String] = None,
       force_source: Boolean = false,
       number_of_fragments: Option[Int] = None,
       fragment_size: Option[Int] = None,
       no_match_size: Option[Int] = None,
       highlight_query: Option[qb.QueryElement] = None,
       pre_tags: Seq[String] = Seq(),
       post_tags: Seq[String] = Seq(),
       matched_fields: Seq[String] = Seq()
      )
      extends CustomTypedToString
    {
      @SimpleObjectDescription("obj",
        obj.Field("order"),
        obj.Field("require_field_match"),
        obj.Field("fields"),
        obj.Field("`type`"),
        obj.Field("force_source"),
        obj.Field("number_of_fragments"),
        obj.Field("fragment_size"),
        obj.Field("no_match_size"),
        obj.Field("highlight_query"),
        obj.MultiTypeField("pre_tags"),
        obj.MultiTypeField("post_tags"),
        obj.MultiTypeField("matched_fields")
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    //////////////////////

    // Rescoring

    /** Rescoring can help to improve precision by reordering just the top (eg 100 - 500) documents returned by the
      * query and post_filter phases, using a secondary (usually more costly) algorithm, instead of applying the costly
      * algorithm to all documents in the index.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-rescore.html Docs]]
      * @param rescore_query The new query that will be applied on the top matches and re-order them
      * @param window_size The query rescorer executes a second query only on the Top-K results returned by the query
      *                    and post_filter phases. The number of docs which will be examined on each shard can be
      *                    controlled by the window_size parameter, which defaults to from and size.
      * @param score_mode The way the scores are combined can be controlled with the score_mode:
      *                   "total", "multiply", "avg", "min", "max"
      * @param query_weight By default the scores from the original query and the rescore query are combined linearly
      *                     to produce the final _score for each document. The relative importance of the original
      *                     query and of the rescore query can be controlled with the query_weight and
      *                     rescore_query_weight respectively. Both default to 1.
      * @param rescore_query_weight By default the scores from the original query and the rescore query are combined
      *                             linearly to produce the final _score for each document. The relative importance of
      *                             the original query and of the rescore query can be controlled with the query_weight
      *                             and rescore_query_weight respectively. Both default to 1.
      */
    case class RescoreConfig
      (rescore_query: qb.QueryElement,
       window_size: Option[Int] = None,
       score_mode: Option[String] = None,
       query_weight: Option[Double] = None,
       rescore_query_weight: Option[Double] = None)
      extends CustomTypedToString
    {
      @SimpleObjectDescription("obj",
        obj.Field("window_size"),
        obj.SimpleObject("query")(
          obj.Field("rescore_query"),
          obj.Field("score_mode"),
          obj.Field("query_weight"),
          obj.Field("rescore_query_weight")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }
  }

  /////////////////////////////////////////////////////////////////////////////

  /** Query builders */
  object qb {

    /** The base trait that all query elements must inherit */
    trait QueryElement {
      /** Each filter and query can accept a _name in its top level definition. */
      def _name: Option[String]
    }

    //TODO: things to add to some/all of the individual query elements (and also at the top level - both are currently only supported via QueryBodyRaw strings):
    // * https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-inner-hits.html (HasParentQuery/HasChildQuery)

    /////////////////////////////////////////////////////////////////////////////

    /** A query term to retrieve all documents in the specified index/indicies (optionally with specified types)
      *
      * @param boost The optional boost to apply to the scoring
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class MatchAllQuery
      (boost: Option[Double] = None, _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("match_all")(
          obj.Field("boost"),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** The term query finds documents that contain the exact term specified in the inverted index.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-term-query.html Docs]]
      *
      * @param field The field in the document to test against (can include nested fields using dot notation)
      * @param value The term of match against
      * @param boost The boost multiplier to the scoring (eg `1.0` is equivalent to not setting it)
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class TermQuery
      (field: String, value: String, boost: Option[Double] = None, _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("term")(
          obj.KeyValue("field")("value", obj.Field("boost")),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** Filters documents that have fields that match any of the provided terms (not analyzed)
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-terms-query.html Docs]]
      *
      * @param terms The fields to filter, and for each one a list of the allowed matching terms
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class TermsQuery(terms: Map[String, Seq[String]], _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("terms")(
          obj.KeyValues("terms")(),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** This case class can only be used in the `terms` param of the `TermsLookupQuery`, NOT standalone:
      * When it’s needed to specify a terms filter with a lot of terms it can be beneficial to fetch those term values
      * from a document in an index. A concrete example would be to filter tweets tweeted by your followers. Potentially
      * the amount of user ids specified in the terms filter can be a lot. In this scenario it makes sense to use the
      * terms filter’s terms lookup mechanism.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-terms-query.html Docs]]
      *
      * @param index   The index to fetch the term values from. Defaults to the current index.
      * @param `type`  The type to fetch the term values from.
      * @param id      The id of the document to fetch the term values from.
      * @param path    The field specified as path to fetch the actual values for the terms filter.
      * @param routing A custom routing value to be used when retrieving the external terms doc.
      */
    case class TermLookupQuery
      (index: String,
       `type`: String,
       id: String,
       path: String,
       routing: Option[String] = None)
      extends CustomTypedToString
    {
      @SimpleObjectDescription("obj",
        obj.Field("index"),
        obj.Field("`type`"),
        obj.Field("id"),
        obj.Field("path"),
        obj.Field("routing")
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** When it’s needed to specify a terms filter with a lot of terms it can be beneficial to fetch those term values
      * from a document in an index. A concrete example would be to filter tweets tweeted by your followers. Potentially
      * the amount of user ids specified in the terms filter can be a lot. In this scenario it makes sense to use the
      * terms filter’s terms lookup mechanism.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-terms-query.html Docs]]
      *
      * @param terms The map of lookup filters keyed on the field name
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class TermsLookupQuery
      (terms: Map[String, TermLookupQuery], _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("terms")(
          obj.KeyValues("terms")(),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** Matches documents with fields that have terms within a certain range. The type of the Lucene query depends on the
      * field type, for string fields, the `TermRangeQuery`, while for number/date fields, the query is a
      * `NumericRangeQuery`.
      * Note that only of `gt`/`gte` and only one of `lt`/`lte` can be specified
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-range-query.html]]
      *
      * @param field     The name of the field on which to apply the range query
      * @param gt        If present, requires that `field` be greater-than the value (the type must correspond to the field type)
      * @param gte       If present, requires that `field` be greater-than-or-equal the value (the type must correspond to the
      *                  field type)
      * @param lt        If present, requires that `field` be lesser-than the value (the type must correspond to the field type)
      * @param lte       If present, requires that `field` be lesser-than-or-equal the value (the type must correspond to the
      *                  field type)
      * @param boost     Sets the boost value of the query, defaults to 1.0
      * @param format    Formatted dates will be parsed using the format specified on the date field by default, but it
      *                  can be overridden by passing the format parameter to the range query
      * @param time_zone Dates can be converted from another timezone to UTC either by specifying the time zone in the
      *                  date value itself (if the format accepts it), or it can be specified as the time_zone parameter
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class RangeQuery
      (field: String,
      gt: Option[Any] = None, gte: Option[Any] = None, lt: Option[Any] = None, lte: Option[Any] = None,
       boost: Option[Double] = None, format: Option[String] = None, time_zone: Option[String] = None,
       _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("range")(
          obj.KeyValue("field")(
            obj.SimpleObject(
              obj.Field("gt"),
              obj.Field("gte"),
              obj.Field("lt"),
              obj.Field("lte"),
              obj.Field("boost"),
              obj.Field("format"),
              obj.Field("time_zone")
            )
          ),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** Matches documents that have fields containing terms with a specified prefix (not analyzed).
      * The prefix query maps to Lucene `PrefixQuery`
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-prefix-query.html Docs]]
      *
      * @param field The field in the document to test against (can include nested fields using dot notation)
      * @param value The term of match against (by prefix matching)
      * @param boost The boost multiplier to the scoring (eg `1.0` is equivalent to not setting it)
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class PrefixQuery
      (field: String, value: String, boost: Option[Double] = None, _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("prefix")(
          obj.KeyValue("field")("value", obj.Field("boost")),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** Filters documents that only have the provided ids. Note, this query uses the _uid field.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-ids-query.html Docs]]
      *
      * @param values The list of ids to filter on
      * @param `type` The type is optional and can be omitted, and can also accept an array of values.
      *               If no type is specified, all types defined in the index mapping are tried.
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class IdsQuery(values: Seq[String], `type`: Seq[String] = Seq.empty, _name: Option[String] = None)
      extends CustomTypedToString with QueryElement {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("ids")(
          obj.MultiTypeField("`type`", arrayIfSingleton = false),
          obj.Field("values"),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** Combine a set of query terms in various boolean equations to build up a composite query
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-bool-query.html Docs]]
      *
      * @param must                 If specified, all of these clauses must return true for a doc to be returned
      * @param must_not             If specified, any docs matching any of these clauses are not returned
      * @param should               If specified, any doc matching at least 1 (or `minimum_should_match` if specified) of these
      *                             clauses will be returned
      * @param filter               The clause (query) must appear in matching documents. However unlike `must` the score of the query
      *                             will be ignored.
      * @param minimum_should_match If specified, can allow a more restrictive `should` where >1 clause must match
      * @param boost                The optional boost to apply to the scoring
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class BoolQuery
      (must: Seq[QueryElement] = Seq.empty,
       must_not: Seq[QueryElement] = Seq.empty,
       should: Seq[QueryElement] = Seq.empty,
       filter: Seq[QueryElement] = Seq.empty,
       minimum_should_match: Option[Integer] = None,
       boost: Option[Double] = None,
       _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("bool")(
          obj.MultiTypeField("must", arrayIfSingleton = false),
          obj.MultiTypeField("must_not", arrayIfSingleton = false),
          obj.MultiTypeField("should", arrayIfSingleton = false),
          obj.MultiTypeField("filter", arrayIfSingleton = false),
          obj.Field("minimum_should_match"),
          obj.Field("boost"),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** A query that wraps another query and simply returns a constant score equal to the query boost for every document
      * in the filter. Maps to Lucene ConstantScoreQuery.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-constant-score-query.html Docs]]
      *
      * @param filter The query to apply
      * @param boost  The boost that generates the score
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class ConstantScoreQuery
      (filter: QueryElement, boost: Option[Double] = None, _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("constant_score")(
          obj.Field("filter"),
          obj.Field("boost"),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** The has_child filter accepts a query and the child type to run against, and results in parent documents
      * that have child docs matching the query.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-has-child-query.html Docs]]
      *
      * @param query        The query to apply to the children of the documents in the specified index
      * @param `type`       Optionally filters the query to a single type
      * @param score_mode   The supported score modes are min, max, sum, avg or none. The default is none and yields the
      *                     same behaviour as in previous versions. If the score mode is set to another value than none,
      *                     the scores of all the matching child documents are aggregated into the associated parent
      *                     documents.
      * @param min_children The has_child query allows you to specify that a minimum and/or maximum number of
      *                     children are required to match for the parent doc to be considered a match
      * @param max_children The has_child query allows you to specify that a minimum and/or maximum number of
      *                     children are required to match for the parent doc to be considered a match
      * @param inner_hits The actual matches in the different scopes that caused a document to be returned is hidden.
      *                   In many cases, it’s very useful to know which children/parent documents
      *                   caused certain information to be returned. The inner hits feature can be used for this.
      *                   This feature returns per search hit in the search response additional nested hits that caused
      *                   a search hit to match in a different scope.
      *                   Currently the `RawQueryBody` must be used to inject the desired configuration, until
      *                   an element is written
      *                   [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-inner-hits.html Docs]]
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class HasChildQuery
      (query: QueryElement,
       `type`: Option[String] = None,
       score_mode: Option[String] = None,
       min_children: Option[Int] = None, max_children: Option[Int] = None,
       inner_hits: Option[QueryBodyBase] = None,
       _name: Option[String] = None
      )
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("has_child")(
          obj.Field("`type`"),
          obj.Field("score_mode"),
          obj.Field("min_children"),
          obj.Field("max_children"),
          obj.Field("query"),
          obj.Field("inner_hits"),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** The has_parent query accepts a query and a parent type. The query is executed in the parent document space,
      * which is specified by the parent type. This query returns child documents which associated parents have matched.
      * For the rest has_parent query has the same options and works in the same manner as the has_child query.
      * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-has-parent-query.html Docs]]
      *
      * @param query       The query to apply to the parents of the documents in the specified index
      * @param parent_type If specified, filters parent docs to need to match this type
      * @param score_mode  The has_parent also has scoring support. The supported score types are score or none. The
      *                    default is none and this ignores the score from the parent document. The score is in this case
      *                    equal to the boost on the has_parent query (Defaults to 1). If the score type is set to score,
      *                    then the score of the matching parent document is aggregated into the child documents belonging
      *                    to the matching parent document.
      * @param inner_hits The actual matches in the different scopes that caused a document to be returned is hidden.
      *                   In many cases, it’s very useful to know which children/parent documents
      *                   caused certain information to be returned. The inner hits feature can be used for this.
      *                   This feature returns per search hit in the search response additional nested hits that caused
      *                   a search hit to match in a different scope.
      *                   Currently the `RawQueryBody` must be used to inject the desired configuration, until
      *                   an element is written
      *                   [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-inner-hits.html Docs]]
      * @param _name Each filter and query can accept a _name in its top level definition.
      *              The search response will include for each hit the matched_queries it matched on.
      *              The tagging of queries and filters only make sense for the bool query.
      *              [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-named-queries-and-filters.html Docs]]
      */
    case class HasParentQuery
      (query: QueryElement, parent_type: Option[String] = None,
       score_mode: Option[String] = None,
       inner_hits: Option[QueryBodyBase] = None,
       _name: Option[String] = None)
      extends CustomTypedToString with QueryElement
    {
      @SimpleObjectDescription("obj",
        obj.SimpleObject("has_parent")(
          obj.Field("parent_type"),
          obj.Field("score_mode"),
          obj.Field("query"),
          obj.Field("inner_hits"),
          obj.Field("_name")
        )
      )
      override def fromTyped: String = obj.AutoGenerated
    }

    /** A query element that can take a JSON object string (eg generated by `json.asString` from whatever JSON library
      * is being used, and will insert it into the query unchanged). Note that no validation is performed, ie
      * if the string is invalid JSON or not a valid query element a runtime error will occur.
      * @param jsonStr String representation of a valid JSON object
      */
    case class RawQuery(jsonStr: String) extends CustomTypedToString with QueryElement {
      /** (embed this directly into the JSON via the string, this explicit param is unused) */
      def _name: Option[String] = None
      override def fromTyped: String = jsonStr
    }

  }

  /////////////////////////////////////////////////////////////////////////////

  /** Aggregation builders */
  object ab {

    /** The base trait that all query elements must inherit */
    trait AggregationElement

    /** A aggregation element that can take a JSON object string (eg generated by `json.asString` from whatever JSON library
      * is being used, and will insert it into the query unchanged). Note that no validation is performed, ie
      * if the string is invalid JSON or not a valid aggregation element a runtime error will occur.
      * @param jsonStr String representation of a valid JSON object
      */
    case class RawAggregation(jsonStr: String) extends CustomTypedToString with AggregationElement {
      override def fromTyped: String = jsonStr
    }
  }

  /////////////////////////////////////////////////////////////////////////////

  /** List of search operations to apply as part of the multi search operation
    *
    * @param ops The list of ES operations
    */
  case class MultiSearchOps(ops: List[BaseDriverOp with MultiSearchDeclaration]) extends CustomTypedToString {
    override def fromTyped: String = BulkUtils.buildBulkSearchOps(ops)
  }
}
object DataModelSearch extends DataModelSearch