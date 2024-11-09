package entity;

import java.util.List;

/**
 * The representation of a list of media.
 *
 * @param <T> the type of media stored in the collection.
 */
public class MediaCollection<T> {
    private String name;
    private final String collectionType;
    private final List<T> mediaList;

    /**
     * Constructs a list of Media.
     * @param name the name of the list, provided by the user
     * @param collectionType the watch status type of the collection
     * @param mediaList the list of media contained in the collection
     */
    MediaCollection(String name, String collectionType, List<T> mediaList) {
        this.name = name;
        this.collectionType = collectionType;
        this.mediaList = mediaList;
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

    /**
     * Return a (mutable) list of media.
     * @return a list of media
     */
    public List<T> getMediaList() {
        return mediaList;
    }
}
