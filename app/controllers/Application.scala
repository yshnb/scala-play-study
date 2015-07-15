package controllers

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import slick.driver.JdbcProfile

import models.Item
import dao.ItemDAO

object Application extends Controller {
 
    val dao = new ItemDAO

    def index = Action.async {
        dao.getAll.map(res => Ok(views.html.Application.index("test title")(res.toList)))
    }
    
    def get(id: Int) = Action.async {
        dao.get(id).map(res => Ok(views.html.Application.index("test get")(res.toList)))
    }
    
    def insert(name: String, price: Int) = Action.async {
        dao.insert(Item(None, name, price)).map(res => Ok(views.html.Application.index("inserted.")(List(Item(None, name, price)))))
    }

    def setup = Action.async {
        dao.setUp.map(res => Ok(views.html.Application.index("setup to done")(List.empty[Item])))
    }

    def download(name: String) = Action {
        Ok("Downlaod!!!")
    }

    def more = Action {
      Ok("<h1>More</h1>").as(HTML)
    }
    
    def sess = Action {
      Ok("Welcome")
        .withSession("connected" -> "mail@hoge.jp")
    }

//    def sess2 = Action {
//      request.session.get("connected").map{ user =>
//        Ok("Hello " + user)
//      }.getOrElse {
//        Unauthorized("Opps!")
//      }
//    }
}
