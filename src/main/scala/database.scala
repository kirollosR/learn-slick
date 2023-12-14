import slick.jdbc.GetResult

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import slick.jdbc.MySQLProfile.api._
import PrivateExecutor._
import SlickTables.MovieTable

import scala.concurrent.duration.Duration

object database {
  def ReadPlainQuery[T](tableName: String, query: String)(implicit getResult: GetResult[T]): Unit = {
    // Construct the SQL query
    val sqlQuery = sql"""#$query""".as[T]

    // Execute the query
    val futureResults: Future[Seq[T]] = connection.db.run(sqlQuery)

    // Handle the result asynchronously
    futureResults.onComplete {
      case Success(results) => println(s"Read $tableName:\n${results.mkString("\n")}")
      case Failure(ex) => println(s"Error reading $tableName because: $ex")
    }

    // Sleep and shutdown executor (as in your original code)
    Thread.sleep(1000)
    PrivateExecutor.executor.shutdown()
  }
}
