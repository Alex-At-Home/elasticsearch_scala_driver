import sbt._
import Keys._
import sbtassembly.AssemblyKeys._
import sbtassembly.{MergeStrategy, PathList}

object BuildSettings {

  val scalaBuildVersion = "2.11.8"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "org.elastic",
    scalacOptions ++= Seq(),
    scalaVersion := scalaBuildVersion,
    crossScalaVersions := Seq(),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
}

object MyBuild extends Build {
  import BuildSettings._

  // Dependencies:

  val esRestVersion = "5.0.0-alpha5"
  lazy val esRestDeps = "org.elasticsearch.client" % "rest" % esRestVersion

  val ammoniteVersion = "0.7.6"
  lazy val ammoniteDeps = "com.lihaoyi" % "ammonite" % ammoniteVersion cross CrossVersion.full

  val utestJvmVersion = "0.4.3"
  lazy val utestJvmDeps = "com.lihaoyi" %% "utest" % utestJvmVersion % "test"

  lazy val simpleScalaHttpServer = "com.tumblr" %% "colossus" % "0.8.1" % "test"

  val rest_client_library_uri = uri("https://github.com/Alex-At-Home/rest_client_library.git")

  // Project definitions

  val esScalaDriverVersion = "0.1-SNAPSHOT"

  lazy val root = Project(
    "root",
    file("."),
    settings = buildSettings
  )
  .aggregate(
    elasticsearch_scala_core,
    elasticsearch_scala_java_client,
    elasticsearch_scala_shell
  )

  val githubName = "elasticsearch_scala_driver"
  val apiRoot = "https://alex-at-home.github.io"
  val docVersion = "current"

  lazy val rest_scala_core = ProjectRef(rest_client_library_uri, "rest_scala_core")

  lazy val rest_json_circe_module = ProjectRef(rest_client_library_uri, "rest_json_circe_module")

  lazy val elasticsearch_scala_core: Project = Project(
    "elasticsearch_scala_core",
    file("elasticsearch_scala_core"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Scala Core",
      version := esScalaDriverVersion,
      apiURL := Some(url(s"$apiRoot/$githubName/$docVersion/elasticsearch_scala_core/")),
      autoAPIMappings := true,
      libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaBuildVersion,
      libraryDependencies += utestJvmDeps,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  ).dependsOn(rest_scala_core).dependsOn(rest_json_circe_module)

  lazy val elasticsearch_scala_java_client: Project = Project(
    "elasticsearch_scala_java_client",
    file("elasticsearch_scala_java_client"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Java Client Scala bridge",
      version := esScalaDriverVersion,
      apiURL := Some(url(s"$apiRoot/$githubName/$docVersion/elasticsearch_scala_java_client/")),
      autoAPIMappings := true,
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
      ,
      assemblyMergeStrategy in assembly := {
        case PathList("META-INF", xs @ _*) => MergeStrategy.discard
        case x => MergeStrategy.first
      },
      assemblyJarName in assembly := "elasticsearch_shell.jar",
      mainClass in assembly := Some("org.elastic.elasticsearch.scala.driver.jvm.ShellMain")
    )
  )
  .dependsOn(rest_json_circe_module, elasticsearch_scala_java_client)

  // Doc project
  // (from https://groups.google.com/forum/#!topic/simple-build-tool/QXFsjLozLyU)
  def mainDirs(project: ProjectRef) = unmanagedSourceDirectories in project in Compile
  def mainDirs(project: Project) = unmanagedSourceDirectories in project in Compile
  lazy val doc = Project("doc", file("doc"))
      .dependsOn(rest_scala_core, rest_json_circe_module, elasticsearch_scala_core, elasticsearch_scala_java_client)
      .settings(buildSettings ++ Seq(
        version := esScalaDriverVersion,
        unmanagedSourceDirectories in Compile <<= Seq(
            mainDirs(elasticsearch_scala_core),
            mainDirs(elasticsearch_scala_java_client),
            mainDirs(rest_scala_core),
            mainDirs(rest_json_circe_module)
          ).join.apply {(s) => s.flatten}
      )
    )
}