import akka.http.scaladsl.server.{HttpApp, Route}
import controllers.Routes
import quill.MyContext


object WebServer extends HttpApp {
  val ctx = new MyContext
  override def routes: Route = Routes.routes(ctx)
}

object Main {
  def main(args: Array[String]): Unit = {
    WebServer.startServer("localhost", 8080)
  }
}
