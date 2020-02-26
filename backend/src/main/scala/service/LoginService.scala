package service

import cats.data.EitherT
import cats.implicits._
import dao.UserDao
import model.LoginUser

import scala.concurrent.{ExecutionContext, Future}

class LoginService(userDao: UserDao)(implicit ec: ExecutionContext) {
  def login(username: String, password: String): EitherT[Future, LoginServiceErrors.LoginError, LoginUser] = {
    EitherT.fromOptionF(userDao.checkLogin(username, password), LoginServiceErrors.UserNotFound())
  }
}

object LoginServiceErrors {
  sealed trait LoginError
  case class UserNotFound() extends LoginServiceErrors.LoginError
}
