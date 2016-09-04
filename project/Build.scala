import sbt._
import Keys._

object BuildSettings {

  val paradiseVersion   = "2.0.1"
  val scalaBuildVersion = "2.11.2"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "org.elasticsearch",
    scalacOptions ++= Seq(),
    scalaVersion := scalaBuildVersion,
    crossScalaVersions := Seq("2.10.2", "2.10.3", "2.10.4", "2.11.0", "2.11.1"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases")
  )
}

object MyBuild extends Build {
  import BuildSettings._

  // Dependencies:

  val circeVersion = "0.4.1"
  lazy val circeDeps = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)
  //add like: libraryDependencies ++= circeDeps,

  val esRestVersion = "5.0.0-alpha5"
  lazy val esRestDeps = "org.elasticsearch.client" % "rest" % esRestVersion

  val utestJvmVersion = "0.4.3"
  lazy val utestJvmDeps = "com.lihaoyi" %% "utest" % utestJvmVersion % "test"

  lazy val simpleScalaHttpServer = "com.tumblr" %% "colossus" % "0.8.1" % "test"

  // Project definitions

  val esScalaDriverVersion = "0.1-SNAPSHOT"

  lazy val root = Project("root", file("."))
    .aggregate(elasticsearch_scala_core, elasticsearch_scala_java_client, elasticsearch_scala_js_client)

  lazy val elasticsearch_scala_core: Project = Project(
    "elasticsearch_scala_core",
    file("elasticsearch_scala_core"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Scala Core",
      version := esScalaDriverVersion,
      libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.2",
      libraryDependencies += utestJvmDeps,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  )

  lazy val elasticsearch_scala_java_client: Project = Project(
    "elasticsearch_scala_java_client",
    file("elasticsearch_scala_java_client"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Java Client Scala bridge",
      version := esScalaDriverVersion,
      libraryDependencies += esRestDeps,
      libraryDependencies += utestJvmDeps,
      libraryDependencies += simpleScalaHttpServer,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  ).dependsOn(elasticsearch_scala_core)

  lazy val elasticsearch_scala_js_client: Project = Project(
    "elasticsearch_scala_js_client",
    file("elasticsearch_scala_js_client"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Scala Js Client",
      version := esScalaDriverVersion
    )
  ).dependsOn(elasticsearch_scala_core)
}