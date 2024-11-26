package use_case.list.removeMedia;

/**
 * The Input Data for the remove media Use Case.
 */
public class RemoveMediaInput {
    private String collectionName;
    private String mediaName;

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMovieToRemove(String mediaName) {
        this.mediaName = mediaName;
    }
}
