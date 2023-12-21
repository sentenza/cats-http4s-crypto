package com.github.sentenza.catsh4s.infrastructure.repository.doobie

import cats.effect.Async
import com.github.sentenza.catsh4s.domain.crypto.CryptoCurrency
import com.github.sentenza.catsh4s.domain.crypto.CryptoCurrencyRepositoryAlgebra
import doobie.implicits._
import doobie.util.query.Query0
import doobie.util.transactor.Transactor

object CryptoCurrencySQL {

  def select(symbol: CryptoCurrency.Symbol): Query0[CryptoCurrency] = sql"""
          SELECT symbol, name, slug
          FROM crypto_currencies
          WHERE symbol = ${symbol.value}
      """.query[CryptoCurrency]
}

class DoobieCryptoCurrencyRepository[F[_]: Async](xa: Transactor[F]) extends CryptoCurrencyRepositoryAlgebra[F] {
  import CryptoCurrencySQL._

  override def create(crypto: CryptoCurrency): F[CryptoCurrency] = ???

  override def get(symbol: CryptoCurrency.Symbol): F[Option[CryptoCurrency]] =
    select(symbol).option.transact(xa)

  override def delete(symbol: CryptoCurrency.Symbol): F[Option[CryptoCurrency]] = ???
}
