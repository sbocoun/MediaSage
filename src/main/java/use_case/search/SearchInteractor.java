package use_case.search;

import data_access.movies.MovieDBDataAccessException;
import entity.Movie;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

/**
 * Interactor for the search by title use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final MovieDBDataAccessInterface movieDBDataAccess;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(MovieDBDataAccessInterface movieDBDataAccess, SearchOutputBoundary searchPresenter) {
        this.movieDBDataAccess = movieDBDataAccess;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchByCriteriaInputData inputData) {
        String movieDetails;

        // Check if search input is empty
        if (inputData.getKeywords().isEmpty() || inputData.getKeywords().get(0).trim().isEmpty()) {
            movieDetails = "Error: Please provide a valid movie title.";
            searchPresenter.prepareFailView(new SearchByCriteriaOutputData(movieDetails));
        }
        else {
            try {
                if ("movie".equalsIgnoreCase(inputData.getCategory())) {
                    movieDetails = searchMovie(inputData.getKeywords().get(0).trim());
                }
                else if ("tv show".equalsIgnoreCase(inputData.getCategory())) {
                    throw new UnsupportedOperationException("Search by TV show title is not currently supported.");
                }
                else {
                    throw new UnsupportedOperationException("This category is not currently supported.");
                }
            }
            catch (UnsupportedOperationException ex) {
                movieDetails = "Error: " + ex.getMessage();
            }
            catch (MovieDBDataAccessException ex) {
                movieDetails = "Error: Could not retrieve movie details.";
            }
            searchPresenter.prepareSuccessView(new SearchByCriteriaOutputData(movieDetails));
        }
    }

    // Helper method for searching movies
    private String searchMovie(String searchKeyword) throws MovieDBDataAccessException {
        final String result;
        final Movie matchingMovie = movieDBDataAccess.getMovie(searchKeyword);

        if (matchingMovie != null) {
            result = matchingMovie.toString();
        }
        else {
            result = "No results found for the keyword: " + searchKeyword;
        }

        return result;
    }
}
