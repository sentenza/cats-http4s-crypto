package com.github.sentenza.catsh4s

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = IO(println("Cats HTTP4S Main()")) *> IO.pure(ExitCode.Success)
  //Server.stream[IO].compile.drain.as(ExitCode.Success)
}
