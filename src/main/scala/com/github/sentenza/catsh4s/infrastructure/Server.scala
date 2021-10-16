package com.github.sentenza.catsh4s.infrastructure

import cats.effect.{Async, Resource}
import com.comcast.ip4s._
import com.github.sentenza.catsh4s.config.AppConfig
import com.github.sentenza.catsh4s.db.Database
import com.github.sentenza.catsh4s.infrastructure.routes.{CoinMarketCapRoutes, HealthRoutes, MainApiRoutes}
import com.github.sentenza.catsh4s.infrastructure.service.{CoinMarketCapService, PingService}
import doobie.ExecutionContexts
import org.http4s.HttpApp
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object Server {

  def run[F[_]: Async]: F[Nothing] = {
    import cats.syntax.semigroupk._ // to be able to combine routes
    (for {
      client        <- EmberClientBuilder.default[F].build
      generalConfig <- AppConfig.load()
      ec            <- ExecutionContexts.fixedThreadPool[F](generalConfig.database.threadPoolSize)
      _             <- Database.transactor(generalConfig.database, ec)
      _             <- Resource.eval(Database.initializeDb(generalConfig.database))
      pingService    = PingService.impl[F]
      cmcService     = CoinMarketCapService.impl[F](client, generalConfig.cmc)
      mainApiRoutes  = MainApiRoutes.essentialRoutes[F](pingService)
      healthRoutes   = HealthRoutes.healthRoutes[F]
      cmcRoutes      = CoinMarketCapRoutes.cmcRoutes[F](cmcService)
      combinedRoutes = mainApiRoutes <+> healthRoutes <+> cmcRoutes
      httpApp        = combinedRoutes.orNotFound

      // Logger Middleware
      finalHttpApp: HttpApp[F] = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

      _ <-
        EmberServerBuilder
          .default[F]
          .withHost(
            Ipv4Address
              .fromString(generalConfig.server.host)
              .getOrElse(ipv4"0.0.0.0")
          )
          .withPort(Port.fromInt(generalConfig.server.port).getOrElse(port"8080"))
          .withHttpApp(finalHttpApp)
          .build

    } yield ()).useForever
  }
}
