package controllers

import akka.http.scaladsl.server.Directives._

class ArticleController {
  def createArticle = complete("article created!")
}
