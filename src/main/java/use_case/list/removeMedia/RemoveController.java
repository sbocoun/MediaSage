package use_case.list.removeMedia;

/**
 * Controller for remove button.
 */
public class RemoveController {
    private final RemoveInputBoundary removeInteractor;

    public RemoveController(RemoveInputBoundary removeInteractor) {
        this.removeInteractor = removeInteractor;
    }

    /**
     * Removes a movie from the current collection.
     *
     * @param desiredCollectionName the name of the media collection to modify
     * @param movieName the name of the selected movie
     */
    public void removeMovie(String desiredCollectionName, String movieName) {
        final RemoveMediaInput removeMediaInput = new RemoveMediaInput();
        removeMediaInput.setCollectionName(desiredCollectionName);
        removeMediaInput.setMediaToRemove(movieName);
        removeInteractor.execute(removeMediaInput);
    }
}
