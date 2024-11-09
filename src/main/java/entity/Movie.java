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
                 Rating externalRating,
                 String description,
                 List<String> castMembers,
                 int minuteRuntime) {
        super(name, genres, externalRating);
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
}
