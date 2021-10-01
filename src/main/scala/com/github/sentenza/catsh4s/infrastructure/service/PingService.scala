package com.github.sentenza.catsh4s.infrastructure.service

import cats.Applicative
import cats.implicits._
import com.github.sentenza.catsh4s.domain.{Pong, Name}

trait PingService[F[_]] {
  def pong(n: Name): F[Pong]
}

object PingService {
  implicit def apply[F[_]](implicit ev: PingService[F]): PingService[F] = ev

  def impl[F[_]: Applicative]: PingService[F] = (n: Name) => Pong("Hello, " + n.name).pure[F]
}
