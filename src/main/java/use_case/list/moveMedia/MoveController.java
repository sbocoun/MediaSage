package use_case.list.moveMedia;

/**
 * Controller for move button.
 */
public class MoveController {
    private final MoveInputBoundary moveInteractor;

    public MoveController(MoveInputBoundary moveInteractor) {
        this.moveInteractor = moveInteractor;
    }

    /**
     * Moves movie from current collection.
     *
     * @param sourceCollectionName the name of the source collection where the movie currently exists
     * @param targetCollectionName the name of the target collection to move the movie to
     * @param movieName the name of the movie to be moved
     */
    public void moveMovie(String sourceCollectionName, String targetCollectionName, String movieName)
            throws IllegalArgumentException {
        if (sourceCollectionName == null || sourceCollectionName.isEmpty()) {
            throw new IllegalArgumentException("Source collection name cannot be null or empty.");
        }
        if (targetCollectionName == null || targetCollectionName.isEmpty()) {
            throw new IllegalArgumentException("Target collection name cannot be null or empty.");
        }
        if (movieName == null || movieName.isEmpty()) {
            throw new IllegalArgumentException("Movie name cannot be null or empty.");
        }

        final MoveInputData inputData = new MoveInputData();
        inputData.setSourceCollectionName(sourceCollectionName);
        inputData.setTargetCollectionName(targetCollectionName);
        inputData.setMovieName(movieName);

        moveInteractor.execute(inputData);
    }

}
