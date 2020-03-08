package controllers

import org.scalatest.WordSpec
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._
import module.TestModule

class RouteSpec extends WordSpec with ScalatestRouteTest {
  val module = new TestModule {}
  val testRoute = module.route.routes()

  "The service" should {
    "return 200" in {
      Post("/home") ~> testRoute ~> check {
        assert(status == StatusCodes.OK)
      }
    }
  }
}
