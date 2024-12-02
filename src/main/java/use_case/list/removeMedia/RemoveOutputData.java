package use_case.list.removeMedia;

import java.util.List;

/**
 * Output Data for the Remove Use Case.
 */
public class RemoveOutputData {
    private final String collectionName;
    private final List<List<Object>> collectionData;
    private final String collectionType;
    private final List<String> availableCollections;
    private final String errorMessage;

    /**
     * Constructor for success output data.
     *
     * @param collectionData       the updated media collection data to display
     * @param collectionName       the name of the updated media collection
     * @param collectionType       the type of the updated media collection
     * @param availableCollections all media collections stored in the user
     */
    public RemoveOutputData(List<List<Object>> collectionData, String collectionName,
                            String collectionType, List<String> availableCollections) {
        this.collectionData = collectionData;
        this.collectionName = collectionName;
        this.collectionType = collectionType;
        this.availableCollections = availableCollections;
        this.errorMessage = "";
    }

    /**
     * Constructor for failure output data.
     *
     * @param errorMessage         the error message
     * @param availableCollections all media collections stored in the user
     */
    public RemoveOutputData(String errorMessage, List<String> availableCollections) {
        this.collectionData = null;
        this.collectionName = "";
        this.collectionType = "";
        this.availableCollections = availableCollections;
        this.errorMessage = errorMessage;
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

    /**
     * Checks if the output represents a successful operation.
     *
     * @return true if there is no error message, false otherwise
     */
    public boolean isSuccess() {
        return errorMessage == null || errorMessage.isEmpty();
    }
}
