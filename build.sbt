val PureConfigVersion = "0.17.0"
val Http4sVersion     = "0.23.5"
val CirceVersion      = "0.14.1"
val EnumeratumVersion = "1.7.0"
val LogbackVersion    = "1.2.6"
val ScalaTestVersion  = "3.2.10"
val FlexmarkVersion   = "0.62.2"

lazy val root = (project in file("."))
  .settings(
    organization := "com.github.sentenza",
    name         := "cats-h4s",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-ember-server"           % Http4sVersion,
      "org.http4s"            %% "http4s-ember-client"           % Http4sVersion,
      "org.http4s"            %% "http4s-circe"                  % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"                    % Http4sVersion,
      "io.circe"              %% "circe-generic"                 % CirceVersion,
      "io.circe"              %% "circe-literal"                 % CirceVersion,
      "com.beachape"          %% "enumeratum"                    % EnumeratumVersion,
      "com.beachape"          %% "enumeratum-circe"              % EnumeratumVersion,
      "com.github.pureconfig" %% "pureconfig"                    % PureConfigVersion,
      "com.github.pureconfig" %% "pureconfig-cats-effect"        % PureConfigVersion,
      "org.scalatest"         %% "scalatest"                     % ScalaTestVersion          % Test,
      "org.scalatestplus"     %% "mockito-3-4"                   % (ScalaTestVersion + ".0") % Test,
      "com.vladsch.flexmark"   % "flexmark-all"                  % FlexmarkVersion           % Test,
      "org.typelevel"         %% "cats-effect-testing-scalatest" % "1.3.0"                   % Test,
      "ch.qos.logback"         % "logback-classic"               % LogbackVersion,
      "org.scalameta"         %% "svm-subs"                      % "20.2.0"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework")
  )
