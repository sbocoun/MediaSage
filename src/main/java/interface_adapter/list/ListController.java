package interface_adapter.list;

import use_case.list.ListInputBoundary;
import use_case.list.ListInputData;

/**
 * Controller for displaying the media collection list.
 */
public class ListController {
    private final ListInputBoundary listInteractor;

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
}
