package entity;

import java.util.List;

/**
 * The representation of a list of Media.
 *
 * @param <T> the representation of Media
 */
public abstract class AbstractMediaCollection<T> {
    private String name;
    private final String collectionType;
    private final List<T> mediaCollection;

    /**
     * Constructs a list of Media.
     * @param name the name of the list, provided by the user
     * @param collectionType the watch status type of the collection
     * @param mediaCollection the list of media contained in the collection
     */
    AbstractMediaCollection(String name, String collectionType, List<T> mediaCollection) {
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

    public List<T> getMediaCollection() {
        return mediaCollection;
    }
}
