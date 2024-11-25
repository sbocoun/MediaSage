package use_case.filter_list;

/**
 * The output boundary for the filter list use case.
 */
public interface FilterListOutputBoundary {

    /**
     * Prepares the success view for the filter list related Use Cases.
     *
     * @param filterListOutputData output data containing the data used to update the display
     */
    void prepareSuccessView(FilterListOutputData filterListOutputData);

    /**
     * Prepares the failure view for the filter list related Use Cases.
     *
     * @param message error message to display
     */
    void prepareFailView(String message);
}
