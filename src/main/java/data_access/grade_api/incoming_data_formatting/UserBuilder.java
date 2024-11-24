package data_access.grade_api.incoming_data_formatting;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.AbstractMedia;
import entity.MediaCollection;
import entity.Movie;
import entity.Television;
import entity.User;

/**
 * A builder for creating User objects from JSON data.
 */
public class UserBuilder {

    /**
     * Creates a User object from JSON data.
     *
     * @param data the JSON data to create the User object from
     * @return the User object created from the JSON data
     * @throws UnsupportedOperationException if the media type is not supported
     */
    public User createUser(JSONObject data) throws UnsupportedOperationException {
        final User user = new User(data.getString("username"), data.getString("password"));
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections = new ArrayList<>();
        final JSONArray collections;
        if (data.get("info") instanceof JSONObject) {
            user.setMediaCollections(mediaCollections);
            return user;
        }
        else {
            collections = data.getJSONArray("info");
        }
        for (int i = 0; i < collections.length(); i++) {
            final JSONObject collectionJSON = collections.getJSONObject(i);
            switch (collectionJSON.getString("mediaType")) {
                case "entity.Movie" -> {
                    final MediaCollectionBuilder<Movie> movieBuilder = new MediaCollectionBuilder<>(Movie.class);
                    mediaCollections.add(movieBuilder.createCollection(collectionJSON));
                }
                case "entity.Television" -> {
                    final MediaCollectionBuilder<Television> tvBuilder = new MediaCollectionBuilder<>(Television.class);
                    mediaCollections.add(tvBuilder.createCollection(collectionJSON));
                }
                default -> throw new UnsupportedOperationException("Unsupported entity type.");
            }
        }
        user.setMediaCollections(mediaCollections);
        return user;
    }
}
