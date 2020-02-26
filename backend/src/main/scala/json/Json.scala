package json

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import model.{LoginUser, Task}

trait Json {
  implicit val taskDecoder: Decoder[Task] = deriveDecoder
  implicit val taskEncoder: Encoder[Task] = deriveEncoder
  implicit val userDecoder: Decoder[LoginUser] = deriveDecoder
  implicit val userEncoder: Encoder[LoginUser] = deriveEncoder
}
