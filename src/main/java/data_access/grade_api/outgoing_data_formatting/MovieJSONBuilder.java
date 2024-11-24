package data_access.grade_api.outgoing_data_formatting;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.AbstractMedia;
import entity.Movie;

/**
 * An abstract builder for creating JSON objects representing media objects.
 */
public class MovieJSONBuilder extends AbstractMediaJSONBuilder {

    /**
     * Builds a JSON object representing a media object.
     *
     * @param media the media object to build the JSON object from
     * @return the JSON object representing the media object
     */
    public JSONObject buildJSON(AbstractMedia media) {
        final JSONObject movieJSON = super.buildJSON(media);
        final Movie movie = (Movie) media;

        final JSONArray cast = new JSONArray();
        for (String actor : movie.getCastMembers()) {
            cast.put(actor);
        }

        movieJSON.put("cast", cast);
        movieJSON.put("description", movie.getDescription());
        movieJSON.put("runtime", movie.getMinuteRuntime());

        return movieJSON;
    }
}
