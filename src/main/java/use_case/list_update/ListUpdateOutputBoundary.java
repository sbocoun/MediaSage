package use_case.list_update;

/**
 * The output boundary for the media collection list update Use Case.
 */
public interface ListUpdateOutputBoundary {

    /**
     * Prepares the success view for the media collection list update Use Case,
     * informing the user that data has been saved to the database successfully.
     * @param outputData the output data
     */
    void prepareSuccessView(ListUpdateOutputData outputData);

    /**
     * Prepares the fail view for the media collection list display Use Case,
     * informing the user that the changes have not been successfully saved, with the error message raised.
     * @param outputData the output data
     */
    void prepareFailView(ListUpdateOutputData outputData);
}
