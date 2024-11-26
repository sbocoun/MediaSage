package use_case.list.removeMedia;

/**
 * Output boundary for the remove media Use Case.
 */
public interface RemoveOutputBoundary {
    /**
     * Prepares the success view for the media collection list display.
     * @param outputData the output data
     */
    void present(RemoveOutputData outputData);
}
