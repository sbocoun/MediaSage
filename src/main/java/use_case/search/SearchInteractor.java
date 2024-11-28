package use_case.search;

import data_access.movies.MovieDBDataAccessException;
import entity.Movie;
import use_case.generate_recommendations.MovieDBDataAccessInterface;


/**
 * Interactor for the search by criteria use case.
 */
public class SearchInteractor implements SearchInputBoundary {

    private final MovieDBDataAccessInterface movieDBDataAccess;

    public SearchInteractor(MovieDBDataAccessInterface movieDBDataAccess) {
        this.movieDBDataAccess = movieDBDataAccess;
    }

    /**
     * Execute the search by criteria use case.
     *
     * @param inputData the input data containing search criteria.
     * @return SearchByCriteriaOutputData containing the search result.
     * @throws MovieDBDataAccessException if an error occurs during the search.
     */
    @Override
    public SearchByCriteriaOutputData execute(SearchByCriteriaInputData inputData) throws MovieDBDataAccessException {
        Movie matchingMovie = null;

        // Ensure category is set to Movie
        if ("movie".equalsIgnoreCase(inputData.getCategory())) {
            // Retrieve keyword
            String searchKeyword = null;
            if (!inputData.getKeywords().isEmpty()) {
                searchKeyword = inputData.getKeywords().get(0);
            }
            // If a keyword is provided, call DAO to search for the movie
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                matchingMovie = movieDBDataAccess.getMovie(searchKeyword);
            }
        }

        // Convert result to output data
        final String movieDetails;
        if (matchingMovie != null) {
            movieDetails = matchingMovie.toString();
        }
        else {
            movieDetails = "";
        }
        return new SearchByCriteriaOutputData(movieDetails);
    }
}