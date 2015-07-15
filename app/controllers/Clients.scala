package controllers

import play.api.mvc._

object Clients extends Controller {

    def hello = Action {
        Ok("World")
    }

    def show(id: Long) = Action {
        Ok("hoge")
    }
}
