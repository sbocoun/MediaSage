package use_case.list;

import java.util.List;

/**
 * Output Data for the media collection list display Use Case.
 */
public class ListOutputData {
    private List<List<Object>> collectionData;
    private String collectionName = "";
    private String collectionType = "";
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
    public ListOutputData(List<List<Object>> collectionToDisplay,
                          String collectionName,
                          String collectionType,
                          List<String> availableCollections) {
        this.collectionData = collectionToDisplay;
        this.collectionName = collectionName;
        this.collectionType = collectionType;
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

    public List<List<Object>> getCollectionData() {
        return collectionData;
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

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
}
