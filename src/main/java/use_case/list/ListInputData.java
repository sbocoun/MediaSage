package use_case.list;

/**
 * The Input Data for the media collection list display Use Case.
 */
public class ListInputData {
    private String collectionNameToDisplay;
    private String displayMediaType;

    public String getCollectionNameToDisplay() {
        return collectionNameToDisplay;
    }

    public void setCollectionNameToDisplay(String collectionNameToDisplay) {
        this.collectionNameToDisplay = collectionNameToDisplay;
    }

    public String getDisplayMediaType() {
        return displayMediaType;
    }

    public void setDisplayMediaType(String displayMediaType) {
        this.displayMediaType = displayMediaType;
    }
}
