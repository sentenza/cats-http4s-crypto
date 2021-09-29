package com.github.sentenza.catsh4s.infrastructure.service

import cats.Applicative
import cats.implicits._
import com.github.sentenza.catsh4s.domain.{Greeting, Name}

trait HelloWorldService[F[_]] {
  def hello(n: Name): F[Greeting]
}

object HelloWorldService {
  implicit def apply[F[_]](implicit ev: HelloWorldService[F]): HelloWorldService[F] = ev

  def impl[F[_]: Applicative]: HelloWorldService[F] = (n: Name) => Greeting("Hello, " + n.name).pure[F]
}
