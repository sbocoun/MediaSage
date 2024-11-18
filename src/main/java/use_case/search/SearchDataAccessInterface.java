package use_case.search;

import entity.AbstractMedia;
import use_case.note.DataAccessException;

/**
 * Interface for the SearchDAO. It consists of methods for
 * performing a search and retrieving media data.
 */
public interface SearchDataAccessInterface {
    /**
     * Searches for media based on the provided name.
     *
     * @param name the name of the media to search for
     * @return the media entity if found, or null if not found
     * @throws DataAccessException if there is an issue accessing the data
     */

    AbstractMedia searchByName(String name) throws DataAccessException;
    /**
     * Adds a media entity to the storage for testing purposes.
     *
     * @param media the media entity to add
     */

    void addMedia(AbstractMedia media);
}
