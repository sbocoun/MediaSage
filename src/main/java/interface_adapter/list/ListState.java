package interface_adapter.list;

import java.util.List;

/**
 * The State for the media collection list.
 */
public class ListState {
    private List<String> availableCollections;
    private String currentCollectionName;
    private ListTableModel tableModel;
    private String errorMessage;
    private String successMessage;
    private String generatedRecommendations;

    public List<String> getAvailableCollections() {
        return availableCollections;
    }

    public void setAvailableCollections(List<String> availableCollections) {
        this.availableCollections = availableCollections;
    }

    public String getCurrentCollectionName() {
        return currentCollectionName;
    }

    public void setCurrentCollectionName(String currentCollectionName) {
        this.currentCollectionName = currentCollectionName;
    }

    public ListTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(ListTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getGeneratedRecommendations() {
        return generatedRecommendations;
    }

    public void setGeneratedRecommendations(String generatedRecommendations) {
        this.generatedRecommendations = generatedRecommendations;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
