name := "neptune-api"
// TODO: Update if need be
organization := "in.tap"
version := "0.0.1-SNAPSHOT"
description := "Gremlin Graph Driver & Queries."

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true

scalaVersion := "2.12.8"

// TODO: Update this here with Impl of AbstractMain (& package)
mainClass in assembly := Some("in.tap.base.neptune.api.Main")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}

val gremlinVersion: String = "3.4.1"
libraryDependencies ++= Seq(
  // gremlins
  "org.apache.tinkerpop" % "gremlin-driver" % gremlinVersion,
  "org.apache.tinkerpop" % "tinkergraph-gremlin" % gremlinVersion,
  // gremlins scala wrapper
  "com.michaelpollmeier" %% "gremlin-scala" % "3.4.4.5",
  // aws sdk
  "com.amazonaws" % "aws-java-sdk-core" % "1.11.307",
  "com.amazonaws" % "amazon-neptune-sigv4-signer" % "2.0.2",
  "com.amazonaws" % "amazon-neptune-gremlin-java-sigv4" % "2.0.2",
  // arg parser
  "com.monovore" %% "decline" % "1.0.0",
  "com.beachape" %% "enumeratum" % "1.5.15"
)
