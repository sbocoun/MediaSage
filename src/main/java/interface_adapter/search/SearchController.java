package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * The Controller for the Search Use Case.
 */
public class SearchController {

    private final SearchInputBoundary searchInputBoundary;

    /**
     * Constructs a SearchController with the given Input Boundary.
     *
     * @param searchInputBoundary the Input Boundary for the Search Use Case
     */
    public SearchController(SearchInputBoundary searchInputBoundary) {
        this.searchInputBoundary = searchInputBoundary;
    }

    /**
     * Executes a search with the given query.
     *
     * @param query the search query
     * @throws IllegalArgumentException for illegal arguments
     */
    public void execute(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be null or empty.");
        }

        // Encapsulate raw input into Input Data
        final SearchInputData inputData = new SearchInputData(query);

        // Pass input data to the interactor
        searchInputBoundary.execute(inputData);
    }
}
