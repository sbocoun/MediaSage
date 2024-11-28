package use_case.search;

import entity.Movie;

/**
 * Interface for search by criteria functionality.
 */
public interface SearchDataAccessInterface {
    /**
     * Fetches movie details based on the provided query.
     *
     * @param query The search query (i.e. movie title).
     * @return A Movie object containing the details of the movie.
     * @throws IllegalArgumentException if the query is null or empty.
     */
    Movie getMovieByQuery(String query);
}
