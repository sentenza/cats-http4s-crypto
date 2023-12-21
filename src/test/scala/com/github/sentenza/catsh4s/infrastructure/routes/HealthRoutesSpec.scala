package com.github.sentenza.catsh4s.infrastructure.routes

import cats.effect.unsafe.implicits.global
import cats.effect.IO
import org.http4s.implicits._
import org.http4s.Method
import org.http4s.Request
import org.http4s.Response
import org.http4s.Status.Ok
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec

class HealthRoutesSpec extends wordspec.AnyWordSpec with Matchers {

  class TestCase {
    def execHealthRoute(request: Request[IO]): IO[Response[IO]] =
      HealthRoutes.healthRoutes[IO].orNotFound(request)
  }

  "HealthRoutes" when {
    "the liveness probe is responsive" should {
      "return 200 OK for /h/healthz" in new TestCase {
        val request: Request[IO]   = Request[IO](Method.GET, uri"/h/healthz")
        val response: Response[IO] = execHealthRoute(request).unsafeRunSync()
        response.status shouldBe Ok
      }
    }
    "the readiness probe is responsive" should {
      "return 200 OK for /h/ready" in new TestCase {
        val request: Request[IO]   = Request[IO](Method.GET, uri"/h/ready")
        val response: Response[IO] = execHealthRoute(request).unsafeRunSync()
        response.status shouldBe Ok
        response.as[String].unsafeRunSync() shouldBe ""
      }
    }
  }
}
