package use_case.list;

import java.util.List;

/**
 * Output Data for the media collection list display Use Case.
 */
public class ListOutputData {
    private List<List<Object>> collectionData;
    private String collectionName = "";
    private String mediaType = "";
    private final List<String> availableCollections;
    private String errorMessage = "";

    /**
     * Success output data for the List Output Boundary.
     *
     * @param collectionToDisplay  the media collection to display
     * @param collectionName       the name of the media collection to display
     * @param mediaType            the type of the media  stored in the collection to display
     * @param availableCollections all media collections stored in the user
     */
    public ListOutputData(List<List<Object>> collectionToDisplay,
                          String collectionName,
                          String mediaType,
                          List<String> availableCollections) {
        this.collectionData = collectionToDisplay;
        this.collectionName = collectionName;
        this.mediaType = mediaType;
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

    public String getMediaType() {
        return mediaType;
    }
}
