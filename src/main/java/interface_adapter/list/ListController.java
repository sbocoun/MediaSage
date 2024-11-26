package interface_adapter.list;

import use_case.list.ListInputBoundary;
import use_case.list.ListInputData;
import use_case.list.removeMedia.RemoveInputBoundary;
import use_case.list.removeMedia.RemoveMediaInput;

/**
 * Controller for displaying the media collection list.
 */
public class ListController {
    private final ListInputBoundary listInteractor;
    private final RemoveInputBoundary removeInteractor;

    /**
     * Constructor for ListController.
     *
     * @param listInteractor the interactor for list-related use cases
     * @param removeInteractor the interactor for remove-related use cases
     */
    public ListController(ListInputBoundary listInteractor, RemoveInputBoundary removeInteractor) {
        this.listInteractor = listInteractor;
        this.removeInteractor = removeInteractor;
    }

    /**
     * Executes the list display related Use Cases.
     *
     * @param desiredCollectionName the name of the media collection to retrieve for display
     */
    public void executeCollectionSwitch(String desiredCollectionName) {
        final ListInputData listInputData = new ListInputData();
        listInputData.setNameOfDesiredCollection(desiredCollectionName);
        listInteractor.execute(listInputData);
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
        removeMediaInput.setMovieToRemove(movieName);
        removeInteractor.execute(removeMediaInput);
    }

    /**
     * Filters current list based on recommendations.
     *
     * @param filter the key we want to filter for
     */
    public void filterMoviesByDescription(String filter) {
    }
}
