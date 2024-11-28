package use_case.search;

import data_access.movies.MovieDBDataAccessException;

/**
 * Input boundary for the search by criteria use case.
 */
public interface SearchInputBoundary {

    /**
     * Execute the search by criteria use case.
     *
     * @param inputData the search criteria input data.
     * @return the search results (output data).
     * @throws MovieDBDataAccessException if there is an error during the search process.
     */
    SearchByCriteriaOutputData execute(SearchByCriteriaInputData inputData) throws MovieDBDataAccessException;
}
