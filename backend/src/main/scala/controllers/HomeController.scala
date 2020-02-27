package controllers

import akka.http.scaladsl.server.Directives._

class HomeController() {
  def home = {
    complete("home")
  }
}
