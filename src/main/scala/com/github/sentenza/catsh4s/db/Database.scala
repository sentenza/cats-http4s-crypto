package com.github.sentenza.catsh4s.db

import scala.concurrent.ExecutionContext

import cats.effect.Async
import cats.effect.Resource
import cats.effect.Sync
import cats.implicits.toFunctorOps
import com.github.sentenza.catsh4s.config.DatabaseConfig
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway

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

  def init[F[_]](transactor: HikariTransactor[F])(implicit S: Sync[F]): F[Unit] =
    transactor.configure { dataSource =>
      val fw: Flyway = Flyway.configure().dataSource(dataSource).load()
      S.delay(fw.migrate()).as(())
    }

  /**
   * Runs the flyway migrations against the target database
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
