package entity;

import java.util.List;

/**
 * The representation of a list of media.
 * @param <T> the type of media stored in the collection
 */
public class MediaCollection<T> {
    private String name;
    private final String collectionType;
    private final Class<T> mediaType;
    private final List<T> mediaList;

    /**
     * Constructs a list of Media.
     * @param name the name of the list, provided by the user
     * @param collectionType the watch status type of the collection
     *                 (e.g. "watched", "to watch", "watching")
     * @param mediaType the type of media stored in the collection
     * @param mediaList the list of media contained in the collection
     */
    public MediaCollection(String name, String collectionType, Class<T> mediaType, List<T> mediaList) {
        this.name = name;
        this.collectionType = collectionType;
        this.mediaType = mediaType;
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

    /**
     * Add a piece of media to the collection.
     * @param media the media to add
     */
    public void addMedia(T media) {
        mediaList.add(media);
    }

    /**
     * Remove a piece of media from the collection.
     * @param media the media to remove
     */
    public void removeMedia(T media) {
        mediaList.remove(media);
    }

    /**
     * Return the type of media stored in the collection.
     * @return the type of media stored in the collection
     */
    public Class<T> getMediaType() {
        return mediaType;
    }
}
