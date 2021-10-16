package com.github.sentenza.catsh4s.infrastructure.repository

import _root_.doobie.Transactor
import cats.effect.Async
import com.github.sentenza.catsh4s.config.DatabaseConfig

import scala.concurrent.ExecutionContext

package object doobie {

  lazy val testEC: ExecutionContext = ExecutionContext.Implicits.global

  def getTransactor[F[_]: Async](cfg: DatabaseConfig): Transactor[F] =
    Transactor.fromDriverManager[F](
      cfg.driver,  // driver classname
      cfg.url,     // connect URL (driver-specific)
      cfg.user,    // user
      cfg.password // password
    )
}
