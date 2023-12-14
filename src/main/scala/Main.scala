import Movies._
import Actors._
import database._
import slick.jdbc.GetResult
import slick.lifted.TableQuery

import scala.concurrent.Future
import scala.util.{Failure, Success}


object Main {

//  def MultipleQuery() ={
//    import slick.jdbc.MySQLProfile.api._
//    import slick.dbio.DBIO
//
//    val insertMovie = SlickTables.MovieTable += phantom
//    val insertActor = SlickTables.ActorTable += keanuReeves
//    val finalQuery = DBIO.seq(insertMovie, insertActor)
//
//    connection.db.run(finalQuery.transactionally)
//    Thread.sleep(1000)
//  }

  def main(args: Array[String]): Unit = {
    import SlickTables._
    import PrivateExecutor._
//    InsertMovie()
//    ReadAllMovies()
//    ReadSomeMovies("Matrix")
//    UpdateMovie()
//    ReadAllMovies()
//    DeleteMovie()
//    ReadPlainQuery()
//    InsertActors()
//    MultipleQuery()

//    ReadPlainQuery[Actor]("actor", "insert into actor(name) values ('test')")
//    ReadPlainQuery[Actor]("actor", "select * from actor")

//    FindAllActorsByMovie(1).onComplete{
//      case Success(actors) => println(s"Read actors:\n${actors.mkString("\n")}")
//      case Failure(exception) => println(s"Error reading actors because:  $exception")
//    }
//    Thread.sleep(1000)


  }
}
