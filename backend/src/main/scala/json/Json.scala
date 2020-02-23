package json

import io.circe.generic.semiauto._
import model.{Task, LoginUser}

trait Json {
  implicit val taskDecoder = deriveDecoder[Task]
  implicit val taskEncoder = deriveEncoder[Task]
  implicit val userDecoder = deriveDecoder[LoginUser]
  implicit val userEncoder = deriveEncoder[LoginUser]
}
