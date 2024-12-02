package interface_adapter.search;

import java.util.Collections;

import data_access.movies.MovieDBDataAccessException;
import use_case.search.SearchByCriteriaInputData;
import use_case.search.SearchByCriteriaOutputData;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchOutputBoundary;

/**
 * Controller for handling the search by criteria use case.
 */
public class SearchController {
    private final SearchInputBoundary inputBoundary;
    private final SearchOutputBoundary outputBoundary;

    public SearchController(SearchInputBoundary inputBoundary, SearchOutputBoundary outputBoundary) {
        this.inputBoundary = inputBoundary;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Simplified "search by name" method.
     * Executes the search use case based on a keyword (movie name).
     *
     * @param keyword the movie name to search for.
     * @throws MovieDBDataAccessException if TMDB API is unsuccessfully called or no results are found.
     */
    public void execute(String keyword) throws MovieDBDataAccessException {
        if (keyword == null || keyword.trim().isEmpty()) {
            outputBoundary.displaySearchResults(new SearchByCriteriaOutputData(keyword));
            return;
        }

        final SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(
                "movie", // Category defaults to Movie
                Collections.singletonList(keyword.trim()), // Only the keyword
                Collections.emptyList(), // No genres filtering
                Collections.emptyList()  // No cast filtering
        );

        try {
            final SearchByCriteriaOutputData outputData = inputBoundary.execute(inputData);
            // Pass results to Output Boundary
            outputBoundary.displaySearchResults(outputData);
        }
        catch (MovieDBDataAccessException e) {
            // Display empty result if error occurs
            outputBoundary.displaySearchResults(new SearchByCriteriaOutputData(keyword));
        }
    }
}