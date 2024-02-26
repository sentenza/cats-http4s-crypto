// Makes our code tidy
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2")

// Revolver allows us to use re-start and work a lot faster!
addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

// To enable test coverage analysis
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.11")

// To keep our dependencies up to date
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.4")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.1.5")
