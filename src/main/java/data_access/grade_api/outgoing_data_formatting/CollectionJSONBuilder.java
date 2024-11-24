package data_access.grade_api.outgoing_data_formatting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.AbstractMedia;
import entity.MediaCollection;

/**
 * A builder for creating a JSON array of media collections.
 */
public class CollectionJSONBuilder {
    private final Map<String, AbstractMediaJSONBuilder> builders = new HashMap<>();

    public CollectionJSONBuilder() {
        builders.put("entity.Movie", new MovieJSONBuilder());
        builders.put("entity.Television", new TelevisionJSONBuilder());
        // Add other media type builders here
    }

    /**
     * Builds a JSON array of media collections.
     *
     * @param mediaCollections the list of media collections to build the JSON array from
     * @return the JSON array of media collections
     */
    public JSONArray buildMediaCollections(List<MediaCollection
            <? extends AbstractMedia>> mediaCollections) {
        final JSONArray collectionsJSON = new JSONArray();
        for (final MediaCollection<? extends AbstractMedia> mediaCollection : mediaCollections) {
            final JSONObject collectionJSON = buildMediaCollection(mediaCollection);
            collectionsJSON.put(collectionJSON);
        }
        return collectionsJSON;
    }

    /**
     * Builds a JSON object representing a media collection.
     *
     * @param mediaCollection the media collection to build the JSON object from
     * @return the JSON object representing the media collection
     * @throws UnsupportedOperationException if the media type is not supported
     */
    private JSONObject buildMediaCollection(MediaCollection<
            ? extends AbstractMedia> mediaCollection)
            throws UnsupportedOperationException {
        final JSONObject collectionJSON = new JSONObject();

        collectionJSON.put("name", mediaCollection.getName());
        collectionJSON.put("collectionType", mediaCollection.getCollectionType());
        final String mediaType = mediaCollection.getMediaType().getName();
        collectionJSON.put("mediaType", mediaType);
        final JSONArray mediaJSONArray = new JSONArray();
        for (AbstractMedia media : mediaCollection) {
            mediaJSONArray.put(builders.get(mediaType).buildJSON(media));
        }
        collectionJSON.put("media", mediaJSONArray);
        return collectionJSON;
    }
}
