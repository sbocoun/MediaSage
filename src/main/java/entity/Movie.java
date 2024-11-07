package entity;

import java.util.List;

public class Movie extends AbstractMedia {
    String description;
    List<String> cast;
    int minuteRuntime;

    public Movie(String name, List<String> genres,
                 Rating externalRating, String description,
                 List<String> cast, int minuteRuntime) {
        super(name, genres, externalRating);
        this.description = description;
        this.cast = cast;
        this.minuteRuntime = minuteRuntime;

    }

    public String getDescription() {
        return description;
    }

    public List<String> getCast() {
        return cast;
    }

    public int getMinuteRuntime() {
        return minuteRuntime;
    }
}
