import slick.jdbc.MySQLProfile.api._

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object PrivateExecutor {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

object connection {
  // Define your database connection details
  val db = Database.forConfig("database")
}
