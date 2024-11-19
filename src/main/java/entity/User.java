package entity;

import java.util.List;

/**
 * The representation of a password-protected user for our program.
 */
public class User {

    private String name;
    private String password;
    private List<MediaCollection<Movie>> movieCollections;

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

    public void setMovieCollections(List<MediaCollection<Movie>> movieCollections) {
        this.movieCollections = movieCollections;
    }

    public List<MediaCollection<Movie>> getMovieCollections() {
        return movieCollections;
    }

    // /**
    //  * Return a (mutable) list of media collections.
    //  * @return a list of media collections
    //  */
    // @SuppressWarnings("unchecked")
    // public List<MediaCollection<Movie>> getMediaCollectionsMovies() {
    //     final List<MediaCollection<Movie>> movieCollections = new ArrayList<>();
    //     for (MediaCollection<? extends AbstractMedia> mediaCollection : mediaCollections) {
    //         if (mediaCollection.getMediaType() == Movie.class) {
    //             movieCollections.add((MediaCollection<Movie>) mediaCollection);
    //         }
    //     }
    //     return movieCollections;
    // }
}
