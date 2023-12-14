-- Create the Movie table
CREATE TABLE movie (
                       movie_id INT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(255),
                       releaseDate DATE,
                       lengthInMin INT
);

-- Create the Actor table
CREATE TABLE actor (
                       actor_id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255)
);

-- Create the MovieActor table with composite primary key and foreign keys
CREATE TABLE movie_actor (
                             actor_id INT,
                             movie_id INT,
                             PRIMARY KEY (actor_id, movie_id),
                             FOREIGN KEY (actor_id) REFERENCES actor(actor_id),
                             FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
) ENGINE=InnoDB; -- Make sure to specify the storage engine (InnoDB in this case)
