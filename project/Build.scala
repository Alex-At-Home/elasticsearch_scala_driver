import sbt._
import Keys._
import sbtassembly.AssemblyKeys._
import sbtassembly.{MergeStrategy, PathList}
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

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

  // (been seeing some issues with NodeJS in travis, so default to Rhino)
  val useRhino = true

  // Dependencies:

  val esRestVersion = "5.0.0-alpha5"
  lazy val esRestDeps = "org.elasticsearch.client" % "rest" % esRestVersion

  val ammoniteVersion = "0.7.6"
  lazy val ammoniteDeps = "com.lihaoyi" % "ammonite" % ammoniteVersion cross CrossVersion.full

  val utestVersion = "0.4.3"
  lazy val utestJvmDeps = "com.lihaoyi" %% "utest" % utestVersion % "test"

  lazy val simpleScalaHttpServer = "com.tumblr" %% "colossus" % "0.8.1" % "test"

  val rest_client_library_branch = "" //("#$branch" or "" for master)
  val rest_client_library_uri =
    uri(s"https://github.com/Alex-At-Home/rest_client_library.git$rest_client_library_branch")

  // Project definitions

  val esScalaDriverVersion = "0.1-SNAPSHOT"

  lazy val root = Project(
    "root",
    file("."),
    settings = buildSettings
  )
  .enablePlugins(ScalaJSPlugin)
  .aggregate(
    elasticsearch_scala_coreJVM,
    elasticsearch_scala_coreJS,
    elasticsearch_scala_java_clientJVM,
    elasticsearch_scala_shellJVM
  )

  val githubName = "elasticsearch_scala_driver"
  val apiRoot = "https://alex-at-home.github.io"
  val docVersion = "current"

  lazy val rest_scala_coreJVM = ProjectRef(rest_client_library_uri, "rest_scala_coreJVM")
  lazy val rest_scala_coreJS = ProjectRef(rest_client_library_uri, "rest_scala_coreJS")

  lazy val rest_json_circe_moduleJVM = ProjectRef(rest_client_library_uri, "rest_json_circe_moduleJVM")
  lazy val rest_json_circe_moduleJS = ProjectRef(rest_client_library_uri, "rest_json_circe_moduleJS")

  lazy val elasticsearch_scala_core = crossProject
    .in(file("elasticsearch_scala_core"))
    .settings(
      buildSettings ++ Seq(
        name := "Elasticsearch Scala Core",
        version := esScalaDriverVersion,
        apiURL := Some(url(s"$apiRoot/$githubName/$docVersion/")),
        autoAPIMappings := true,
        libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaBuildVersion,
        libraryDependencies += "com.lihaoyi" %%% "utest" % utestVersion % "test",
        testFrameworks += new TestFramework("utest.runner.Framework")
      ): _*)
    .jvmSettings()
    .jsSettings(
      scalaJSUseRhino in Global := useRhino
    )
  lazy val elasticsearch_scala_coreJVM = elasticsearch_scala_core.jvm dependsOn rest_scala_coreJVM dependsOn rest_json_circe_moduleJVM
  lazy val elasticsearch_scala_coreJS = elasticsearch_scala_core.js dependsOn rest_scala_coreJS dependsOn rest_json_circe_moduleJS

  lazy val elasticsearch_scala_java_clientJVM: Project = Project(
    "elasticsearch_scala_java_client",
    file("elasticsearch_scala_java_client"),
    settings = buildSettings ++ Seq(
      name := "Elasticsearch Java Client Scala bridge",
      version := esScalaDriverVersion,
      apiURL := Some(url(s"$apiRoot/$githubName/$docVersion/")),
      autoAPIMappings := true,
      libraryDependencies += esRestDeps,
      libraryDependencies += utestJvmDeps,
      libraryDependencies += simpleScalaHttpServer,
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  ).dependsOn(elasticsearch_scala_coreJVM)

  lazy val elasticsearch_scala_shellJVM: Project = Project(
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
  .dependsOn(rest_json_circe_moduleJVM, elasticsearch_scala_java_clientJVM)

  // Doc project
  // (from https://groups.google.com/forum/#!topic/simple-build-tool/QXFsjLozLyU)
  def mainDirs(project: ProjectRef) = unmanagedSourceDirectories in project in Compile
  def mainDirs(project: Project) = unmanagedSourceDirectories in project in Compile
  lazy val doc = Project("doc", file("doc"))
      .dependsOn(
        rest_scala_coreJVM, rest_json_circe_moduleJVM,
        elasticsearch_scala_coreJVM, elasticsearch_scala_java_clientJVM
      )
      .settings(buildSettings ++ Seq(
        version := esScalaDriverVersion,
        unmanagedSourceDirectories in Compile <<= Seq(
            mainDirs(elasticsearch_scala_coreJVM),
            mainDirs(elasticsearch_scala_java_clientJVM),
            mainDirs(rest_scala_coreJVM),
            mainDirs(rest_json_circe_moduleJVM)
          ).join.apply {(s) => s.flatten}
      )
    )
}