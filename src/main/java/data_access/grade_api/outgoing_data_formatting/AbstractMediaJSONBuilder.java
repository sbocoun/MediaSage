package data_access.grade_api.outgoing_data_formatting;

import org.json.JSONObject;

import entity.AbstractMedia;

/**
 * An abstract builder for creating JSON objects representing media objects.
 */
abstract class AbstractMediaJSONBuilder {

    /**
     * Builds a JSON object representing a media object.
     *
     * @param media the media object to build the JSON object from
     * @return the JSON object representing the media object
     */
    JSONObject buildJSON(AbstractMedia media) {
        final JSONObject mediaJSON = new JSONObject();
        mediaJSON.put("name", media.getName());
        mediaJSON.put("userRating", media.getUserRating());
        mediaJSON.put("externalRating", media.getExternalRating());
        for (String genre : media.getGenres()) {
            mediaJSON.append("genres", genre);
        }
        return mediaJSON;
    }
}
