package interface_adapter.filter_list;

/**
 * The output boundary for the filter list use case.
 */
public interface FilterListOutputBoundary {

    /**
     * Prepares the success view for the filter list related Use Cases.
     *
     * @param filterListOutputData output data containing the data used to update the display
     */
    public void prepareSuccessView(FilterListOutputData filterListOutputData);

    /**
     * Prepares the failure view for the filter list related Use Cases.
     *
     * @param message error message to display
     */
    public void prepareFailView(String message);
}
