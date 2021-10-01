package com.github.sentenza.catsh4s.domain

import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

final case class Pong(pong: String) extends AnyVal

object Pong {
  implicit val pongEncoder: Encoder[Pong] = new Encoder[Pong] {
    final def apply(p: Pong): Json = Json.obj(
      ("message", Json.fromString(p.pong))
    )
  }
  implicit def pongEntityEncoder[F[_]]: EntityEncoder[F, Pong] =
    jsonEncoderOf[F, Pong]
}
