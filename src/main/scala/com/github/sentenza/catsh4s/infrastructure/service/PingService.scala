package com.github.sentenza.catsh4s.infrastructure.service

import cats.implicits._
import cats.Applicative
import com.github.sentenza.catsh4s.domain.generic.Pong

trait PingService[F[_]] {
  def pong(n: String): F[Pong]
}

object PingService {
  implicit def apply[F[_]](implicit ev: PingService[F]): PingService[F] = ev

  def impl[F[_]: Applicative]: PingService[F] = (n: String) => Pong("PONG > " + n).pure[F]
}
