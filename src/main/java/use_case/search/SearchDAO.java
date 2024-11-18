package use_case.search;

import entity.AbstractMedia;
import entity.Movie;
import entity.Rating;
import use_case.note.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple DAO implementation that uses a Map to store media data.
 * This is a temporary solution for testing purposes.
 */
public class SearchDAO implements SearchDataAccessInterface {

    // Map to store media entities by name
    private final Map<String, AbstractMedia> mediaMap = new HashMap<>();

    /**
     * Constructor that populates the map with some mock data.
     */
    public SearchDAO() {
        // Mock data for testing purposes
        addMedia(new Movie("Inception", List.of("Sci-Fi", "Thriller"), new Rating(87), "A mind-bending thriller", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), 148));
        addMedia(new Movie("The Matrix", List.of("Action", "Sci-Fi"), new Rating(88), "A hacker learns the true nature of reality", List.of("Keanu Reeves", "Laurence Fishburne"), 136));
    }

    @Override
    public AbstractMedia searchByName(String name) throws DataAccessException {
        if (mediaMap.containsKey(name)) {
            return mediaMap.get(name);
        }
        // Returns null if media is not found
        return null;
    }

    @Override
    public void addMedia(AbstractMedia media) {
        mediaMap.put(media.getName(), media);
    }
}
