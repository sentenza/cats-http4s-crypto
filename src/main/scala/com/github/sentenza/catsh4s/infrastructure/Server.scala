package com.github.sentenza.catsh4s.infrastructure

import cats.effect.{Async, Resource}
import cats.implicits.catsSyntaxFlatMapOps
import com.comcast.ip4s._
import com.github.sentenza.catsh4s.config.AppConfig
import com.github.sentenza.catsh4s.infrastructure.routes.{HealthRoutes, MainApiRoutes}
import com.github.sentenza.catsh4s.infrastructure.service.PingService
import fs2.Stream
import org.http4s.HttpApp
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object Server {

  def stream[F[_]: Async]: Stream[F, Nothing] = {
    import cats.syntax.semigroupk._
    for {
      generalConfig <- Stream.resource(AppConfig.load())
      helloWorldService = PingService.impl[F]
      mainApiRoutes     = MainApiRoutes.essentialRoutes[F](helloWorldService)
      healthRoutes      = HealthRoutes.healthRoutes[F]
      combinedRoutes    = mainApiRoutes <+> healthRoutes
      httpApp           = combinedRoutes.orNotFound

      // With Middlewares in place
      finalHttpApp: HttpApp[F] = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

      exitCode <- Stream.resource(
        EmberServerBuilder
          .default[F]
          .withHost(
            Ipv4Address
              .fromString(generalConfig.server.host)
              .getOrElse(ipv4"0.0.0.0")
          )
          .withPort(Port.fromInt(generalConfig.server.port).getOrElse(port"8080"))
          .withHttpApp(finalHttpApp)
          .build >>
          Resource.eval(Async[F].never)
      )
    } yield exitCode
  }.drain
}
