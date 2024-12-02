package interface_adapter.search;

import java.util.Collections;

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
     */
    public void execute(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            outputBoundary.displaySearchResults(new SearchByCriteriaOutputData(keyword));
            return;
        }

        final SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(
                // Category defaults to Movie
                "movie",
                // Only the keyword
                Collections.singletonList(keyword.trim()),
                // No genres filtering
                Collections.emptyList(),
                // No cast filtering
                Collections.emptyList()
        );

        inputBoundary.execute(inputData);
    }
}
