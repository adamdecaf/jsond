name := "jsond"

organization := "org.decaf"

scalaVersion := "2.10.1"

scalacOptions ++= Seq( "-feature" )

libraryDependencies ++= Seq(
  "jsonz" %% "jsonz" % "0.3-SNAPSHOT"
)
