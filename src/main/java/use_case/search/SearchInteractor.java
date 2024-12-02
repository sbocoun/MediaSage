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
        String movieDetails;

        // Check if the list of keywords is empty or if the first keyword is empty or contains only whitespace
        if (inputData.getKeywords().isEmpty() || inputData.getKeywords().get(0).trim().isEmpty()) {
            movieDetails = "Error: Please provide a valid movie title.";
            outputBoundary.displaySearchResults(new SearchByCriteriaOutputData(movieDetails));
        }
        else {
            try {
                // Ensure category is set to Movie
                if ("movie".equalsIgnoreCase(inputData.getCategory())) {
                    final String searchKeyword = inputData.getKeywords().get(0).trim();

                    // Call DAO to search for the movie if a valid keyword is provided
                    if (!searchKeyword.isEmpty()) {
                        matchingMovie = movieDBDataAccess.getMovie(searchKeyword);
                    }
                }

                // Convert result to output data
                if (matchingMovie != null) {
                    movieDetails = matchingMovie.toString();
                }
                else {
                    movieDetails = "No results found for the keyword: " + inputData.getKeywords().get(0);
                }

            }
            catch (MovieDBDataAccessException exception) {
                movieDetails = "Error: Could not retrieve movie details.";
            }

            outputBoundary.displaySearchResults(new SearchByCriteriaOutputData(movieDetails));
        }
    }
}
