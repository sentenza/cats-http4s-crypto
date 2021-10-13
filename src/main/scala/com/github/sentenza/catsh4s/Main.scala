package com.github.sentenza.catsh4s

import cats.effect.{IO, IOApp}
import com.github.sentenza.catsh4s.infrastructure.Server

object Main extends IOApp.Simple {
  override def run: IO[Unit] = Server.run[IO]
}
