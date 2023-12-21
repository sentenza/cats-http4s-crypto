package com.github.sentenza.catsh4s.domain.crypto

/**
 * Represents a Cryptocurrency
 *
 * @param symbol
 *   e.g. "BTC"
 * @param name
 *   e.g. "Bitcoin"
 * @param slug
 *   e.g. "bitcoin"
 */
case class CryptoCurrency(symbol: CryptoCurrency.Symbol, name: CryptoCurrency.Name, slug: CryptoCurrency.Slug)

object CryptoCurrency {
  case class Symbol(value: String) extends AnyVal
  case class Name(value: String)   extends AnyVal
  case class Slug(value: String)   extends AnyVal
}
