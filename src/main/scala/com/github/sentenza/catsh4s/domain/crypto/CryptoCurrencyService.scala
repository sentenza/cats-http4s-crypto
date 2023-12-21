package com.github.sentenza.catsh4s.domain.crypto

import cats.data.EitherT
import cats.Functor
import com.github.sentenza.catsh4s.domain.CurrencyNotFoundError

class CryptoCurrencyService[F[_]](ccRepo: CryptoCurrencyRepositoryAlgebra[F]) {

  def get(symbol: CryptoCurrency.Symbol)(
      implicit F: Functor[F]
  ): EitherT[F, CurrencyNotFoundError.type, CryptoCurrency] =
    EitherT.fromOptionF(ccRepo.get(symbol), CurrencyNotFoundError)

}

object CryptoCurrencyService {
  def apply[F[_]](ccRepo: CryptoCurrencyRepositoryAlgebra[F]): CryptoCurrencyService[F] =
    new CryptoCurrencyService[F](ccRepo)
}
