package use_case.list.removeMedia;

/**
 * Input Boundary for the Remove Media Use Case.
 */
public interface RemoveInputBoundary {
    /**
     * Executes the media collection list display use case.
     * @param input the input data
     */
    void execute(RemoveMediaInput input);
}
