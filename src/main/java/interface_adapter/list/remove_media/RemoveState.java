package interface_adapter.list.remove_media;

import java.util.List;

/**
 * The state for the Remove Use Case.
 */
public class RemoveState {
    private String currentCollectionName;
    private String errorMessage;
    private List<String> availableCollections;

    public String getCurrentCollectionName() {
        return currentCollectionName;
    }

    public void setCurrentCollectionName(String currentCollectionName) {
        this.currentCollectionName = currentCollectionName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getAvailableCollections() {
        return availableCollections;
    }

    public void setAvailableCollections(List<String> availableCollections) {
        this.availableCollections = availableCollections;
    }
}
