package data_access.grade_api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entity.MediaCollection;

/**
 * A builder for creating MediaCollection objects from JSON data.
 * @param <T> the type of media being created
 */
public class MediaCollectionBuilder<T> {

    private final Class<T> type;

    public MediaCollectionBuilder(Class<T> type) {
        this.type = type;
    }

    /**
     * Creates a MediaCollection object from JSON data.
     * @param collection the JSON data to create the MediaCollection object from
     * @return the MediaCollection object created from the JSON data
     */
    public MediaCollection<T> createCollection(JSONObject collection) {
        final String collectionName = collection.getString("name");
        final String collectionType = collection.getString("collectionType");
        final List<T> mediaList = new ArrayList<>();
        for (int i = 0; i < collection.getJSONArray("media").length(); i++) {
            final JSONObject media = collection.getJSONArray("media").getJSONObject(i);
            final MediaFactory<T> mediaFactory = new MediaFactory<>(type);
            mediaList.add(mediaFactory.createMedia(media));
        }
        return new MediaCollection<>(collectionName, collectionType, type, mediaList);
    }
}
