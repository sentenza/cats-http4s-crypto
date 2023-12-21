package com.github.sentenza.catsh4s.infrastructure.routes

import cats.effect.Sync
import cats.implicits._
import com.github.sentenza.catsh4s.infrastructure.json._
import com.github.sentenza.catsh4s.infrastructure.service.PingService
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes

object MainApiRoutes {
  def essentialRoutes[F[_]: Sync](H: PingService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "api" / "ping" / name =>
        for {
          message <- H.pong(name)
          resp    <- Ok(message)
        } yield resp
    }
  }
}
