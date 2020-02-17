package controllers

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import json._
import quill.TaskDao

class TaskController(taskDao: TaskDao) extends json.Json {
  def getTask =
    entity(as[GetTaskRequest]) { json =>
      val task = taskDao.find(json.id)
      complete(task)
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
