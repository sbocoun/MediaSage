package entity;

import java.util.List;

/**
 * A non-existent media type used for testing.
 */
public class PropMedia extends AbstractMedia {
    public PropMedia(String name, List<String> genres, Rating userRating, Rating externalRating) {
        super(name, genres, userRating, externalRating);
    }
}
