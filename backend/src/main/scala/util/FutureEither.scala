package util

import cats.data.EitherT

import scala.concurrent.Future

case class FutureEither[A, B](private val value: Future[Either[A, B]]) {
  private val underlying = EitherT[Future, A, B](value)
}
