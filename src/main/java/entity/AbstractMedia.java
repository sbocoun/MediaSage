package entity;

import java.util.List;

/**
 * The representation of a piece of any Media.
 */
public abstract class AbstractMedia {
    private String name;
    private List<String> genres;
    private final Rating userRating;
    private final Rating externalRating;

    AbstractMedia(String name, List<String> genres, Rating userRating, Rating externalRating) {
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

    public int getUserRating() {
        return userRating.getScore();
    }

    /**
     * Updates the user rating to newRating, if the 0 <= newRating <= 100.
     * @param newRating new user rating score for the media
     */
    public void setUserRatingScore(int newRating) {
        userRating.setScore(newRating);
    }

    public int getExternalRating() {
        return externalRating.getScore();
    }

    /**
     * Updates the external rating to newExternalRatingScore.
     * @param newExternalRatingScore new external rating score for the media
     */
    public void setExternalRating(int newExternalRatingScore) {
        this.externalRating.setScore(newExternalRatingScore);
    }
}
