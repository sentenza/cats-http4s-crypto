package com.github.sentenza.catsh4s.domain

sealed trait ValidationError extends Product with Serializable

case object CurrencyNotFoundError extends ValidationError
