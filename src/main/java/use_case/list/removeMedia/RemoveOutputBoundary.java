package use_case.list.removeMedia;

/**
 * The output boundary for the removed list use case.
 */
public interface RemoveOutputBoundary {
    /**
     * Prepares the success view for the removed list related Use Cases.
     *
     * @param removeOutputData output data containing the data used to update the display
     */
    void prepareSuccessView(RemoveOutputData removeOutputData);

    /**
     * Prepares the fail view for the removed list related Use Cases.
     *
     * @param errorMessage output data containing the data used to update the display
     */
    void prepareFailView(String errorMessage);

}
