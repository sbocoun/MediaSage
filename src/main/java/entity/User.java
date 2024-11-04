package entity;

import java.util.List;

/**
 * The representation of a password-protected user for our program.
 */
public class User {

    private final String name;
    private final String password;
    // private List<MediaCollection> mediaCollections;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        // this.mediaCollections = mediaCollections;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<MediaCollection> getMediaCollections() {
        return mediaCollections;
    }

    public void setMediaCollections(List<MediaCollection> mediaCollections) {
        this.mediaCollections = mediaCollections;
    }

}
