package controllers

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import io.circe.Decoder
import json.Json
import service.{GithubService, GithubServiceErrors, LoginService, LoginServiceErrors}

import scala.concurrent.ExecutionContext

class LoginController(loginService: LoginService, githubService: GithubService)(implicit ec: ExecutionContext)
    extends LoginControllerJson {
  def login = {
    entity(as[LoginRequest]) { json =>
      val result = loginService.login(json.username, json.password)
      onSuccess(result.value) {
        case Right(_)                                => complete("TODO: return the token")
        case Left(LoginServiceErrors.UserNotFound()) => complete(StatusCodes.BadRequest, "user not found")
      }
    }
  }

  def loginOauth = {
    entity(as[LoginOauthRequest]) { json =>
      val userE = githubService.loginWithGithub(json.code)

      onSuccess(userE.value) {
        case Right(user) =>
          complete(user)
        // TODO error handling
        case Left(error) =>
          error match {
            case GithubServiceErrors.AccessTokenRequestError(err) =>
              println(err)
              complete("access token error")
            case GithubServiceErrors.UserDataRequestError(err) =>
              println(err)
              println(err.body)
              complete("user data error")
          }
      }
    }
  }
}

trait LoginControllerJson extends Json {
  import io.circe.generic.semiauto._

  case class LoginRequest(username: String, password: String)
  case class LoginOauthRequest(code: String)

  implicit val loginRequestDecoder: Decoder[LoginRequest] = deriveDecoder
  implicit val loginOauthRequestDecoder: Decoder[LoginOauthRequest] = deriveDecoder
}
