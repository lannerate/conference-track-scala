name := "conference-track-scala"

version := "1.0"

scalaVersion := "2.11.8"

mainClass in (Compile, run) := Some("conf.track.ConferenceApp")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

