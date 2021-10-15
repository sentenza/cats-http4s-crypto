package com.github.sentenza.catsh4s.infrastructure.routes

import cats.effect.Sync
import com.github.sentenza.catsh4s.infrastructure.service.CoinMarketCapService
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import cats.implicits._
import com.github.sentenza.catsh4s.domain.crypto.CryptoCurrency

object CoinMarketCapRoutes {
  def cmcRoutes[F[_]: Sync](CMC: CoinMarketCapService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] { case GET -> Root / "cmc" / "quote" / currencySymbol =>
      for {
        currency <- CMC.getLatestQuote(CryptoCurrency.Symbol(currencySymbol))
        response <- Ok(currency.toString)
      } yield response
    }
  }

}
