package data_access.grade_api.outgoing_data_formatting.media_json_builders;

import org.json.JSONObject;

import entity.AbstractMedia;

/**
 * An abstract builder for creating JSON objects representing media objects.
 */
public abstract class AbstractMediaJSONBuilder {

    /**
     * Builds a JSON object representing a media object.
     *
     * @param media the media object to build the JSON object from
     * @return the JSON object representing the media object
     */
    public JSONObject buildJSON(AbstractMedia media) {
        final JSONObject mediaJSON = new JSONObject();
        mediaJSON.put("name", media.getName());
        mediaJSON.put("userRating", media.getUserRating().getScore());
        mediaJSON.put("externalRating", media.getExternalRating().getScore());
        for (String genre : media.getGenres()) {
            mediaJSON.append("genres", genre);
        }
        return mediaJSON;
    }
}