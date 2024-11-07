package entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMedia {

    String name;
    List<String> genres = new ArrayList<>();
    Rating userRating = null;
    Rating externalRating;

    public AbstractMedia(String name, List<String> genres, Rating externalRating) {
        this.name = name;
        this.genres = genres;
        this.externalRating = externalRating;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Rating getExternalRating() {
        return userRating;
    }
    
    public void setUserRating(Rating userRating) {
        this.userRating = userRating;
    }

    public Rating getUserRating(Rating userRating) {
        return userRating;
    }

}
