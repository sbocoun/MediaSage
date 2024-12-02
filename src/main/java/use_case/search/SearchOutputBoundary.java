package use_case.search;

/**
 * Output boundary for the search by criteria use case.
 */
public interface SearchOutputBoundary {

    /**
     * Update the UI with the results of the search.
     *
     * @param outputData the search results to be displayed in the UI.
     */
    void displaySearchResults(SearchByCriteriaOutputData outputData);
}
