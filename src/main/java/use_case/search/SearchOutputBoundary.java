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
    void prepareSuccessView(SearchByCriteriaOutputData outputData);

    /**
     * Prepares the fail view for the search by movie title use case.
     * @param outputData the output data
     */
    void prepareFailView(SearchByCriteriaOutputData outputData);
}
