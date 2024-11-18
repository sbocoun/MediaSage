package use_case.search;

/**
 * Input Boundary for actions related to searching media.
 */
public interface SearchInputBoundary {

    /**
     * Executes the search use case with the provided input data.
     *
     * @param inputData the input data for the search
     */
    void execute(SearchInputData inputData);
}
