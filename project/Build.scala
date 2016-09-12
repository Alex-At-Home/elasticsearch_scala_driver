import sbt._
import Keys._

object BuildSettings {

  val scalaBuildVersion = "2.11.8"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "org.elasticsearch",
    scalacOptions ++= Seq(),
    scalaVersion := scalaBuildVersion,
    crossScalaVersions := Seq("2.11.4", "2.11.5", "2.11.6", "2.11.7", "2.11.8"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
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

  val esRestVersion = "5.0.0-alpha5"
  lazy val esRestDeps = "org.elasticsearch.client" % "rest" % esRestVersion

  val ammoniteVersion = "0.7.6"
  lazy val ammoniteDeps = "com.lihaoyi" % "ammonite" % ammoniteVersion cross CrossVersion.full

  val utestJvmVersion = "0.4.3"
  lazy val utestJvmDeps = "com.lihaoyi" %% "utest" % utestJvmVersion % "test"

  lazy val simpleScalaHttpServer = "com.tumblr" %% "colossus" % "0.8.1" % "test"

  // Project definitions

  val esScalaDriverVersion = "0.1-SNAPSHOT"

  lazy val root = Project("root", file("."))
    .aggregate(
      rest_scala_core,
      rest_json_circe_module,
      rest_scala_js_client,
      elasticsearch_scala_core,
      elasticsearch_scala_java_client,
      elasticsearch_scala_shell
    )

  lazy val rest_scala_core: Project = Project(
    "rest_scala_core",
    file("rest_scala_core"),
    settings = buildSettings ++ Seq(
      name := "REST Scala Core",
      version := esScalaDriverVersion,
      libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaBuildVersion,
      libraryDependencies += utestJvmDeps,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  )

  lazy val rest_json_circe_module: Project = Project(
    "rest_json_circe_module",
    file("rest_json_circe_module"),
    settings = buildSettings ++ Seq(
      name := "REST JSON - CIRCE module",
      version := esScalaDriverVersion,
      libraryDependencies += utestJvmDeps,
      libraryDependencies ++= circeDeps,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  ).dependsOn(rest_scala_core)

  lazy val rest_scala_js_client: Project = Project(
    "rest_scala_js_client",
    file("rest_scala_js_client"),
    settings = buildSettings ++ Seq(
      name := "REST Scala JS Client",
      version := esScalaDriverVersion
    )
  ).dependsOn(elasticsearch_scala_core)

  lazy val elasticsearch_scala_core: Project = Project(
    "elasticsearch_scala_core",
    file("elasticsearch_scala_core"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Scala Core",
      version := esScalaDriverVersion,
      libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaBuildVersion,
      libraryDependencies += utestJvmDeps,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  ).dependsOn(rest_scala_core)

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

  lazy val elasticsearch_scala_shell: Project = Project(
    "elasticsearch_scala_shell",
    file("elasticsearch_scala_shell"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Scala shell",
      mainClass in (Compile, run) := Some("org.elastic.elasticsearch.scala.driver.jvm.ShellMain"),
      version := esScalaDriverVersion,
      libraryDependencies += ammoniteDeps,
      libraryDependencies += utestJvmDeps,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  )
  .dependsOn(rest_json_circe_module, elasticsearch_scala_java_client)

}