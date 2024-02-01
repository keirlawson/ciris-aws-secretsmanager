ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"

name := "ciris-aws-secretsmanager"
organization := "io.github.keirlawson"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

scalaVersion := "3.3.1"
crossScalaVersions := Seq(scalaVersion.value, "2.13.12", "2.12.18")
releaseCrossBuild := true

libraryDependencies ++= Seq(
  "is.cir" %% "ciris" % "3.5.0",
  "org.typelevel" %% "cats-core" % "2.10.0",
  "org.typelevel" %% "cats-effect" % "3.5.2",
  "software.amazon.awssdk" % "secretsmanager" % "2.23.14",
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
)

publishTo := sonatypePublishToBundle.value

testFrameworks += new TestFramework("munit.Framework")

sonatypeProfileName := "io.github.keirlawson"
publishMavenStyle := true

homepage := Some(url("https://github.com/keirlawson/ciris-aws-secretsmanager"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/keirlawson/ciris-aws-secretsmanager"),
    "scm:git@github.com:keirlawson/ciris-aws-secretsmanager.git"
  )
)
developers := List(
  Developer(
    id = "keirlawson",
    name = "Keir Lawson",
    email = "keirlawson@gmail.com",
    url = url("https://github.com/keirlawson/")
  )
)

import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

Compile / doc / sources := Nil
