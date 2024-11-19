package use_case.generate_recommendations;

import data_access.movies.MovieDBDataAccessException;
import entity.Movie;

/**
 * Interface for the TMDB. Includes methods for setting the API key and getting movie information.
 */
public interface MovieDBDataAccessInterface {

    /**
     * Get a Movie entity, containing the complete details of a movie from TMDB.
     *
     * @param movieName The name of the movie to be looked up.
     * @return a JSONObject containing the movie genres, cast, runtime, rating, and description.
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     */
    Movie getMovie(String movieName) throws MovieDBDataAccessException;

    /**
     * Sets the TMDB API key apikey.
     *
     * @param apikey the TMDB API key used for api calls
     */
    void setApiKey(String apikey);
}
