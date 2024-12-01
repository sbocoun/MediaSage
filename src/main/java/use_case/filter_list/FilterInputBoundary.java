package use_case.filter_list;

/**
 * Input boundary for the "filter list" use case.
 */
public interface FilterInputBoundary {

    /**
     * Executes the "filter list" use case.
     * @param filterListInputData the input data
     */
    void execute(FilterListInputData filterListInputData);
}
