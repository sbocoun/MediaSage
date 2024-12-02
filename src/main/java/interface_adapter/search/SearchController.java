package interface_adapter.search;

import java.util.Collections;

import use_case.search.SearchByCriteriaInputData;
import use_case.search.SearchInputBoundary;

/**
 * Controller for handling the search by criteria use case.
 */
public class SearchController {
    private final SearchInputBoundary inputBoundary;

    public SearchController(SearchInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Simplified "search by name" method.
     * Executes the search use case based on a keyword (movie name).
     *
     * @param keyword the movie name to search for.
     */
    public void execute(String keyword) {
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
