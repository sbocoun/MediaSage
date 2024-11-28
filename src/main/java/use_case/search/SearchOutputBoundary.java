package use_case.search;

import data_access.movies.MovieDBDataAccessException;

/**
 * Output boundary for the search by criteria use case.
 */
public interface SearchOutputBoundary {

    /**
     * Update the UI with the results of the search.
     *
     * @param outputData the search results to be displayed in the UI.
     * @throws MovieDBDataAccessException if TMDB API is unsuccessfully called or no results are found.
     */
    void displaySearchResults(SearchByCriteriaOutputData outputData) throws MovieDBDataAccessException;
}
