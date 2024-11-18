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
     * @param listOutputData output data containing the explanation of the failure, and a list of available collections
     */
    void prepareFailView(ListOutputData listOutputData);

}
