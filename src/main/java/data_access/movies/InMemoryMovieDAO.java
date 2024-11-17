package data_access.movies;

import java.util.Map;

import entity.Movie;
import kotlin.NotImplementedError;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

/**
 * An in-memory implementation of movie database access.
 */
public class InMemoryMovieDAO implements MovieDBDataAccessInterface {
    private Map<String, Movie> movies;

    @Override
    public void setApiKey(String apikey) {
        throw new NotImplementedError("Not applicable here.");
    }

    @Override
    public Movie getMovie(String movieName) throws MovieDBDataAccessException {
        if (!movies.containsKey(movieName)) {
            throw new MovieDBDataAccessException(movieName + " not found.");
        }
        return movies.get(movieName);
    }

    /**
     * Add a movie to the in-memory database.
     * @param movie a movie
     */
    public void addMovie(Movie movie) {
        movies.put(movie.getName(), movie);
    }
}
