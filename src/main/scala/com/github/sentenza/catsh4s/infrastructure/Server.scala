package com.github.sentenza.catsh4s.infrastructure

import cats.effect.{Async, Resource}
import com.comcast.ip4s.{Ipv4Address, Port}
import com.github.sentenza.catsh4s.config.AppConfig
import com.github.sentenza.catsh4s.infrastructure.endpoint.CatsH4sRoutes
import com.github.sentenza.catsh4s.infrastructure.service.HelloWorldService
import fs2.Stream
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s.HttpApp
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object Server {

  def stream[F[_]: Async]: Stream[F, Nothing] = {
    for {
      generalConfig <- Stream.resource(AppConfig.load())
      helloWorldService = HelloWorldService.impl[F]

      httpApp = CatsH4sRoutes.helloWorldRoutes[F](helloWorldService).orNotFound

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
