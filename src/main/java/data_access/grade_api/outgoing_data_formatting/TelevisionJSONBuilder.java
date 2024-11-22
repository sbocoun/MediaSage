package data_access.grade_api.outgoing_data_formatting;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.AbstractMedia;
import entity.Television;

/**
 * An abstract builder for creating JSON objects representing media objects.
 */
public class TelevisionJSONBuilder extends AbstractMediaJSONBuilder {

    /**
     * Builds a JSON object representing a media object.
     *
     * @param media the media object to build the JSON object from
     * @return the JSON object representing the media object
     */
    public JSONObject buildJSON(AbstractMedia media) {
        final JSONObject televisionJSON = super.buildJSON(media);
        final Television television = (Television) media;

        final JSONArray cast = new JSONArray();
        for (String actor : television.getCastMembers()) {
            cast.put(actor);
        }
        televisionJSON.put("cast", cast);

        final JSONObject seasonEpisodeMap = new JSONObject();
        for (int season : television.getSeasonToEpisodeCount().keySet()) {
            seasonEpisodeMap.put(String.valueOf(season), television.getEpisodes(season));
        }
        televisionJSON.put("season-episode-count", seasonEpisodeMap);
        televisionJSON.put("description", television.getDescription());
        return televisionJSON;
    }
}
