package controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

class AppRoute(
    articleController: ArticleController,
    homeController: HomeController,
    loginController: LoginController,
    taskController: TaskController,
    userController: UserController
) {
  private def postRoute(pathString: String) = path(pathString) & post

  private def exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: Throwable =>
        extractUri { uri =>
          println(s"Request to $uri could not be handled normally")
          println(e.printStackTrace())
          complete(StatusCodes.InternalServerError, "internal server error")
        }
    }

  def routes(): Route = {
    handleExceptions(exceptionHandler) {
      cors() {
        concat(
          // user
          postRoute("create-user") { userController.createUser },
          // login
          postRoute("login") { loginController.login },
          postRoute("login_oauth") { loginController.loginOauth },
          // home
          postRoute("home") { homeController.home },
          // article
          postRoute("create-article") { articleController.createArticle }
        )
      }
    }
  }
}
