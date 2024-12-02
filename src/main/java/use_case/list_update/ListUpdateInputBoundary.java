package use_case.list_update;

/**
 * Input Boundary for actions related to updating the user's media collecting list with changes from the app.
 */
public interface ListUpdateInputBoundary {

    /**
     * Executes the media rating update use case.
     * @param listUpdateInputData the input data
     */
    void executeRatingUpdate(ListUpdateInputData listUpdateInputData);
}
