package use_case.list.moveMedia;

/**
 * Input data for the move media use case.
 */
public class MoveInputData {
    private String sourceCollectionName;
    private String targetCollectionName;
    private String mediaName;

    public String getSourceCollectionName() {
        return sourceCollectionName;
    }

    public void setSourceCollectionName(String sourceCollectionName) {
        this.sourceCollectionName = sourceCollectionName;
    }

    public String getTargetCollectionName() {
        return targetCollectionName;
    }

    public void setTargetCollectionName(String targetCollectionName) {
        this.targetCollectionName = targetCollectionName;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }
}
