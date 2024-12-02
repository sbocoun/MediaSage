package use_case.list.removeMedia;

import java.util.List;

/**
 * Output Data for the media collection list display Use Case.
 */
public class RemoveOutputData {
    private String collectionName = "";
    private final List<String> availableCollections;
    private String errorMessage = "";

    /**
     * Success output data for the List Output Boundary.
     *
     * @param collectionToDisplay  the media collection to display
     * @param collectionName       the name of the media collection to display
     * @param collectionType       the type of the media collection to display
     * @param availableCollections all media collections stored in the user
     */
    public RemoveOutputData(List<List<Object>> collectionToDisplay,
                          String collectionName,
                          String collectionType,
                          List<String> availableCollections) {
        this.collectionName = collectionName;
        this.availableCollections = availableCollections;
    }

    /**
     * Fail output data for the List Output Boundary.
     * @param errorMessage the error message
     * @param availableCollections all movie collections stored in the user
     */
    public RemoveOutputData(String errorMessage, List<String> availableCollections) {
        this.errorMessage = errorMessage;
        this.availableCollections = availableCollections;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public List<String> getAvailableCollections() {
        return availableCollections;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
