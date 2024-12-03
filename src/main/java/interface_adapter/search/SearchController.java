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
     * Simplified "search by title" method.
     * Executes the search use case based on a keyword (movie name).
     *
     * @param keyword the movie name to search for.
     * @param category the search category.
     */
    public void execute(String category, String keyword) {
        final SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(
                category,
                // Only the keyword
                Collections.singletonList(keyword),
                // No genres filtering
                Collections.emptyList(),
                // No cast filtering
                Collections.emptyList()
        );

        inputBoundary.execute(inputData);
    }
}
