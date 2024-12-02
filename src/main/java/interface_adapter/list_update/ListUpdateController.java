package interface_adapter.list_update;

import use_case.list_update.ListUpdateInputBoundary;
import use_case.list_update.ListUpdateInputData;

/**
 * Controller for updating the media collection list.
 */
public class ListUpdateController {
    private final ListUpdateInputBoundary listUpdateInteractor;

    public ListUpdateController(ListUpdateInputBoundary listUpdateInteractor) {
        this.listUpdateInteractor = listUpdateInteractor;
    }

    /**
     * Executes user rating update for a media in the user's collections.
     *
     * @param collectionName the name of the current collection
     * @param mediaName      the name of the media that was updated
     * @param updatedRating  the new user rating value
     */
    public void executeUserRatingUpdate(String collectionName, String mediaName, int updatedRating) {
        final ListUpdateInputData listUpdateInputData = new ListUpdateInputData();
        listUpdateInputData.setCollectionName(collectionName);
        listUpdateInputData.setMediaName(mediaName);
        listUpdateInputData.setNewRating(updatedRating);
        listUpdateInteractor.executeRatingUpdate(listUpdateInputData);
    }
}
