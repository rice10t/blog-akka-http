package controllers

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import json.Json
import dao.TaskDao
import io.circe.Decoder
import model.Task

class TaskController(taskDao: TaskDao) extends TaskControllerJson {
  def getTask = {
    val a = implicitly[Decoder[GetTaskRequest]]
    entity(as[GetTaskRequest]) { json =>
      val task = taskDao.find(json.id)
      complete(task)
    }
  }

  def getTasks = {
    val tasks = taskDao.findAll()
    complete(tasks)
  }

  def createTask =
    entity(as[CreateTaskRequest]) { json =>
      val id = taskDao.create(json.title)
      complete(s"created id: ${id}")
    }

  def deleteTask =
    entity(as[DeleteTaskRequest]) { json =>
      val effectedNum = taskDao.delete(json.id)
      complete(s"deleted ${effectedNum} column")
    }

  def doneTask =
    entity(as[DoneTaskRequest]) { json =>
      taskDao.done(json.id)
      complete("done")
    }

  def undoneTask =
    entity(as[UndoneTaskRequest]) { json =>
      taskDao.undone(json.id)
      complete("undone")
    }
}

trait TaskControllerJson extends Json {
  import io.circe.generic.semiauto._

  case class GetTaskRequest(id: Long)
  case class GetTaskResponse(task: Task)
  case class CreateTaskRequest(title: String)
  case class DeleteTaskRequest(id: Long)
  case class DoneTaskRequest(id: Long)
  case class UndoneTaskRequest(id: Long)

  implicit val getTaskRequestDecoder: Decoder[GetTaskRequest] = deriveDecoder
  implicit val createTaskRequestDecoder: Decoder[CreateTaskRequest] = deriveDecoder
  implicit val deleteTaskRequestDecoder: Decoder[DeleteTaskRequest] = deriveDecoder
  implicit val doneTaskRequestDecoder: Decoder[DoneTaskRequest] = deriveDecoder
  implicit val undoneTaskRequestDecoder: Decoder[UndoneTaskRequest] = deriveDecoder
}
