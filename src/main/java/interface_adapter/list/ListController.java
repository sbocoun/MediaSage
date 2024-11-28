package interface_adapter.list;

import use_case.list.ListInputBoundary;
import use_case.list.ListInputData;

/**
 * Controller for displaying the media collection list.
 */
public class ListController {
    private final ListInputBoundary listInteractor;

    /**
     * Constructor for ListController.
     *
     * @param listInteractor the interactor for list-related use cases
     */
    public ListController(ListInputBoundary listInteractor) {
        this.listInteractor = listInteractor;
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
     * Filters current list based on recommendations.
     *
     * @param filter the key we want to filter for
     */
    public void filterMoviesByDescription(String filter) {
    }
}
