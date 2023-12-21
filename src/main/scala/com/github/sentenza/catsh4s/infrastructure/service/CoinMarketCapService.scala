package com.github.sentenza.catsh4s.infrastructure.service

import cats.effect.Concurrent
import cats.implicits.catsSyntaxMonadError
import com.github.sentenza.catsh4s.config.CmcConfig
import com.github.sentenza.catsh4s.domain.crypto.CryptoCurrency
import com.github.sentenza.catsh4s.infrastructure.json.cryptoCurrencyDecoder
import org.http4s.circe.jsonOf
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.client.Client
import org.http4s.headers.Accept
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.EntityDecoder
import org.http4s.Headers
import org.http4s.MediaType
import org.http4s.Method.GET
import org.http4s.Request
import org.http4s.Uri

sealed trait CoinMarketCapService[F[_]] {
  // request the latest quote for specific cryptocurrencies
  def getLatestQuote(symbol: CryptoCurrency.Symbol): F[String]
}

object CoinMarketCapService {
  def apply[F[_]](implicit ev: CoinMarketCapService[F]): CoinMarketCapService[F] = ev

  final case class ApiServiceUnavailable(e: Throwable) extends RuntimeException

  object JsonHelpers {
    implicit def cryptoCurrencyEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, CryptoCurrency] =
      jsonOf[F, CryptoCurrency]
  }

  def impl[F[_]: Concurrent](C: Client[F], cmcConfig: CmcConfig): CoinMarketCapService[F] =
    new CoinMarketCapService[F] {
      val dsl: Http4sClientDsl[F] = new Http4sClientDsl[F] {}
      import dsl._
      val basePath: Uri       = Uri.unsafeFromString(cmcConfig.baseUrl)
      val authHeader: Headers = Headers("X-CMC_PRO_API_KEY" -> cmcConfig.apikey)

      /**
       * @example
       *   curl -H "X-CMC_PRO_API_KEY: b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c" -H "Accept: application/json" \
       * -d "symbol=btc" -G "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest"
       */
      def getLatestQuote(symbol: CryptoCurrency.Symbol): F[String] = {
        val cmcEndpoint = basePath
          .withPath(path"/v1/cryptocurrency/quotes/latest")
          .withQueryParam("symbol", symbol.value)
        val request: Request[F] = GET(cmcEndpoint, Accept(MediaType.application.json), authHeader)
        C.expect[String](req = request).adaptError {
          case t =>
            ApiServiceUnavailable(t)
        }
      }
    }
}
