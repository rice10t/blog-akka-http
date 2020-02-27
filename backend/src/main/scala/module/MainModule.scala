package module

import com.softwaremill.macwire._
import com.typesafe.config.ConfigFactory
import controllers._
import dao._
import service._
import session._

trait MainModule {
  // TODO don't use global ExecutionContext
  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
  lazy val config = ConfigFactory.load()
  lazy val sessionManager = wire[AppSessionManager]
  lazy val sessionDirectives = wire[SessionDirectives]

  // daos
  lazy val quillContext = wire[MyContext]
  lazy val githubUserDao = wire[GithubUserDao]
  lazy val taskDao = wire[TaskDao]
  lazy val UserDao = wire[UserDao]

  // services
  lazy val githubService = wire[GithubService]
  lazy val loginService = wire[LoginService]
  lazy val userService = wire[UserService]

  // controllers
  lazy val articleController = wire[ArticleController]
  lazy val homeController = wire[HomeController]
  lazy val loginController = wire[LoginController]
  lazy val taskController = wire[TaskController]
  lazy val userController = wire[UserController]
}
