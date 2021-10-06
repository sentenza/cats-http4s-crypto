package com.github.sentenza.catsh4s.config

import cats.effect.{Async, Resource}
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import pureconfig.module.catseffect.syntax._

case class ServerConfig(host: String, port: Int)
case class CmcConfig(baseUrl: String, apikey: String)
case class AppConfig(server: ServerConfig, cmc: CmcConfig)

object AppConfig {
  def load[F[_]: Async](): Resource[F, AppConfig] =
    Resource.eval(
      ConfigSource.default
        .loadF[F, AppConfig]()
    )
}
