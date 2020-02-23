package controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}

object Routes {
  private def postRoute(pathString: String) = path(pathString) & post

  private def exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case _: Throwable =>
        extractUri { uri =>
          println(s"Request to $uri could not be handled normally")
          complete(StatusCodes.InternalServerError, "internal server error")
        }
    }

  def routes(
      taskController: TaskController,
      userController: UserController,
      loginController: LoginController
  ): Route = {
    handleExceptions(exceptionHandler) {
      concat(
        postRoute("get-task") { taskController.getTask },
        postRoute("get-tasks") { taskController.getTasks },
        postRoute("create-task") { taskController.createTask },
        postRoute("delete-task") { taskController.deleteTask },
        postRoute("done-task") { taskController.doneTask },
        postRoute("undone-task") { taskController.undoneTask },
        // user
        postRoute("create-user") { userController.createUser },
        // login
        postRoute("login") { loginController.login }
      )
    }
  }
}
