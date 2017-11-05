mainClass in Compile := Some("com.simservice.SimService")
fork in run := true
enablePlugins(JavaAppPackaging)

// Docker settings
maintainer in Docker := "Michael DiSalvo <michael.vincent.disalvo@gmail.com>"
packageName in Docker := "sim-service-scala"
packageSummary in Docker := "A Demo Text Similarity Service"
dockerBaseImage := "openjdk:jdk-oraclelinux7"
dockerRepository := Some("michaelvdisalvo")
dockerUpdateLatest := true
dockerExposedPorts := Seq(8080)

lazy val simservice = (project in file("."))
  .settings(
    organization := "com.simservice",
    version := "1.0",
    name := "sim-service-scala",
    description := """A Demo Text Similarity Service""",
    scalaVersion := "2.12.10",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    libraryDependencies ++= {
      val akkaVersion = "2.6.3"
      val akkaHttpVersion = "10.1.11"
      def typesafe = "com.typesafe.akka"
      Seq(
        typesafe %% "akka-actor" % akkaVersion,
        typesafe %% "akka-stream" % akkaVersion,
        typesafe %% "akka-http-spray-json" % akkaHttpVersion,
        typesafe %% "akka-http" % akkaHttpVersion,
        "org.apache.commons" % "commons-text" % "1.8",
        "com.google.guava" % "guava" % "28.2-jre",
        "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.0.4",
        "org.scalatest" %% "scalatest" % "3.1.0" % Test
      )
    }
  )

unmanagedResourceDirectories in Compile += {
  baseDirectory.value / "src/main/resources"
}
