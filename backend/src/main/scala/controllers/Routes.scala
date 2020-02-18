package controllers

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import quill.{MyContext, TaskDao}

object Routes {
  private def postRoute(pathString: String) = path(pathString) & post

  def routes(ctx: MyContext): Route = {
    val taskController = new TaskController(new TaskDao(ctx))

    concat(
      postRoute("get-task") { taskController.getTask },
      postRoute("get-tasks") { taskController.getTasks },
      postRoute("create-task") { taskController.createTask },
      postRoute("delete-task") { taskController.deleteTask },
      postRoute("done-task") { taskController.doneTask },
      postRoute("undone-task") { taskController.undoneTask }
    )
  }
}
