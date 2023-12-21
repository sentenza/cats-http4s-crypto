package com.github.sentenza.catsh4s.infrastructure.repository.doobie

import cats.effect.unsafe.implicits.global
import cats.effect.IO
import com.github.sentenza.catsh4s.domain.crypto.CryptoCurrency
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec
import org.scalatest.OptionValues

class DoobieCryptoCurrencyRepositorySpec
    extends wordspec.AnyWordSpec
    with PostgresTestLayout
    with Matchers
    with OptionValues {

  class TestCase {
    val repo = new DoobieCryptoCurrencyRepository[IO](getTransactor(dbConfig))
  }

  "CryptoCurrencyRepository" should {
    "get Bitcoin record at least" in new TestCase {
      private val btc = CryptoCurrency.Symbol("BTC")
      repo.get(btc).unsafeRunSync().value.name shouldBe CryptoCurrency.Name("Bitcoin")
    }
  }
}
