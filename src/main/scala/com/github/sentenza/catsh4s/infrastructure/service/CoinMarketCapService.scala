package com.github.sentenza.catsh4s.infrastructure.service

import cats.effect.Concurrent
import cats.implicits.catsSyntaxMonadError
import com.github.sentenza.catsh4s.domain.CryptoCurrency
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.Method.GET
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.{EntityDecoder, Uri}

sealed trait CoinMarketCapService[F[_]] {
  // request the latest quote for specific cryptocurrencies
  def getLatestQuote(symbol: CryptoCurrency.Symbol): F[CryptoCurrency]
}

object CoinMarketCapService {
  def apply[F[_]](implicit ev: CoinMarketCapService[F]): CoinMarketCapService[F] = ev

  final case class ApiServiceUnavailable(e: Throwable) extends RuntimeException

  object JsonHelpers {
    implicit val nameDecoder: Decoder[CryptoCurrency.Name] = deriveDecoder[CryptoCurrency.Name]
    implicit val symbolDecoder: Decoder[CryptoCurrency.Symbol] = deriveDecoder[CryptoCurrency.Symbol]
    implicit val slugDecoder: Decoder[CryptoCurrency.Slug] = deriveDecoder[CryptoCurrency.Slug]
    implicit val cryptoCurrencyDecoder: Decoder[CryptoCurrency] = deriveDecoder[CryptoCurrency]
    implicit def cryptoCurrencyEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, CryptoCurrency] = jsonOf[F, CryptoCurrency]
  }

  def impl[F[_]: Concurrent](C: Client[F]): CoinMarketCapService[F] =
    new CoinMarketCapService[F] {
      val dsl: Http4sClientDsl[F] = new Http4sClientDsl[F] {}
      import JsonHelpers._
      import dsl._

      def getLatestQuote(symbol: CryptoCurrency.Symbol): F[CryptoCurrency] = {
        val externalUrl = Uri.unsafeFromString("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest")
        C.expect[CryptoCurrency](GET(externalUrl / symbol.value)).adaptError {
          case t => ApiServiceUnavailable(t)
        }
      }
    }
}

