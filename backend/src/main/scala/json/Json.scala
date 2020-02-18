package json

import io.circe.generic.semiauto._
import model.Task

case class GetTaskRequest(id: Long)
case class GetTaskResponse(task: Task)
case class CreateTaskRequest(title: String)
case class DeleteTaskRequest(id: Long)
case class DoneTaskRequest(id: Long)
case class UndoneTaskRequest(id: Long)

trait Json {
  implicit val taskDecoder = deriveDecoder[Task]
  implicit val taskEncoder = deriveEncoder[Task]

  implicit val getTaskRequestDecoder = deriveDecoder[GetTaskRequest]
  implicit val createTaskRequestDecoder = deriveDecoder[CreateTaskRequest]
  implicit val deleteTaskRequestDecoder = deriveDecoder[DeleteTaskRequest]
  implicit val doneTaskRequest = deriveDecoder[DoneTaskRequest]
  implicit val undoneTaskRequest = deriveDecoder[UndoneTaskRequest]
}
