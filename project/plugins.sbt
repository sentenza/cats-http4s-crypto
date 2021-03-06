// Easily manage scalac settings across scala versions with this
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.3.1")

// Makes our code tidy
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")

// Revolver allows us to use re-start and work a lot faster!
addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

// To keep our dependencies up to date
addSbtPlugin("net.vonbuchholtz" % "sbt-dependency-check" % "4.1.0")

// To enable test coverage analysis
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.3")

addSbtPlugin("com.eed3si9n"     % "sbt-assembly" % "1.2.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates"  % "0.6.2")
