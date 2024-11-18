package use_case.list;

/**
 * Input Boundary for actions which are related to displaying media collection list.
 */
public interface ListInputBoundary {

    /**
     * Executes the media collection list display use case.
     * @param listInputData the input data
     */
    void execute(ListInputData listInputData);
}
