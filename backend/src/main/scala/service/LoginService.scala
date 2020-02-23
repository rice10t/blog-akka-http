package service

import cats.implicits._
import cats.data.EitherT
import dao.UserDao
import model.LoginUser

import scala.concurrent.{ExecutionContext, Future}

class LoginService(userDao: UserDao)(implicit private val ec: ExecutionContext) {
  def login(username: String, password: String): EitherT[Future, LoginServiceErrors.LoginError, LoginUser] = {
    EitherT.fromOptionF(userDao.checkLogin(username, password), LoginServiceErrors.UserNotFound())
  }
}

object LoginServiceErrors {
  sealed trait LoginError
  case class UserNotFound() extends LoginServiceErrors.LoginError
}
