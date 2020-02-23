package dao

import model.LoginUser

import scala.concurrent.{ExecutionContext, Future, blocking}

class UserDao(ctx: MyContext)(implicit private val ec: ExecutionContext) {
  import ctx._

  def create(username: String, password: String): Future[Long] =
    Future {
      blocking {
        run {
          quote {
            query[LoginUser]
              .insert(lift(LoginUser(-1, username, password)))
              .returning(_.id)
          }
        }
      }
    }

  def find(username: String): Future[Option[LoginUser]] =
    Future {
      blocking {
        run {
          quote {
            query[LoginUser]
              .filter(loginUser => loginUser.username == lift(username))
          }
        }.headOption
      }
    }

  def checkLogin(username: String, password: String): Future[Option[LoginUser]] = {
    Future {
      blocking {
        run {
          quote {
            query[LoginUser]
              .filter(loginUser => loginUser.username == lift(username) && loginUser.password == lift(password))
          }
        }.headOption
      }
    }
  }
}
