package module

import com.softwaremill.macwire._
import controllers._
import dao._
import service._

trait MainModule {
  // TODO
  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  // daos
  lazy val quillContext = wire[MyContext]
  lazy val taskDao = wire[TaskDao]
  lazy val UserDao = wire[UserDao]

  // services
  lazy val loginService = wire[LoginService]
  lazy val userService = wire[UserService]

  // controllers
  lazy val loginController = wire[LoginController]
  lazy val taskController = wire[TaskController]
  lazy val userController = wire[UserController]
}
