import slick.jdbc.GetResult

import java.time.LocalDate

case class Movie(
                  movieId: Int,
                  title: String,
                  releaseDate: LocalDate,
                  lengthInMin: Int
                )

case class Actor(
                  actorId: Int,
                  name: String

                )


case class MovieActor(
                         actorId: Int,
                         movieId: Int
                       )

object SlickTables{
  import slick.jdbc.MySQLProfile.api._

  class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
    def movieId = column[Int]("movie_id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def releaseDate = column[LocalDate]("release_date")
    def lengthInMin = column[Int]("length_in_min")

    // mapping function to transform from tuple to domain object and vice versa
    override def * = (movieId, title, releaseDate, lengthInMin) <> (Movie.tupled, Movie.unapply)
  }
  lazy val MovieTable = TableQuery[MovieTable]
  implicit val getActorResult: GetResult[Movie] = GetResult(r => Movie(r.nextInt, r.nextString, r.nextDate.toLocalDate, r.nextInt))

  class ActorTable(tag: Tag) extends Table[Actor](tag, "actor") {
    def actorId = column[Int]("actor_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")

    // mapping function to transform from tuple to domain object and vice versa
    override def * = (actorId, name) <> (Actor.tupled, Actor.unapply)
  }
  lazy val ActorTable = TableQuery[ActorTable]
//  implicit val getActorResult: GetResult[Actor] = GetResult(r => Actor(r.nextInt, r.nextString))

  class MovieActorTable(tag: Tag) extends Table[MovieActor](tag, "movie_actor") {
    def actorId = column[Int]("actor_id")
    def movieId = column[Int]("movie_id")

    // mapping function to transform from tuple to domain object and vice versa
    override def * = (actorId, movieId) <> (MovieActor.tupled, MovieActor.unapply)
  }
  lazy val MovieActorTable = TableQuery[MovieActorTable]
}