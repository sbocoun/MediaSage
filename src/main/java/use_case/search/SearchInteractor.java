package use_case.search;

import data_access.movies.MovieDBDataAccessException;
import entity.Movie;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

/**
 * Interactor for the search by criteria use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final MovieDBDataAccessInterface movieDBDataAccess;
    private final SearchOutputBoundary outputBoundary;

    public SearchInteractor(MovieDBDataAccessInterface movieDBDataAccess, SearchOutputBoundary outputBoundary) {
        this.movieDBDataAccess = movieDBDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SearchByCriteriaInputData inputData) {
        Movie matchingMovie = null;
        String movieDetails = "";

        try {
            // Ensure category is set to Movie
            if ("movie".equalsIgnoreCase(inputData.getCategory())) {
                String searchKeyword = null;
                if (!inputData.getKeywords().isEmpty()) {
                    searchKeyword = inputData.getKeywords().get(0);
                }

                if (searchKeyword != null && !searchKeyword.isEmpty()) {
                    // Call DAO to search for the movie
                    matchingMovie = movieDBDataAccess.getMovie(searchKeyword);
                }
            }

            // Convert result to output data
            if (matchingMovie != null) {
                movieDetails = matchingMovie.toString();
            }

        }
        catch (MovieDBDataAccessException ex) {
            movieDetails = "Error: Could not retrieve movie details.";
        }

        outputBoundary.displaySearchResults(new SearchByCriteriaOutputData(movieDetails));
    }
}
