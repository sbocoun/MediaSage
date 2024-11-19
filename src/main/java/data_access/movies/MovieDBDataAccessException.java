package data_access.movies;

/**
 * Exception thrown when there is an error with accessing data from the TMDB API.
 */
public class MovieDBDataAccessException extends Exception {
    public MovieDBDataAccessException(String string) {
        super(string);
    }
}
