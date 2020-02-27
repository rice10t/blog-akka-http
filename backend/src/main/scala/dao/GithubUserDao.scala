package dao

import model.{GithubUser, LoginUser}

import scala.concurrent.{ExecutionContext, Future, blocking}

class GithubUserDao(ctx: MyContext)(implicit private val ec: ExecutionContext) {
  import ctx._

  def find(id: Long): Future[Option[GithubUser]] =
    Future {
      blocking {
        run {
          quote {
            query[GithubUser]
              .filter(user => user.id == lift(id))
          }
        }.headOption
      }
    }

  def createOrUpdate(id: Long, accessToken: String): Future[Long] = {
    Future {
      blocking {
        run {
          quote {
            query[GithubUser]
              .insert(lift(GithubUser(id, accessToken)))
              .onConflictUpdate(_.id)((t, e) => t.accessToken -> lift(accessToken))
          }
        }
        id
      }
    }
  }
}
