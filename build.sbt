import _root_.play.sbt.PlayImport._
import _root_.play.sbt.PlayScala
import _root_.play.sbt.routes.RoutesKeys._
import _root_.play.twirl.sbt.Import.TwirlKeys

name := """play-slick-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

routesGenerator := InjectedRoutesGenerator

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.h2database" % "h2" % "1.4.192",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test",
  // https://mvnrepository.com/artifact/com.typesafe.scala-logging/scala-logging_2.11
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",

    specs2 % Test,
  guice
)


/*libraryDependencies += "com.typesafe.slick" % "slick-extensions_2.11" % "3.0.0"*/
resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
sourceDirectories in (Compile, TwirlKeys.compileTemplates) :=
  (unmanagedSourceDirectories in Compile).value

fork in run := true
