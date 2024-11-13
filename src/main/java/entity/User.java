package entity;

import java.util.List;

/**
 * The representation of a password-protected user for our program.
 */
public class User {

    private String name;
    private String password;
    private List<MediaCollection<AbstractMedia>> mediaCollections;
    // This is temporary before the JSON to entity parser is implemented
    private String notes;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String notes) {
        this(name, password);
        this.notes = notes;
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

    public List<MediaCollection<AbstractMedia>> getMediaCollections() {
        return mediaCollections;
    }

    public void setMediaCollections(List<MediaCollection<AbstractMedia>> mediaCollections) {
        this.mediaCollections = mediaCollections;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
