package service

import cats.data.EitherT
import cats.implicits._
import com.typesafe.config.Config
import dao.GithubUserDao
import io.circe.Decoder
import service.GithubServiceErrors._
import sttp.client.ResponseError
import sttp.client.asynchttpclient.WebSocketHandler

import scala.concurrent.{ExecutionContext, Future}

class GithubService(githubUserDao: GithubUserDao, config: Config)(implicit ec: ExecutionContext) extends GithubJson {
  def loginWithGithub(code: String): EitherT[Future, LoginWithGithubError, Long] = {
    import sttp.client._
    import sttp.client.asynchttpclient.future.AsyncHttpClientFutureBackend
    import sttp.client.circe._

    def accessTokenRequest(clientId: String, clientSecret: String)(
        implicit backend: SttpBackend[Future, Nothing, NothingT]
    ): EitherT[Future, LoginWithGithubError, AccessTokenResponse] = {
      val body = basicRequest
        .post(
          uri"https://github.com/login/oauth/access_token?client_id=${clientId}&client_secret=${clientSecret}&code=${code}"
        )
        .header("Accept", "application/json")
        .response(asJson[AccessTokenResponse])
        .send()
        .map { res =>
          res.body.left.map(err => AccessTokenRequestError(err))
        }

      EitherT(body)
    }

    def userDataRequest(accessToken: String)(
        implicit backend: SttpBackend[Future, Nothing, NothingT]
    ): EitherT[Future, LoginWithGithubError, UserDataResponse] = {
      val body = basicRequest
        .get(uri"https://api.github.com/user")
        .header("Authorization", "token " + accessToken)
        .response(asJson[UserDataResponse])
        .send()
        .map { res =>
          res.body.left.map(err => UserDataRequestError(err))
        }

      EitherT(body)
    }

    def createUserOrUpdateAccessToken(id: Long, accessToken: String): EitherT[Future, LoginWithGithubError, Long] = {
      EitherT.right(githubUserDao.createOrUpdate(id, accessToken))
    }

    implicit val backend: SttpBackend[Future, Nothing, WebSocketHandler] = AsyncHttpClientFutureBackend()

    val clientId = config.getString("app.github.oauth.clientId")
    val clientSecret = config.getString("app.github.oauth.clientSecret")

    for {
      accessTokenResponse <- accessTokenRequest(clientId, clientSecret)
      userDataResponse <- userDataRequest(accessTokenResponse.access_token)
      githubId <- createUserOrUpdateAccessToken(userDataResponse.id, accessTokenResponse.access_token)
    } yield githubId
  }
}

trait GithubJson {
  import io.circe.generic.semiauto._

  case class AccessTokenResponse(access_token: String, scope: String, token_type: String)
  case class UserDataResponse(id: Long)

  implicit val accessTokenResponse: Decoder[AccessTokenResponse] = deriveDecoder
  implicit val userDataResponse: Decoder[UserDataResponse] = deriveDecoder
}

object GithubServiceErrors {
  sealed trait LoginWithGithubError
  case class AccessTokenRequestError(error: ResponseError[io.circe.Error]) extends LoginWithGithubError
  case class UserDataRequestError(error: ResponseError[io.circe.Error]) extends LoginWithGithubError
}
