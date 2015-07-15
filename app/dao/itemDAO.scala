package dao

import scala.concurrent.Future

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import models.Item

class ItemDAO extends HasDatabaseConfig[JdbcProfile] {
  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  class Items(tag: Tag) extends Table[Item] (tag, "item") {
    def id      = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name    = column[String]("name", O.SqlType("varchar(128)"))
    def price   = column[Int]("price")

    def * = (id.?, name, price) <> (Item.tupled, Item.unapply)
  }
  val items = TableQuery[Items]

  def getAll: Future[Seq[Item]] = db.run(items.result)

  def get(id: Int): Future[Option[Item]] = db.run(items.filter(_.id === id).result.headOption)

  def insert(newItem: Item) = db.run(items += newItem)

  def setUp: Future[Unit] = {
    val setup = DBIO.seq(
      items.schema.create,

      items += Item(Some(1), "りんご", 100),
      items += Item(Some(2), "みかん", 200),

      items ++= Seq(
        Item(None, "パイナップル", 500),
        Item(None, "マンゴー", 300))
    )
    db.run(setup)

  }

}



