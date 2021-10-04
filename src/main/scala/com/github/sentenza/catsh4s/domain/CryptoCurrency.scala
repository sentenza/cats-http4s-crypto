package com.github.sentenza.catsh4s.domain

/** Represents a Cryptocurrency
 *
 * @param name e.g. "Bitcoin"
 * @param symbol e.g. "BTC"
 * @param slug e.g. "bitcoin"
 */
case class CryptoCurrency(name: CryptoCurrency.Name, symbol: CryptoCurrency.Symbol, slug: CryptoCurrency.Slug)

object CryptoCurrency {
  case class Name(value: String) extends AnyVal
  case class Symbol(value: String) extends AnyVal
  case class Slug(value: String) extends AnyVal
}
