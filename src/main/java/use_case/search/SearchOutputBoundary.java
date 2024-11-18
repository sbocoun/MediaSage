package use_case.search;

/**
 * Output Boundary for Search.
 */
public interface SearchOutputBoundary {

    /**
     * Prepares the success view for Search.
     * @param outputData the output data from the search
     */
    void prepareSuccessView(SearchOutputData outputData);

    /**
     * Prepares the failure view for Search.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
