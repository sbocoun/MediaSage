package use_case.list;

/**
 * The output boundary for the media collection list display Use Case.
 */
public interface ListOutputBoundary {

    /**
     * Prepares the success view for the media collection list display Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ListOutputData outputData);

    /**
     * Prepares the failure view for the media collection list display Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
