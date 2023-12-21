package com.github.sentenza.catsh4s.infrastructure.repository.doobie

import cats.effect.unsafe.implicits.global
import cats.effect.IO
import com.github.sentenza.catsh4s.config.DatabaseConfig
import com.github.sentenza.catsh4s.db.Database
import org.scalatest.wordspec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.BeforeAndAfterEach

trait PostgresTestLayout extends wordspec.AnyWordSpec with BeforeAndAfterAll with BeforeAndAfterEach {
  val dbConfig: DatabaseConfig = DatabaseConfig(
    driver = "org.h2.Driver",
    url = "jdbc:h2:mem:cats_h4s_test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
    user = "db_test_user",
    password = "db_test_password",
    threadPoolSize = 16
  )

  override def beforeAll(): Unit = {
    Database.initializeDb[IO](dbConfig).unsafeRunSync()
  }
}
