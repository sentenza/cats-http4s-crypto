package com.github.sentenza.catsh4s.config

import cats.effect.{Async, Resource}
import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import pureconfig.module.catseffect.syntax._

case class ServerConfig(host: String, port: Int)
case class AppConfig(server: ServerConfig)

object AppConfig {
  def load[F[_]: Async](configFile: String = "application.conf"): Resource[F, AppConfig] =
    Resource.eval(
      ConfigSource
        .fromConfig(ConfigFactory.load(configFile))
        .loadF[F, AppConfig]()
    )
}
