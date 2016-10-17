package org.elastic.elasticsearch.scala.driver.common

import org.elastic.elasticsearch.scala.driver.common.DataModelSearch.MultiSearchOps
import utest._
import org.elastic.rest.scala.driver.RestBase._
import org.elastic.rest.scala.driver.utils.NoJsonHelpers

object ApiModelSearchTests extends TestSuite {

  val tests = this {
    "Basic checking for all the search resources" - {

      object api extends ApiModelCommon
      object search extends ApiModelSearch

      // Standard searches

      // all indexes, all types

      search.`/_search`().readS("TEST")
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .from(10).request_cache(true).search_type("ss").size(100).terminate_after(10).timeout("1s")
        .getUrl ==> "/_search?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&from=10&request_cache=true&search_type=ss&size=100&terminate_after=10&timeout=1s"

      api.`/`()._search.readS("TEST").getUrl ==> "/_search"

      search.`/_search`().read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .from(10).search_type("ss").size(100).terminate_after(10).timeout("1s").df("field").default_operator("op")
        .track_scores(true).sort("s1", "s2").query("qq").lowercase_expanded_terms(true).lenient(true)
        .explain(true)._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_search?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&from=10&search_type=ss&size=100&terminate_after=10&timeout=1s&df=field&default_operator=op" +
                    "&track_scores=true&sort=s1,s2&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&explain=true&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._search.read()._source("_s1", "_s2").getUrl ==> "/_search?_source=_s1,_s2"

      // all indexes, specified types

      search.`/_all/$types/_search`("t1", "t2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .from(10).request_cache(false).search_type("ss").size(100).terminate_after(10).timeout("1s")
        .getUrl ==> "/_all/t1,t2/_search?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&from=10&request_cache=false&search_type=ss&size=100&terminate_after=10&timeout=1s"

      api.`/`()._all.$("t1", "t2")._search.readS("TEST").getUrl ==> "/_all/t1,t2/_search"

      search.`/_all/$types/_search`("t1", "t2").read()
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .from(10).search_type("ss").size(100).terminate_after(10).timeout("1s").df("field").default_operator("op")
        .track_scores(false).sort("s1", "s2").query("qq").lowercase_expanded_terms(false).lenient(false)
        .explain(false)._source(false)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_all/t1,t2/_search?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&from=10&search_type=ss&size=100&terminate_after=10&timeout=1s&df=field&default_operator=op" +
                    "&track_scores=false&sort=s1,s2&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&explain=false&_source=false&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._all.$("t1", "t2")._search.read()._source("_s1", "_s2").getUrl ==>
        "/_all/t1,t2/_search?_source=_s1,_s2"

      // specified indexes, all types

      search.`/$indexes/_search`("i1", "i2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .from(10).request_cache(false).search_type("ss").size(100).terminate_after(10).timeout("1s")
        .getUrl ==> "/i1,i2/_search?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&from=10&request_cache=false&search_type=ss&size=100&terminate_after=10&timeout=1s"

      api.`/`().$("i1")._search.readS("TEST").getUrl ==> "/i1/_search"
      api.`/`().$("i1", "i2")._search.readS("TEST").getUrl ==> "/i1,i2/_search"

      search.`/$indexes/_search`("i1", "i2").read()
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .from(10).search_type("ss").size(100).terminate_after(10).timeout("1s").df("field").default_operator("op")
        .track_scores(false).sort("s1", "s2").query("qq").lowercase_expanded_terms(false).lenient(false)
        .explain(false)._source(false)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(false)
        .getUrl ==> "/i1,i2/_search?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&from=10&search_type=ss&size=100&terminate_after=10&timeout=1s&df=field&default_operator=op" +
                    "&track_scores=false&sort=s1,s2&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&explain=false&_source=false&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=false"

      api.`/`().$("i1")._search.read()._source("_s1", "_s2").getUrl ==>
        "/i1/_search?_source=_s1,_s2"
      api.`/`().$("i1", "i2")._search.read()._source("_s1", "_s2").getUrl ==>
        "/i1,i2/_search?_source=_s1,_s2"

      // specified indexes, specified types

      search.`/$indexes/$types/_search`(Seq("i1", "i2"), Seq("t1", "t2")).readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .from(10).request_cache(false).search_type("ss").size(100).terminate_after(10).timeout("1s")
        .getUrl ==> "/i1,i2/t1,t2/_search?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&from=10&request_cache=false&search_type=ss&size=100&terminate_after=10&timeout=1s"

      api.`/`().$("i1").$("t1", "t2")._search.readS("TEST").getUrl ==> "/i1/t1,t2/_search"
      api.`/`().$("i1", "i2").$("t1", "t2")._search.readS("TEST").getUrl ==> "/i1,i2/t1,t2/_search"

      search.`/$indexes/$types/_search`(Seq("i1", "i2"), Seq("t1", "t2")).read()
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .from(10).search_type("ss").size(100).terminate_after(10).timeout("1s").df("field").default_operator("op")
        .track_scores(false).sort("s1", "s2").query("qq").lowercase_expanded_terms(false).lenient(false)
        .explain(false)._source(false)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(false)
        .getUrl ==> "/i1,i2/t1,t2/_search?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&from=10&search_type=ss&size=100&terminate_after=10&timeout=1s&df=field&default_operator=op" +
                    "&track_scores=false&sort=s1,s2&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&explain=false&_source=false&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=false"

      api.`/`().$("i1").$("t1", "t2")._search.read()._source("_s1", "_s2").getUrl ==>
        "/i1/t1,t2/_search?_source=_s1,_s2"
      api.`/`().$("i1", "i2").$("t1", "t2")._search.read()._source("_s1", "_s2").getUrl ==>
        "/i1,i2/t1,t2/_search?_source=_s1,_s2"

      // Search templates

      search.`/_search/template`().readS("TEST").pretty(true).getUrl ==> "/_search/template?pretty=true"
      api.`/`()._search.template.readS("TEST").getUrl ==> "/_search/template"

      search.`/_search/template/$template`("t").read().human(true).getUrl ==> "/_search/template/t?human=true"
      api.`/`()._search.template.$("t").read().getUrl ==> "/_search/template/t"

      search.`/_search/template/$template`("t").writeS("TEST").`case`(true).getUrl ==> "/_search/template/t?case=true"
      api.`/`()._search.template.$("t").writeS("TEST").getUrl ==> "/_search/template/t"

      search.`/_search/template/$template`("t").delete().flat_settings(true).getUrl ==>
        "/_search/template/t?flat_settings=true"
      api.`/`()._search.template.$("t").delete().getUrl ==> "/_search/template/t"

      search.`/_render/template`().readS("TEST").filter_path("f1", "f2").getUrl ==> "/_render/template?filter_path=f1,f2"
      api.`/`()._render.template.readS("TEST").getUrl ==> "/_render/template"

      // Search shards

      search.`/$indexes/_search_shards`("i1", "i2").readS("TEST")
        .human(true).routing("n").preference("pr").local(true)
        .getUrl ==> "/i1,i2/_search_shards?human=true&routing=n&preference=pr&local=true"

      api.`/`().$("i1")._search_shards.readS("TEST").getUrl ==> "/i1/_search_shards"
      api.`/`().$("i1", "i2")._search_shards.readS("TEST").getUrl ==> "/i1,i2/_search_shards"

      // Search suggesters

      // all indexes, all types

      search.`/_suggest`().readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false)
        .getUrl ==> "/_suggest?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&analyzer=an&analyze_wildcard=false"

      api.`/`()._suggest.readS("TEST").getUrl ==> "/_suggest"

      search.`/_suggest`().read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_suggest?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._suggest.read().getUrl ==> "/_suggest"

      // all indexes, specified types

      search.`/_all/$types/_suggest`("t1", "t2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false)
        .getUrl ==> "/_all/t1,t2/_suggest?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&analyzer=an&analyze_wildcard=false"

      api.`/`()._all.$("t1", "t2")._suggest.readS("TEST").getUrl ==>"/_all/t1,t2/_suggest"

      search.`/_all/$types/_suggest`("t1", "t2").read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_all/t1,t2/_suggest?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._all.$("t1", "t2")._suggest.read().getUrl ==> "/_all/t1,t2/_suggest"

      // specified indexes, all types

      search.`/$indexes/_suggest`("i1", "i2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false)
        .getUrl ==> "/i1,i2/_suggest?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&analyzer=an&analyze_wildcard=false"

      api.`/`().$("i1")._suggest.readS("TEST").getUrl ==>"/i1/_suggest"
      api.`/`().$("i1", "i2")._suggest.readS("TEST").getUrl ==>"/i1,i2/_suggest"

      search.`/$indexes/_suggest`("i1", "i2").read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/_suggest?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1")._suggest.read().getUrl ==> "/i1/_suggest"
      api.`/`().$("i1", "i2")._suggest.read().getUrl ==> "/i1,i2/_suggest"

      // specified indexes, specified types

      search.`/$indexes/$types/_suggest`(Seq("i1", "i2"), Seq("t1", "t2")).readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false)
        .getUrl ==> "/i1,i2/t1,t2/_suggest?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=false&lenient=false" +
                    "&analyzer=an&analyze_wildcard=false"

      api.`/`().$("i1").$("t1", "t2")._suggest.readS("TEST").getUrl ==>"/i1/t1,t2/_suggest"
      api.`/`().$("i1", "i2").$("t1", "t2")._suggest.readS("TEST").getUrl ==>"/i1,i2/t1,t2/_suggest"

      search.`/$indexes/$types/_suggest`(Seq("i1", "i2"), Seq("t1", "t2")).read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/t1,t2/_suggest?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1").$("t1", "t2")._suggest.read().getUrl ==> "/i1/t1,t2/_suggest"
      api.`/`().$("i1", "i2").$("t1", "t2")._suggest.read().getUrl ==> "/i1,i2/t1,t2/_suggest"

      // Multi search

      // all indexes, all types

      search.`/_msearch`().readS("TEST").pretty(true).getUrl ==> "/_msearch?pretty=true"
      api.`/`()._msearch.readS("TEST").getUrl ==> "/_msearch"

      // specified indexes, all types

      search.`/$indexes/_msearch`("i1", "i2").readS("TEST").pretty(true).getUrl ==> "/i1,i2/_msearch?pretty=true"
      api.`/`().$("i1")._msearch.readS("TEST").getUrl ==> "/i1/_msearch"
      api.`/`().$("i1", "i2")._msearch.readS("TEST").getUrl ==> "/i1,i2/_msearch"

      // all indexes, specified types

      search.`/_all/$types/_msearch`("t1", "t2").readS("TEST").pretty(true).getUrl ==> "/_all/t1,t2/_msearch?pretty=true"
      api.`/`()._all.$("t1", "t2")._msearch.readS("TEST").getUrl ==> "/_all/t1,t2/_msearch"

      // specified indexes, specified types

      search.`/$indexes/$types/_msearch`(Seq("i1", "i2"), Seq("t1", "t2")).readS("TEST")
        .pretty(false).getUrl ==> "/i1,i2/t1,t2/_msearch?pretty=false"
      api.`/`().$("i1").$("t1", "t2")._msearch.readS("TEST").getUrl ==> "/i1/t1,t2/_msearch"
      api.`/`().$("i1", "i2").$("t1", "t2")._msearch.readS("TEST").getUrl ==> "/i1,i2/t1,t2/_msearch"

      // Count API

      // all indexes, all types

      search.`/_count`().readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(100)
        .getUrl ==> "/_count?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=100"

      api.`/`()._count.readS("TEST").getUrl ==>"/_count"

      search.`/_count`().read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true).terminate_after(100)
        .getUrl ==> "/_count?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true&terminate_after=100"

      api.`/`()._count.read().getUrl ==> "/_count"

      // all indexes, specified types

      search.`/_all/$types/_count`("t1", "t2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(100)
        .getUrl ==> "/_all/t1,t2/_count?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=100"

      api.`/`()._all.$("t1", "t2")._count.readS("TEST").getUrl ==>"/_all/t1,t2/_count"

      search.`/_all/$types/_count`("t1", "t2").read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true).terminate_after(100)
        .getUrl ==> "/_all/t1,t2/_count?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true&terminate_after=100"

      api.`/`()._all.$("t1", "t2")._count.read().getUrl ==> "/_all/t1,t2/_count"

      // specified indexes, all types

      search.`/$indexes/_count`("i1", "i2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(100)
        .getUrl ==> "/i1,i2/_count?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=100"

      api.`/`().$("i1")._count.readS("TEST").getUrl ==>"/i1/_count"
      api.`/`().$("i1", "i2")._count.readS("TEST").getUrl ==>"/i1,i2/_count"

      search.`/$indexes/_count`("i1", "i2").read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true).terminate_after(100)
        .getUrl ==> "/i1,i2/_count?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true&terminate_after=100"

      api.`/`().$("i1")._count.read().getUrl ==> "/i1/_count"
      api.`/`().$("i1", "i2")._count.read().getUrl ==> "/i1,i2/_count"

      // specified indexes, specified types

      search.`/$indexes/$types/_count`(Seq("i1", "i2"), Seq("t1", "t2")).readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(100)
        .getUrl ==> "/i1,i2/t1,t2/_count?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=100"

      api.`/`().$("i1").$("t1", "t2")._count.readS("TEST").getUrl ==>"/i1/t1,t2/_count"
      api.`/`().$("i1", "i2").$("t1", "t2")._count.readS("TEST").getUrl ==>"/i1,i2/t1,t2/_count"

      search.`/$indexes/$types/_count`(Seq("i1", "i2"), Seq("t1", "t2")).read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true).terminate_after(100)
        .getUrl ==> "/i1,i2/t1,t2/_count?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true&terminate_after=100"

      api.`/`().$("i1").$("t1", "t2")._count.read().getUrl ==> "/i1/t1,t2/_count"
      api.`/`().$("i1", "i2").$("t1", "t2")._count.read().getUrl ==> "/i1,i2/t1,t2/_count"

      // Validate

      // all indexes, all types

      search.`/_validate`().readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(10)
        .getUrl ==> "/_validate?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=10"

      api.`/`()._validate.readS("TEST").getUrl ==>"/_validate"

      search.`/_validate`().read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_validate?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true"

      api.`/`()._validate.read().getUrl ==> "/_validate"

      // all indexes, specified types

      search.`/_all/$types/_validate`("t1", "t2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(10)
        .getUrl ==> "/_all/t1,t2/_validate?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=10"

      api.`/`()._all.$("t1", "t2")._validate.readS("TEST").getUrl ==>"/_all/t1,t2/_validate"

      search.`/_all/$types/_validate`("t1", "t2").read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_all/t1,t2/_validate?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true"

      api.`/`()._all.$("t1", "t2")._validate.read().getUrl ==> "/_all/t1,t2/_validate"

      // specified indexes, all types

      search.`/$indexes/_validate`("i1", "i2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(10)
        .getUrl ==> "/i1,i2/_validate?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=10"

      api.`/`().$("i1")._validate.readS("TEST").getUrl ==>"/i1/_validate"
      api.`/`().$("i1", "i2")._validate.readS("TEST").getUrl ==>"/i1,i2/_validate"

      search.`/$indexes/_validate`("i1", "i2").read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/_validate?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1")._validate.read().getUrl ==> "/i1/_validate"
      api.`/`().$("i1", "i2")._validate.read().getUrl ==> "/i1,i2/_validate"

      // specified indexes, specified types

      search.`/$indexes/$types/_validate`(Seq("i1", "i2"), Seq("t1", "t2")).readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(false).lenient(false)
        .analyzer("an").analyze_wildcard(false).terminate_after(10)
        .getUrl ==> "/i1,i2/t1,t2/_validate?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=false&lenient=false" +
        "&analyzer=an&analyze_wildcard=false&terminate_after=10"

      api.`/`().$("i1").$("t1", "t2")._validate.readS("TEST").getUrl ==>"/i1/t1,t2/_validate"
      api.`/`().$("i1", "i2").$("t1", "t2")._validate.readS("TEST").getUrl ==>"/i1,i2/t1,t2/_validate"

      search.`/$indexes/$types/_validate`(Seq("i1", "i2"), Seq("t1", "t2")).read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/t1,t2/_validate?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1").$("t1", "t2")._validate.read().getUrl ==> "/i1/t1,t2/_validate"
      api.`/`().$("i1", "i2").$("t1", "t2")._validate.read().getUrl ==> "/i1,i2/t1,t2/_validate"

      // Explain

      // all indexes, all types

      search.`/_explain`().readS("TEST")
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_explain?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
        "&df=field&default_operator=op" +
        "&query=qq&lowercase_expanded_terms=true&lenient=true" +
        "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
        "&analyzer=an&analyze_wildcard=true"

      api.`/`()._explain.readS("TEST").getUrl ==> "/_explain"

      search.`/_explain`().read()
        .`case`(true).filter_path("f1", "f2").flat_settings(true).human(true).pretty(true)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_explain?case=true&filter_path=f1,f2&flat_settings=true&human=true&pretty=true" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._explain.read()._source("_s1", "_s2").getUrl ==> "/_explain?_source=_s1,_s2"

      // all indexes, specified types

      search.`/_all/$types/_explain`("t1", "t2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_all/t1,t2/_explain?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._all.$("t1", "t2")._explain.readS("TEST").getUrl ==> "/_all/t1,t2/_explain"

      search.`/_all/$types/_explain`("t1", "t2").read()
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/_all/t1,t2/_explain?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`()._all.$("t1", "t2")._explain.read()._source("_s1", "_s2").getUrl ==>
        "/_all/t1,t2/_explain?_source=_s1,_s2"

      // specified indexes, all types

      search.`/$indexes/_explain`("i1", "i2").readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/_explain?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1")._explain.readS("TEST").getUrl ==> "/i1/_explain"
      api.`/`().$("i1", "i2")._explain.readS("TEST").getUrl ==> "/i1,i2/_explain"

      search.`/$indexes/_explain`("i1", "i2").read()
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/_explain?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1")._explain.read()._source("_s1", "_s2").getUrl ==>
        "/i1/_explain?_source=_s1,_s2"
      api.`/`().$("i1", "i2")._explain.read()._source("_s1", "_s2").getUrl ==>
        "/i1,i2/_explain?_source=_s1,_s2"

      // specified indexes, specified types

      search.`/$indexes/$types/_explain`(Seq("i1", "i2"), Seq("t1", "t2")).readS("TEST")
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/t1,t2/_explain?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1").$("t1", "t2")._explain.readS("TEST").getUrl ==> "/i1/t1,t2/_explain"
      api.`/`().$("i1", "i2").$("t1", "t2")._explain.readS("TEST").getUrl ==> "/i1,i2/t1,t2/_explain"

      search.`/$indexes/$types/_explain`(Seq("i1", "i2"), Seq("t1", "t2")).read()
        .`case`(false).filter_path("f1", "f2").flat_settings(false).human(false).pretty(false)
        .df("field").default_operator("op")
        .query("qq").lowercase_expanded_terms(true).lenient(true)
        ._source(true)._source_exclude("ex1", "ex2")._source_include("in1", "in2")
        .analyzer("an").analyze_wildcard(true)
        .getUrl ==> "/i1,i2/t1,t2/_explain?case=false&filter_path=f1,f2&flat_settings=false&human=false&pretty=false" +
                    "&df=field&default_operator=op" +
                    "&query=qq&lowercase_expanded_terms=true&lenient=true" +
                    "&_source=true&_source_exclude=ex1,ex2&_source_include=in1,in2" +
                    "&analyzer=an&analyze_wildcard=true"

      api.`/`().$("i1").$("t1", "t2")._explain.read()._source("_s1", "_s2").getUrl ==>
        "/i1/t1,t2/_explain?_source=_s1,_s2"
      api.`/`().$("i1", "i2").$("t1", "t2")._explain.read()._source("_s1", "_s2").getUrl ==>
        "/i1,i2/t1,t2/_explain?_source=_s1,_s2"
    }
    "Custom typed operations: _msearch" - {

      object search extends ApiModelSearch

      import NoJsonHelpers._

      val msearchOps = MultiSearchOps(List(
        search.`/_search`().readS("TEST1").m("param1", "tp1").m("param1", "tp2"),
        search.`/_all/$types/_search`("t1.1", "t1.2").readS("TEST2"),
        search.`/$indexes/_search`("i2.1", "i2.2").readS("TEST3").m("param", List("l1", "l2")),
        search.`/$indexes/$types/_search`(Seq("i3.1", "i3.2"), Seq("t3.1", "t3.2")).readS("TEST4").m("param", true)
      ))

      def formatVals(s: String) =
        s.replace(" ", "").replace("\t", "").replace("\r", "")

      val expected = formatVals(
        s"""{ "param1": "tp1", "param2": "tp2"  }
            |TEST1
            |{ "type": "t1.1,t1.2" }
            |TEST2
            |{ "index": "i2.1,i2.2", "param": ["l1", "l2"] }
            |TEST3
            |{ "index": "i3.1,i3.2", "type": "t3.1,t3.2", "param": true }
            |TEST4
            |"""
          .stripMargin.stripSuffix("\n"))

      formatVals(search.`/_msearch`().read(msearchOps).body.get) ==> expected
    }
  }
}
