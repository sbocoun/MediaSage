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

    public void setMediaCollections(List<MediaCollection<? extends AbstractMedia>> mediaCollections) {
        this.mediaCollections = mediaCollections;
    }

    /**
     * Return a (mutable) list of media collections.
     * @return a list of media collections
     */
    @SuppressWarnings("unchecked")
    public List<MediaCollection<Movie>> getMediaCollectionsMovies() {
        final List<MediaCollection<Movie>> movieCollections = new ArrayList<>();
        for (MediaCollection<? extends AbstractMedia> mediaCollection : mediaCollections) {
            if (mediaCollection.getMediaType() == Movie.class) {
                movieCollections.add((MediaCollection<Movie>) mediaCollection);
            }
        }
        return movieCollections;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
