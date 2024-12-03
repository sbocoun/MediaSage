package use_case.search;

import entity.Movie;
import entity.Television;

/**
 * Interface for search by title functionality.
 */
public interface SearchDataAccessInterface {
    /**
     * Fetches movie details based on the provided query.
     *
     * @param query The title of the movie to search for.
     * @return A Movie object containing the details of the movie.
     * @throws IllegalArgumentException if the query is null or empty.
     */
    Movie getMovieByQuery(String query);

    /**
     * Fetches a TV show by title.
     * @param query The title of the TV show to search for.
     * @return A Television object containing the details of the TV show.
     * @throws IllegalArgumentException if the query is null or empty.
     */

    Television getTelevisionByQuery(String query);
}
