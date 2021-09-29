package com.github.sentenza.catsh4s

import cats.effect.{ExitCode, IO, IOApp}
import com.github.sentenza.catsh4s.infrastructure.Server

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = Server.stream[IO].compile.drain.as(ExitCode.Success)
}
