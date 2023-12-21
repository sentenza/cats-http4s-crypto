package com.github.sentenza.catsh4s.infrastructure.repository

import scala.concurrent.ExecutionContext

import _root_.doobie.Transactor
import cats.effect.Async
import com.github.sentenza.catsh4s.config.DatabaseConfig

package object doobie {

  lazy val testEC: ExecutionContext = ExecutionContext.Implicits.global

  def getTransactor[F[_]: Async](cfg: DatabaseConfig): Transactor[F] =
    Transactor.fromDriverManager[F](
      driver = cfg.driver,     // driver classname
      url = cfg.url,           // connect URL (driver-specific)
      user = cfg.user,         // user
      password = cfg.password, // password
      logHandler = None
    )
}
