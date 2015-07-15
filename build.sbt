name := "my-first-app"

scalaVersion := "2.11.7"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += jdbc
libraryDependencies += "com.typesafe.play" %% "play-slick" % "1.0.0"
libraryDependencies += "com.h2database" % "h2" % "1.4.187"
