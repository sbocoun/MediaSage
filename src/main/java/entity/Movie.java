package entity;

import java.util.List;

/**
 * The representation of a Movie.
 */
public class Movie extends AbstractMedia {
    private String description;
    private List<String> casts;
    private int minuteRuntime;

    public Movie(String name,
                 List<String> genres,
                 Rating externalRating,
                 String description,
                 List<String> casts,
                 int minuteRuntime) {
        super(name, genres, externalRating);
        this.description = description;
        this.casts = casts;
        this.minuteRuntime = minuteRuntime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCasts() {
        return casts;
    }

    public void setCasts(List<String> casts) {
        this.casts = casts;
    }

    public int getMinuteRuntime() {
        return minuteRuntime;
    }

    public void setMinuteRuntime(int minuteRuntime) {
        this.minuteRuntime = minuteRuntime;
    }
}
