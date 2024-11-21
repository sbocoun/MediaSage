package data_access.grade_api.outgoing_data_formatting;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.AbstractMedia;
import entity.Movie;

/**
 * An abstract builder for creating JSON objects representing media objects.
 */
public class MediaJSONBuilder {

    /**
     * Builds a JSON object representing a media object.
     *
     * @param media the media object to build the JSON object from
     * @return the JSON object representing the media object
     */
    public JSONObject buildJSON(AbstractMedia media) {
        final JSONObject mediaJSON = new JSONObject();
        mediaJSON.put("name", media.getName());
        mediaJSON.put("userRating", media.getUserRating());
        mediaJSON.put("externalRating", media.getExternalRating());
        for (String genre : media.getGenres()) {
            mediaJSON.append("genres", genre);
        }
        return mediaJSON;
    }

    /**
     * Builds a JSON object representing a movie object.
     *
     * @param media the movie object to build the JSON object from
     * @return the JSON object representing the movie object
     */
    public JSONObject buildJSON(Movie media) {
        final JSONObject movieJSON = buildJSON((AbstractMedia) media);

        final JSONArray cast = new JSONArray();
        for (String actor : media.getCastMembers()) {
            cast.put(actor);
        }

        movieJSON.put("cast", cast);
        movieJSON.put("description", media.getDescription());
        movieJSON.put("runtime", media.getMinuteRuntime());

        return movieJSON;
    }
}
