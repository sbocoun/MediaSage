package data_access.grade_api.outgoing_data_formatting.media_json_builders;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Movie;

/**
 * A builder for creating a JSON object representing a movie object.
 */
public class MovieJSONBuilder extends AbstractMediaJSONBuilder {

    /**
     * Builds a JSON object representing a movie object.
     *
     * @param media the movie object to build the JSON object from
     * @return the JSON object representing the movie object
     */
    JSONObject buildJSON(Movie media) {
        final JSONObject movieJSON = super.buildJSON(media);

        final JSONArray cast = new JSONArray();
        for (String actor : media.getCastMembers()) {
            cast.put(actor);
            movieJSON.put("cast", cast);
            movieJSON.put("description", media.getDescription());
            movieJSON.put("runtime", media.getMinuteRuntime());
        }
        return movieJSON;
    }
}
