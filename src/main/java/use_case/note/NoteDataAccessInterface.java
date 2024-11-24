package use_case.note;

import java.util.List;

import data_access.grade_api.GradeDataAccessException;
import entity.AbstractMedia;
import entity.MediaCollection;

/**
 * Interface for the NoteDAO. It consists of methods for
 * both loading and saving a note.
 */
public interface NoteDataAccessInterface {

    /**
     * Saves a list of media collections for a given user. This will replace any existing media collections list.
     * User must be logged in via LoginDataAccessInterface.
     *
     * @param mediaCollectionsList the list of media collections to be saved
     * @return the list of media collections
     * @throws GradeDataAccessException if the user's list of media collections can not be saved for any reason
     */
    List<MediaCollection<? extends AbstractMedia>> saveMediaCollections(
            List<MediaCollection<? extends AbstractMedia>> mediaCollectionsList)
            throws GradeDataAccessException;

    /**
     * Returns the list of media collections associated with the user. The password
     * is not checked, so anyone can read the information.
     *
     * @return the list of media collections
     * @throws GradeDataAccessException if the user's list of media collections can not be loaded for any reason
     */
    List<MediaCollection<? extends AbstractMedia>> loadMediaCollections() throws GradeDataAccessException;

    /**
     * Converts the list of media collections to string representation for debug use.
     *
     * @param mediaCollectionList the list of media collections to be converted to string
     * @return the string representation of the list of media collections
     */
    String convertCollectionsListToString(List<MediaCollection<? extends AbstractMedia>> mediaCollectionList);

    /**
     * Converts the string representation of media collections to its entity form.
     *
     * @param mediaCollectionsString the string representation of the list of media collections
     * @return the list of media collection entities
     */
    List<MediaCollection<? extends AbstractMedia>> convertStringToMediaCollections(String mediaCollectionsString);
}
