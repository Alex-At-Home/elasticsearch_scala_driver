import sbt.Keys._

name := "elasticsearch_scala_driver"

version := "1.0"

scalaVersion := "2.11.8"

testFrameworks += new TestFramework("utest.runner.Framework")

lazy val elasticsearch_scala_core = project
    .settings(
      libraryDependencies += "com.lihaoyi" %% "utest" % "0.4.3" % "test",
      testFrameworks += new TestFramework("utest.runner.Framework")
    )

lazy val elasticsearch_scala_java_client = project

lazy val elasticsearch_scala_js_client = project
