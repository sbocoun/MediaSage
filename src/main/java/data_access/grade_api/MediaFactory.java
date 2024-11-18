package data_access.grade_api;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Movie;
import entity.Rating;

/**
 * A factory for creating media collections.
 * @param <T> the type of media being created
 */
public final class MediaFactory<T> {

    private final Class<T> type;

    public MediaFactory(Class<T> type) {
        this.type = type;
    }

    /**
     * Creates a MediaCollection object from JSON data.
     * @param media the JSON data to create the Media object from
     * @return the MediaCollection object created from the JSON data
     */
    public T createMedia(JSONObject media) {
        return switch (type.getName()) {
            case "entity.Movie" -> movieConstructor(media);
            case "entity.TVShow" -> tvShowConstructor(media);
            case "entity.Music" -> musicConstructor(media);
            default -> null;
        };
    }

    private T musicConstructor(JSONObject media) {
        return null;
    }

    private T tvShowConstructor(JSONObject media) {
        return null;
    }

    private T movieConstructor(JSONObject media) {
        final JSONArray genresJSON = media.getJSONArray("genres");
        final List<String> genres = new java.util.ArrayList<>();
        for (int i = 0; i < genresJSON.length(); i++) {
            genres.add(genresJSON.getString(i));
        }
        final JSONArray actorsJSON = media.getJSONArray("cast");
        final List<String> actors = new java.util.ArrayList<>();
        for (int i = 0; i < actorsJSON.length(); i++) {
            actors.add(actorsJSON.getString(i));
        }
        final Movie result = new Movie(media.getString("name"),
                genres,
                new Rating(media.getInt("rating")),
                media.getString("description"),
                actors,
                media.getInt("runtime"));
        if (type.isInstance(result)) {
            return type.cast(result);
        }
        else {
            throw new IllegalArgumentException("Invalid media type");
        }
    }
}
