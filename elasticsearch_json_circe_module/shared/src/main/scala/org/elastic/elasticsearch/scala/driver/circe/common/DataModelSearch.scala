package org.elastic.elasticsearch.scala.driver.circe.common

import io.circe.JsonObject
import io.circe.optics.JsonPath._
import io.circe.generic.auto._
import org.elastic.rest.scala.driver.RestBaseImplicits.{CustomStringToTyped, RegisterType}
import org.elastic.elasticsearch.scala.driver.common.DataModelSearch.SearchResultsBase
import org.elastic.rest.scala.driver.json.CirceJsonModule.{DeclareJson, DeclareJsonFromString}

/** Data model types used by the search resources
  * Uses CIRCE to mix freeform JSON sections with typed sections
  */
trait DataModelSearch {

  /** Declare that this `SearchResults` is the concrete version of `SearchResultsBase`*/
  implicit val registeredSearchResults = new RegisterType[SearchResults] {}

  /** A set of views of the elasticsearch query result API call
    * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/_the_search_api.html Docs]]
    * @param asString The results of the search in string format
    */
  case class SearchResults(asString: String)
    extends SearchResultsBase with CustomStringToTyped with DeclareJsonFromString
  {
    import SearchResults._

    // Search metadata

    /** tells us how many shards were searched, as well as a count of the successful/failed searched shards */
    lazy val _shards: Shards = registerTyped(Lenses._shards, Shards())
    /** time in milliseconds for Elasticsearch to execute the search */
    lazy val took: Int = registerTyped(Lenses.took, 0)
    /** tells us if the search timed out or not */
    lazy val timed_out: Boolean = registerTyped(Lenses.timed_out, default = true)
    /** If this was a scrolling search, tells us the id to use to request the next window */
    lazy val _scroll_id: Option[String] = registerTyped(Lenses._scroll_id)

    // Hit metadata

    /** The highest score seen in the data set */
    lazy val max_score: Option[Double] = registerTyped(Lenses.`hits.max_score`)
    /**  total number of documents matching our search criteria */
    lazy val total_hits: Int = registerTyped(Lenses.`hits.total`, 0)

    // Access to the underlying data

    /** Metadata and content corresponding to each matching document returned from the query (JSON view) */
    lazy val hitsJ: List[JsonObject] = registerJson(Lenses.`hits.hits`)
    /** Metadata and content corresponding to each matching document returned from the query (typed view) */
    lazy val hits: List[Hit] = hitsJ.map(Hit(_))
    /** Content only corresponding to each matching document returned from the query (JSON view) */
    lazy val sourcesJ: List[JsonObject] = registerJson(Lenses.`hits.hits._source`)

    /** Aggregations of fields in the documents based on the query */
    lazy val aggregationsJ: Option[JsonObject] = registerJson(Lenses.aggregations)
    /** Aggregations of fields in the documents based on the query */
    def aggregationJ(aggName: String): Option[JsonObject] = aggregationsJ.flatMap(a => a(aggName)).flatMap(_.asObject)
  }
  /** Lenses and sub-classes used in `SearchResults` */
  object SearchResults {

    /** tells us how many shards were searched, as well as a count of the successful/failed searched shards
      * @param total How many shards were searched
      * @param successful How many shards were successfully searched
      * @param failed How many shards generated an error upon search
      */
    case class Shards(total: Int = 0, successful: Int = 0, failed: Int = 0)

    /** The metadata and contents for each matching document returned from a query */
    case class Hit(asJson: JsonObject) extends DeclareJson {
      /** The id of the document */
      lazy val _id: String = registerTyped(Hit.Lenses._id).get
      /** The index in which the document is stored */
      lazy val _index: String = registerTyped(Hit.Lenses._index).get
      /** The type of the document */
      lazy val _type: String = registerTyped(Hit.Lenses._type).get
      /** The score of the document relative to the query */
      lazy val _score: Option[Double] = registerTyped(Hit.Lenses._score)
      /** If version of the document if requested */
      lazy val _version: Option[Long] = registerTyped(Hit.Lenses._version)

      /** The document itself, if stored */
      lazy val _source: Option[JsonObject] = registerJson(Hit.Lenses._source)

      /** If the query contains highlighting, contains the highlight information (if any) per hit
        * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html Highlighting Docs]]
        */
      lazy val highlight: Option[JsonObject] = registerJson(Hit.Lenses.highlight)
      /** For debugging, queries can issue the request with `"explain": true`, in which case
        * each hit contains additional metadata explaining the matches and scoring
        */
      lazy val explain: Option[JsonObject] = registerJson(Hit.Lenses.explain)
    }
    /** Contains Lens and sub-classes used in `Hit` */
    object Hit {
      /** Lenses to access `Hit` */
      object Lenses {
        /** The id of the document */
        val _id = root._id.string
        /** The type of the document */
        val _index = root._index.string
        /** The index in which the document is stored */
        val _type = root._type.string
        /** The score of the document relative to the query */
        val _score = root._score.double
        /** If version of the document if requested */
        val _version = root._version.long

        /** The document itself, if stored */
        val _source = root._source.json

        /** If the query contains highlighting, contains the highlight information (if any) per hit
          * [[https://www.elastic.co/guide/en/elasticsearch/reference/2.3/search-request-highlighting.html Highlighting Docs]]
          */
        val highlight = root.highlight.json
        /** For debugging, queries can issue the request with `"explain": true`, in which case
          * each hit contains additional metadata explaining the matches and scoring
          */
        val explain = root.explain.json
      }
    }

    /** Lenses for known attributes of the search request */
    object Lenses {
      /** tells us how many shards were searched, as well as a count of the successful/failed searched shards */
      val _shards = root._shards.as[Shards]
      /** time in milliseconds for Elasticsearch to execute the search */
      val took = root.took.int
      /** tells us if the search timed out or not */
      val timed_out = root.timed_out.boolean
      /** If this was a scrolling search, tells us the id to use to request the next window */
      val _scroll_id = root._scroll_id.string

      /** The highest score seen in the data set */
      val `hits.max_score` = root.hits.max_score.double
      /**  total number of documents matching our search criteria */
      val `hits.total` = root.hits.total.int

      /** Metadata and content corresponding to each matching document returned from the query */
      val `hits.hits` = root.hits.hits.each.json
      /** Content only corresponding to each matching document returned from the query */
      val `hits.hits._source` = root.hits.hits.each._source.json

      /** Aggregations of fields in the documents based on the query */
      val aggregations = root.aggregations.json
    }
  }
}
object DataModelSearch extends DataModelSearch
