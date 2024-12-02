package use_case.filter_list;

import entity.AbstractMedia;
import entity.MediaCollection;

/**
 * User DAO interface for the Filter interactor. Allows the interactor to
 * retrieve the specified collection for filtering.
 */
public interface FilterDataAccessInterface {

    /**
     * Retrieve a named collection of media.
     * @param collectionName the name of the collection to retrieve
     *                  (e.g. entity.Movie, entity.Television)
     * @param mediaType the type of media stored in the collection
     * @param <T> the type of media stored in the collection
     * @return the named collection of media
     */
    <T extends AbstractMedia> MediaCollection<T> getNamedCollection(String collectionName, String mediaType);

    /**
     * Retrieve the current username.
     * @return the current username
     */
    String getCurrentUsername();
}
