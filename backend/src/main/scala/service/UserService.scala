package service

import cats.data.EitherT
import cats.implicits._
import dao.UserDao
import UserServiceErrors._

import scala.concurrent.{ExecutionContext, Future}

class UserService(userDao: UserDao)(implicit ec: ExecutionContext) {
  def createUser(username: String, password: String): EitherT[Future, CreateUserError, Long] = {
    def checkIfUserExists(username: String): EitherT[Future, CreateUserError, Unit] = {
      val result = userDao
        .find(username)
        .map {
          case Some(_) => Left(UserAlreadyExistsError())
          case None    => Right()
        }
      EitherT(result)
    }

    def createNewUser(username: String, password: String): EitherT[Future, CreateUserError, Long] = {
      val result = userDao.create(username, password)
      EitherT.right(result)
    }

    for {
      _ <- checkIfUserExists(username)
      userId <- createNewUser(username, password)
    } yield userId
  }
}

object UserServiceErrors {
  sealed trait CreateUserError
  case class UserAlreadyExistsError() extends CreateUserError
}
