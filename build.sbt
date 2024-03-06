val PureConfigVersion = "0.17.4"
val Http4sVersion     = "0.23.26"
val CirceVersion      = "0.14.6"
val EnumeratumVersion = "1.7.3"
val LogbackVersion    = "1.4.14"
val ScalaTestVersion  = "3.2.17"
val FlexmarkVersion   = "0.64.8"
val FlywayVersion     = "9.22.3"
val DoobieVersion     = "1.0.0-RC5"

lazy val root = (project in file("."))
  .settings(
    organization := "com.github.sentenza",
    name         := "cats-h4s",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.12",
    libraryDependencies ++= Seq(
      // http4s
      "org.http4s" %% "http4s-ember-server" % Http4sVersion,
      "org.http4s" %% "http4s-ember-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe"        % Http4sVersion,
      "org.http4s" %% "http4s-dsl"          % Http4sVersion,
      // circe
      "io.circe" %% "circe-generic" % CirceVersion,
      "io.circe" %% "circe-literal" % CirceVersion,
      // pureconfig
      "com.github.pureconfig" %% "pureconfig"             % PureConfigVersion,
      "com.github.pureconfig" %% "pureconfig-cats-effect" % PureConfigVersion,
      // doobie
      "org.flywaydb"  % "flyway-core"      % FlywayVersion,
      "org.tpolecat" %% "doobie-core"      % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari"    % DoobieVersion, // HikariCP transactor.
      "org.tpolecat" %% "doobie-postgres"  % DoobieVersion, // Postgres driver 42.2.23 + type mappings.
      "org.tpolecat" %% "doobie-h2"        % DoobieVersion, // H2 driver 1.4.200 + type mappings.
      "org.tpolecat" %% "doobie-scalatest" % DoobieVersion % Test,
      // enumeratum
      "com.beachape" %% "enumeratum"       % EnumeratumVersion,
      "com.beachape" %% "enumeratum-circe" % EnumeratumVersion,
      // scalatest
      "org.scalatest"       %% "scalatest"                     % ScalaTestVersion % Test,
      "org.scalatestplus"   %% "mockito-3-4"                   % "3.2.10.0"       % Test,
      "com.vladsch.flexmark" % "flexmark-all"                  % FlexmarkVersion  % Test,
      "org.typelevel"       %% "cats-effect-testing-scalatest" % "1.5.0"          % Test,
      "ch.qos.logback"       % "logback-classic"               % LogbackVersion,
      "org.scalameta"       %% "svm-subs"                      % "20.2.0"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
  )

// Commands
Global / onChangedBuildSource := ReloadOnSourceChanges
