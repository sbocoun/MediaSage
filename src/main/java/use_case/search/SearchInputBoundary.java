package use_case.search;

/**
 * Input boundary for the search by criteria use case.
 */
public interface SearchInputBoundary {

    /**
     * Execute the search by criteria use case.
     *
     * @param inputData the search criteria input data.
     */
    void execute(SearchByCriteriaInputData inputData);
}
