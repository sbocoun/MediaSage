package interface_adapter.list;

import java.util.List;

import javax.swing.table.TableModel;

/**
 * The State for the media collection list.
 */
public class ListState {
    private List<String> availableCollections;
    private String currentCollectionName;
    private String currentCollectionType;
    private TableModel tableModel;

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

    public String getCurrentCollectionType() {
        return currentCollectionType;
    }

    public void setCurrentCollectionType(String currentCollectionType) {
        this.currentCollectionType = currentCollectionType;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
}
