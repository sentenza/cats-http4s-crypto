package com.github.sentenza.catsh4s.infrastructure

import com.github.sentenza.catsh4s.domain.crypto.CryptoCurrency
import com.github.sentenza.catsh4s.domain.generic.Pong
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

package object json {
  // Cryptocurrency
  implicit val nameDecoder: Decoder[CryptoCurrency.Name]      = deriveDecoder[CryptoCurrency.Name]
  implicit val symbolDecoder: Decoder[CryptoCurrency.Symbol]  = deriveDecoder[CryptoCurrency.Symbol]
  implicit val slugDecoder: Decoder[CryptoCurrency.Slug]      = deriveDecoder[CryptoCurrency.Slug]
  implicit val cryptoCurrencyDecoder: Decoder[CryptoCurrency] = deriveDecoder[CryptoCurrency]

  // Pong
  implicit val pongEncoder: Encoder[Pong] = new Encoder[Pong] {
    final def apply(p: Pong): Json = Json.obj(
      ("message", Json.fromString(p.pong))
    )
  }
  implicit def pongEntityEncoder[F[_]]: EntityEncoder[F, Pong] =
    jsonEncoderOf[F, Pong]
}
