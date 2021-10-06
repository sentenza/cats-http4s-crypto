package com.github.sentenza.catsh4s.service

import cats.effect.{IO, Resource}
import com.github.sentenza.catsh4s.config.CmcConfig
import com.github.sentenza.catsh4s.infrastructure.service.CoinMarketCapService
import fs2.Stream
import org.http4s.Response
import org.http4s.client.Client
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec

class CoinMarketCapServiceSpec extends wordspec.AnyWordSpec with Matchers {
  class TestCase {
    val cmcConfig: CmcConfig =
      CmcConfig(baseUrl = "https://test-api.coinmarketcap.com/v1", apikey = "1234-5678-91011")

    def cmcService(responseBody: String, faultyClient: Boolean): CoinMarketCapService[IO] = {
      val client = if (faultyClient) faultyHttpClient else httpClient(responseBody)
      CoinMarketCapService.impl[IO](client, cmcConfig)
    }

    private def httpClient(body: String): Client[IO] = Client.apply[IO] { _ =>
      Resource.eval(IO(Response[IO](body = Stream.emits(body.getBytes("UTF-8")))))
    }

    private def faultyHttpClient: Client[IO] = Client.apply[IO] { _ =>
      Resource.eval(IO(Response.notFound[IO]))
    }
  }
}
