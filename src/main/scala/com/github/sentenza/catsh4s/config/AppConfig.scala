package com.github.sentenza.catsh4s.config

import cats.effect.Async
import cats.effect.Resource
import pureconfig.generic.auto._
import pureconfig.module.catseffect.syntax._
import pureconfig.ConfigSource

case class ServerConfig(host: String, port: Int)
case class CmcConfig(baseUrl: String, apikey: String)
case class DatabaseConfig(driver: String, url: String, user: String, password: String, threadPoolSize: Int)

case class AppConfig(server: ServerConfig, cmc: CmcConfig, database: DatabaseConfig)

object AppConfig {
  def load[F[_]: Async](): Resource[F, AppConfig] =
    Resource.eval(
      ConfigSource.default
        .loadF[F, AppConfig]()
    )
}
