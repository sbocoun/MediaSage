package entity;

import java.util.List;

/**
 * The representation of a Movie.
 */
public class Movie extends AbstractMedia {
    private String description;
    private List<String> castMembers;
    private int minuteRuntime;

    public Movie(String name,
                 List<String> genres,
                 Rating userRating,
                 Rating externalRating,
                 String description,
                 List<String> castMembers,
                 int minuteRuntime) {
        super(name, genres, userRating, externalRating);
        this.description = description;
        this.castMembers = castMembers;
        this.minuteRuntime = minuteRuntime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(List<String> castMembers) {
        this.castMembers = castMembers;
    }

    public int getMinuteRuntime() {
        return minuteRuntime;
    }

    public void setMinuteRuntime(int minuteRuntime) {
        this.minuteRuntime = minuteRuntime;
    }

    @Override
    public String toString() {
        return "Title: " + getName() + '\n'
                + "Description: " + description + '\n'
                + "Runtime: " + minuteRuntime + " minutes\n"
                + "Genres: " + String.join(", ", getGenres()) + '\n'
                + "Cast: " + String.join(", ", castMembers) + "\n"
                + "User Rating: " + getUserRating() + "\n"
                + "External Rating: " + getExternalRating();
    }
}
