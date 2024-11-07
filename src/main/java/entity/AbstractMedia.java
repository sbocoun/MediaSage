package entity;

import java.util.List;

/**
 * The representation of a piece of any Media.
 */
public abstract class AbstractMedia {
    private String name;
    private List<String> genres;
    private Rating userRating;
    private Rating externalRating;

    AbstractMedia(String name, List<String> genres, Rating externalRating) {
        this.name = name;
        this.genres = genres;
        this.userRating = userRating;
        this.externalRating = externalRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating userRating) {
        this.userRating = userRating;
    }

    public Rating getExternalRating() {
        return externalRating;
    }

    public void setExternalRating(Rating externalRating) {
        this.externalRating = externalRating;
    }
}
