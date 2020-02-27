package controllers

import akka.http.scaladsl.server.Directives._
import session.SessionDirectives

class HomeController(sessionDirective:SessionDirectives) {
  def home = {
    sessionDirective.requiredSession{ session =>
      complete("home")
    }
  }
}
