import slick.jdbc.GetResult

import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object Actors {
  import slick.jdbc.MySQLProfile.api._
  import PrivateExecutor._

  val keanuReeves = Actor(1, "Keanu Reeves")

  def InsertActors() = {
    val queryDescription = SlickTables.ActorTable ++= Seq(
      Actor(1, "Keanu Reeves"),
      Actor(2, "Laurence Fishburne"),
      Actor(3, "Carrie-Anne Moss"),
      Actor(4, "Hugo Weaving"),
      Actor(5, "Joe Pantoliano"),
      Actor(6, "Lana Wachowski"),
      Actor(7, "Lilly Wachowski")
    )
    val futureId: Future[Option[Int]] = connection.db.run(queryDescription)

    futureId.onComplete {
      case Success(id) => println(s"Inserted actors with id $id")
      case Failure(ex) => println(s"Error inserting actors because:  $ex")
    }
    Thread.sleep(1000)
  }
}
