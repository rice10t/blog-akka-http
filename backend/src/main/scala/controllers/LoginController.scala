package controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import io.circe.Decoder
import json.Json
import service.{LoginService, LoginServiceErrors}

class LoginController(loginService: LoginService) extends LoginControllerJson {
  def login = {
    entity(as[LoginRequest]) { json =>
      val result = loginService.login(json.username, json.password)
      onSuccess(result.value) { r =>
        r match {
          case Right(_)                                => complete("TODO: return the token")
          case Left(LoginServiceErrors.UserNotFound()) => complete(StatusCodes.BadRequest, "user not found")
        }
      }
    }
  }
}

trait LoginControllerJson extends Json {
  import io.circe.generic.semiauto._

  case class LoginRequest(username: String, password: String)

  implicit val loginRequestDecoder: Decoder[LoginRequest] = deriveDecoder
}
