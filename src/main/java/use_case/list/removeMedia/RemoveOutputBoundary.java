package use_case.list.removeMedia;

import use_case.list.ListOutputData;

/**
 * Output boundary for the remove media Use Case.
 */
public interface RemoveOutputBoundary {
    /**
     * Prepares the success view for the media collection list display.
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
