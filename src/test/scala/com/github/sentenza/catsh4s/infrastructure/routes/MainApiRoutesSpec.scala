package com.github.sentenza.catsh4s.infrastructure.routes

import cats.effect.unsafe.implicits.global
import cats.effect.IO
import com.github.sentenza.catsh4s.infrastructure.routes.MainApiRoutes
import com.github.sentenza.catsh4s.infrastructure.service.PingService
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Decoder
import org.http4s.circe.jsonOf
import org.http4s.implicits._
import org.http4s.EntityDecoder
import org.http4s.Method
import org.http4s.Request
import org.http4s.Response
import org.http4s.Status.Ok
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec

class MainApiRoutesSpec extends wordspec.AnyWordSpec with Matchers {

  class TestCase {
    case class PongResponse(message: String)
    // JSON decoders
    implicit val pongDecoder: Decoder[PongResponse]                 = deriveDecoder[PongResponse]
    implicit def pongEntityDecoder: EntityDecoder[IO, PongResponse] = jsonOf[IO, PongResponse]
    // Services and Routes materializer
    val pingService: PingService[IO] = PingService.impl[IO]
    def callRoute(request: Request[IO]): IO[Response[IO]] =
      MainApiRoutes.essentialRoutes[IO](pingService).orNotFound(request)
  }

  "Main API routes" when {
    "requesting /api/ping/me" should {
      "return 200 OK and pong(me)" in new TestCase {
        val request: Request[IO]   = Request[IO](Method.GET, uri"/api/ping/me")
        val response: Response[IO] = callRoute(request).unsafeRunSync()
        response.status shouldBe Ok
        response.as[PongResponse].unsafeRunSync() shouldBe PongResponse("PONG > me")
      }
    }
  }
}
