package com.github.sentenza.catsh4s.db

import cats.effect.{Async, Resource, Sync}
import cats.implicits.toFunctorOps
import com.github.sentenza.catsh4s.config.DatabaseConfig
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway

import scala.concurrent.ExecutionContext

object Database {
  def transactor[F[_]: Async](
      config: DatabaseConfig,
      connectEC: ExecutionContext
  ): Resource[F, HikariTransactor[F]] = {
    HikariTransactor.newHikariTransactor[F](
      config.driver,
      config.url,
      config.user,
      config.password,
      connectEC
    )
  }

  /** Runs the flyway migrations against the target database
    */
  def initializeDb[F[_]](cfg: DatabaseConfig)(implicit S: Sync[F]): F[Unit] =
    S.delay {
      val fw: Flyway =
        Flyway
          .configure()
          .dataSource(cfg.url, cfg.user, cfg.password)
          .load()
      fw.migrate()
    }.as(())
}
