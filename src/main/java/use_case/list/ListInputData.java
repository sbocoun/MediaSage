package use_case.list;

/**
 * The Input Data for the media collection list display Use Case.
 */
public class ListInputData {
    private String nameOfDesiredCollection;
    private String displayMediaType;

    public String getNameOfDesiredCollection() {
        return nameOfDesiredCollection;
    }

    public void setNameOfDesiredCollection(String nameOfDesiredCollection) {
        this.nameOfDesiredCollection = nameOfDesiredCollection;
    }

    public String getDisplayMediaType() {
        return displayMediaType;
    }

    public void setDisplayMediaType(String displayMediaType) {
        this.displayMediaType = displayMediaType;
    }
}
