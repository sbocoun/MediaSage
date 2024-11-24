package data_access.grade_api.incoming_data_formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Movie;
import entity.Rating;
import entity.Television;

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
            case "entity.Television" -> tvShowConstructor(media);
            default -> null;
        };
    }

    private T tvShowConstructor(JSONObject media) {
        final List<String> genres = getArrayFromJSON(media.getJSONArray("genres"));
        final List<String> actors = getArrayFromJSON(media.getJSONArray("cast"));
        final Map<Integer, Integer> seasonToEpisodeCount = getSeasonEpisodeMap(
                media.getJSONObject("season-episode-count"));
        final Television result = new Television(
                media.getString("name"),
                genres,
                new Rating(media.getInt("userRating")),
                new Rating(media.getInt("externalRating")),
                media.getString("description"),
                actors);
        result.setSeasonToEpisodeCount(seasonToEpisodeCount);
        return type.cast(result);
    }

    private T movieConstructor(JSONObject media) {
        final List<String> genres = getArrayFromJSON(media.getJSONArray("genres"));
        final List<String> actors = getArrayFromJSON(media.getJSONArray("cast"));
        final Movie result = new Movie(
                media.getString("name"),
                genres,
                new Rating(media.getInt("userRating")),
                new Rating(media.getInt("externalRating")),
                media.getString("description"),
                actors,
                media.getInt("runtime"));
        return type.cast(result);
    }

    private List<String> getArrayFromJSON(JSONArray jsonArray) {
        final List<String> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    }

    private Map<Integer, Integer> getSeasonEpisodeMap(JSONObject seasonsJSON) {
        final Map<Integer, Integer> result = new HashMap<>();
        for (String season : seasonsJSON.keySet()) {
            result.put(Integer.valueOf(season), seasonsJSON.getInt(season));
        }
        return result;
    }
}
