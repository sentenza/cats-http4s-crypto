package com.github.sentenza.catsh4s.domain.crypto

trait CryptoCurrencyRepositoryAlgebra[F[_]] {

  def create(crypto: CryptoCurrency): F[CryptoCurrency]

  def get(symbol: CryptoCurrency.Symbol): F[Option[CryptoCurrency]]

  def delete(symbol: CryptoCurrency.Symbol): F[Option[CryptoCurrency]]
}
