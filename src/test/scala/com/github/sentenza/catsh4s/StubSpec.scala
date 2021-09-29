package com.github.sentenza.catsh4s

import cats.implicits.catsSyntaxSemigroup
import org.scalatest._
import org.scalatest.matchers.should.Matchers

class StubSpec extends wordspec.AnyWordSpec with Matchers {
  "A Stub" should {
    "demonstrate test capabilities" in {
      "[" |+| "X" |+| ", " |+| "Y" |+| "]" shouldBe "[X, Y]"
    }
  }
}
