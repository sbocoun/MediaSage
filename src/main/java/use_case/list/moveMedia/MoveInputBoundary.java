package use_case.list.moveMedia;

/**
 * Input Boundary for actions which are related to displaying moved media collection list.
 */
public interface MoveInputBoundary {

    /**
     * Executes the media collection list display use case.
     * @param MoveInputData the input data
     */
    void execute(MoveInputData MoveInputData);
}
