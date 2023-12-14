import slick.jdbc.GetResult

import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}



object Movies {
  import slick.jdbc.MySQLProfile.api._
  import PrivateExecutor._

  val shawshankRedemption = Movie(16, "The Shawshank Redemption", LocalDate.of(1994, 9, 23), 162)
  val phantom = Movie(17, "The Phantom of the Opera", LocalDate.of(2004, 12, 22), 143)

  def InsertMovie() = {
    val queryDescription = SlickTables.MovieTable += shawshankRedemption
    val futureId: Future[Int] = connection.db.run(queryDescription)

    futureId.onComplete {
      case Success(id) => println(s"Inserted movie with id $id")
      case Failure(ex) => println(s"Error inserting movie because:  $ex")
    }
    Thread.sleep(1000)
  }

  def InsertMovieWithReturn() = {
    val insertQueryWithReturn = SlickTables.MovieTable.returning(SlickTables.MovieTable.map(_.movieId)) += shawshankRedemption
    val futureId: Future[Int] = connection.db.run(insertQueryWithReturn)

    futureId.onComplete {
      case Success(id) => println(s"Inserted movie with id $id")
      case Failure(ex) => println(s"Error inserting movie because:  $ex")
    }
    Thread.sleep(1000)
  }

  def ReadAllMovies() = {
    val queryDescription = SlickTables.MovieTable.result
    val futureMovies: Future[Seq[Movie]] = connection.db.run(queryDescription)

    futureMovies.onComplete {
      case Success(movies) => println(s"Read all movies: ${movies.mkString("\n")}")
      case Failure(ex) => println(s"Error reading movies because:  $ex")
    }
    Thread.sleep(1000)
  }

  def ReadSomeMovies(search: String) = {
    val queryDescription = SlickTables.MovieTable.filter(_.title.like("%" + search + "%")).result
    val futureMovies: Future[Seq[Movie]] = connection.db.run(queryDescription)

    futureMovies.onComplete {
      case Success(movies) => println(s"Read \"${search}\" movies:\n${movies.mkString("\n")}")
      case Failure(ex) => println(s"Error reading movies because:  $ex")
    }
    Thread.sleep(1000)
  }

//  def ReadPlainQuery() = {
//    //[id, title, release_date, length_in_min]
//    implicit val getMovieResult: GetResult[Movie] =
//      GetResult(r => Movie(
//        r.nextInt,
//        r.nextString,
//        r.nextDate.toLocalDate,
//        r.nextInt)
//    )
//    val query = sql"""select * from movie where title like '%shaw%' """.as[Movie]
//    val futureMovies: Future[Seq[Movie]] = connection.db.run(query)
//
//    futureMovies.onComplete {
//      case Success(movies) => println(s"Read movies:\n${movies.mkString("\n")}")
//      case Failure(ex) => println(s"Error reading movies because:  $ex")
//    }
//    Thread.sleep(1000)
//    PrivateExecutor.executor.shutdown()
//
//  }

//  def ReadPlainQuery[T](tableName: String, query: String)(implicit getResult: GetResult[T]): Unit = {
//    // Construct the SQL query
//    val sqlQuery = sql"""#$query""".as[T]
//
//    // Execute the query
//    val futureResults: Future[Seq[T]] = connection.db.run(sqlQuery)
//
//    // Handle the result asynchronously
//    futureResults.onComplete {
//      case Success(results) => println(s"Read $tableName:\n${results.mkString("\n")}")
//      case Failure(ex) => println(s"Error reading $tableName because: $ex")
//    }
//
//    // Sleep and shutdown executor (as in your original code)
//    Thread.sleep(1000)
//    PrivateExecutor.executor.shutdown()
//  }

  def UpdateMovie() = {
    val queryDescriptor = SlickTables.MovieTable.filter(_.movieId === 16).update(shawshankRedemption.copy(lengthInMin = 150))
    val futureId: Future[Int] = connection.db.run(queryDescriptor)

    futureId.onComplete {
      case Success(id) => println(s"Updated movie with id $id")
      case Failure(ex) => println(s"Error updating movie because:  $ex")
    }
    Thread.sleep(1000)
  }

  def DeleteMovie() = {
    val queryDescriptor = SlickTables.MovieTable.filter(_.movieId === 1).delete
    val futureId: Future[Int] = connection.db.run(queryDescriptor)

    futureId.onComplete {
      case Success(id) => println(s"Deleted movie with id $id")
      case Failure(ex) => println(s"Error deleting movie because:  $ex")
    }
    Thread.sleep(1000)
  }

  def FindAllActorsByMovie(movieId: Int): Future[Seq[Actor]] = {
    val joinQuery = SlickTables.MovieActorTable
      .filter(_.movieId === movieId)
      .join(SlickTables.ActorTable)
      .on(_.actorId === _.actorId) // select * from movie_actor as ma join actor as a on m.actor_id = a.actor_id where ma.movie_id = 16
      .map(_._2)
    connection.db.run(joinQuery.result)

//    Thread.sleep(1000)
  }

}
