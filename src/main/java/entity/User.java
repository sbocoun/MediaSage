package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a password-protected user for our program.
 */
public class User {

    private String name;
    private String password;
    private List<MediaCollection<? extends AbstractMedia>> mediaCollections;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMediaCollections(List<MediaCollection<? extends AbstractMedia>> mediaCollections) {
        this.mediaCollections = mediaCollections;
    }

    /**
     * Return a (mutable) list of media collections.
     * @param <T> the type of media stored in the collection
     * @param mediaType the type of media stored in the collection
     * @return a list of media collections
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractMedia> List<MediaCollection<T>> getSpecifiedMediaCollections(Class<T> mediaType) {
        final List<MediaCollection<T>> specifiedCollections = new ArrayList<>();
        for (MediaCollection<?> mediaCollection : mediaCollections) {
            if (mediaType.isAssignableFrom(mediaCollection.getMediaType())) {
                specifiedCollections.add((MediaCollection<T>) mediaCollection);
            }
        }
        return specifiedCollections;
    }

    /**
     * Return a (mutable) list of all media collections.
     * @return a list of all media collections
     */
    public List<MediaCollection<? extends AbstractMedia>> getAllMediaCollections() {
        return mediaCollections;
    }
}
