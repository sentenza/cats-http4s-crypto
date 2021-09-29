package com.github.sentenza.catsh4s.infrastructure.endpoint

import cats.effect.Sync
import cats.implicits._
import com.github.sentenza.catsh4s.domain.Name
import com.github.sentenza.catsh4s.infrastructure.service.HelloWorldService
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object CatsH4sRoutes {
  def helloWorldRoutes[F[_]: Sync](H: HelloWorldService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "hello" / name =>
      for {
        greeting <- H.hello(Name(name))
        resp     <- Ok(greeting)
      } yield resp
    }
  }
}
