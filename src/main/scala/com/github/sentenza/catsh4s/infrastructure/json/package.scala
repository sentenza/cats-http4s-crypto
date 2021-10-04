package com.github.sentenza.catsh4s.infrastructure

import com.github.sentenza.catsh4s.domain.Pong
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

package object json {
  implicit val pongEncoder: Encoder[Pong] = new Encoder[Pong] {
    final def apply(p: Pong): Json = Json.obj(
      ("message", Json.fromString(p.pong))
    )
  }
  implicit def pongEntityEncoder[F[_]]: EntityEncoder[F, Pong] =
    jsonEncoderOf[F, Pong]
}
