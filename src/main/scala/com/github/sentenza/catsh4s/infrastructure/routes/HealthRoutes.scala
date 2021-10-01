package com.github.sentenza.catsh4s.infrastructure.routes

import cats.effect.Sync
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object HealthRoutes {
  def healthRoutes[F[_]: Sync]: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "h" / "healthz" => Ok()
      case GET -> Root / "h" / "ready"   => Ok()
    }
  }
}
