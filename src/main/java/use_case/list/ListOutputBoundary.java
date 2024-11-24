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
     * Prepares the logout view for the media collection list display Use Case.
     */
    void prepareLogoutView();

    /**
     * Prepares the fail view for the media collection list display Use Case.
     * @param listOutputData the output data
     */
    void prepareFailView(ListOutputData listOutputData);
}
