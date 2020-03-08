import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.server.{HttpApp, Route}
import module.MainModule

import scala.util.Try

object WebServer extends HttpApp {
  val modules: MainModule = new MainModule {}

  override def routes: Route = {
    modules.route.routes()
  }

  override def postServerShutdown(attempt: Try[Done], system: ActorSystem): Unit = {
    modules.quillContext.close()
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    WebServer.startServer("localhost", 8080)
  }
}
