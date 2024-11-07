package entity;

import java.util.List;

/**
 * The representation of a list of Media.
 */
public abstract class AbstractMediaCollection {
    private String name;
    private String collectionType;
    private List<AbstractMedia> mediaCollection;

    /**
     * Constructs a list of Media.
     * @param name the name of the list, provided by the user
     * @param collectionType the watch status type of the collection
     * @param mediaCollection the list of media contained in the collection
     */
    AbstractMediaCollection(String name, String collectionType, List<AbstractMedia> mediaCollection) {
        this.name = name;
        this.collectionType = collectionType;
        this.mediaCollection = mediaCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public List<AbstractMedia> getMediaCollection() {
        return mediaCollection;
    }

    public void setMediaCollection(List<AbstractMedia> mediaCollection) {
        this.mediaCollection = mediaCollection;
    }
}

