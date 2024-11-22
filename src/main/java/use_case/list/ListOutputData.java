package use_case.list;

import java.util.List;

/**
 * Output Data for the media collection list display Use Case.
 */
public class ListOutputData {
    private List<List<Object>> collectionDataToDisplay;
    private String collectionNameToDisplay = "";
    private final List<String> availableCollections;
    private String errorMessage = "";

    /**
     * Success output data for the List Output Boundary.
     * @param collectionToDisplay the media collection to display
     * @param collectionNameToDisplay the name of the media collection to display
     * @param availableCollections all media collections stored in the user
     */
    public ListOutputData(List<List<Object>> collectionToDisplay,
                          String collectionNameToDisplay,
                          List<String> availableCollections) {
        this.collectionDataToDisplay = collectionToDisplay;
        this.collectionNameToDisplay = collectionNameToDisplay;
        this.availableCollections = availableCollections;
    }

    /**
     * Fail output data for the List Output Boundary.
     * @param errorMessage the error message
     * @param availableCollections all movie collections stored in the user
     */
    public ListOutputData(String errorMessage, List<String> availableCollections) {
        this.errorMessage = errorMessage;
        this.availableCollections = availableCollections;
    }

    public List<List<Object>> getCollectionDataToDisplay() {
        return collectionDataToDisplay;
    }

    public String getCollectionNameToDisplay() {
        return collectionNameToDisplay;
    }

    public List<String> getAvailableCollections() {
        return availableCollections;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
