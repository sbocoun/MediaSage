package data_access.grade_api.incoming_data_formatting;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entity.AbstractMedia;
import entity.MediaCollection;

/**
 * A builder for creating MediaCollection objects from JSON data.
 * @param <T> the type of media being created
 */
public class MediaCollectionBuilder<T extends AbstractMedia> {

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
        final List<T> mediaList = new ArrayList<>();
        for (int i = 0; i < collection.getJSONArray("media").length(); i++) {
            final JSONObject media = collection.getJSONArray("media").getJSONObject(i);
            final MediaFactory<T> mediaFactory = new MediaFactory<>(type);
            mediaList.add(mediaFactory.createMedia(media));
        }
        return new MediaCollection<>(
                collection.getString("name"),
                collection.getString("collectionType"),
                type,
                mediaList
        );
    }
}
