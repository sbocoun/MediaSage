package use_case.generate_recommendations;

import org.json.JSONObject;

import data_access.MovieDBDataAccessException;

/**
 * Interface for the TMDB. Includes methods for setting the API key and getting movie information.
 */
public interface MovieDBDataAccessInterface {

    /**
     * Get the complete details of a movie from the TMDB.
     * @param movieName The name of the movie to be looked up.
     * @return a JSONObject containing the movie genres, cast, runtime, rating, and description.
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     */
    JSONObject getCompleteMovieData(String movieName) throws MovieDBDataAccessException;

    /**
     * Sets the TMDB API key apikey.
     *
     * @param apikey the TMDB API key used for api calls
     */
    void setApiKey(String apikey);

}
