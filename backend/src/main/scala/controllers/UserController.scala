package controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import io.circe.Decoder
import json.Json
import service.{UserService, UserServiceErrors}

import scala.concurrent.ExecutionContext

class UserController(userService: UserService)(implicit ec: ExecutionContext) extends UserControllerJson {
  def createUser =
    entity(as[CreateUserRequest]) { json =>
      val result = userService.createUser(json.username, json.password)
      onSuccess(result.value) {
        case Right(_) => complete("user registration succeeded")
        case Left(UserServiceErrors.UserAlreadyExistsError()) => complete(StatusCodes.BadRequest, "user already exists")
      }
    }
}

trait UserControllerJson extends Json {
  import io.circe.generic.semiauto._

  case class CreateUserRequest(username: String, password: String)

  implicit val createUserRequestDecoder: Decoder[CreateUserRequest] = deriveDecoder
}
