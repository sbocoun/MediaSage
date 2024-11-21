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
     * Saves a note for a given user. This will replace any existing note.
     *
     * <p>The password of the user must match that of the user saved in the system.</p>
     *
     * @param mediaCollectionsList the list of media collections to be saved
     * @return the contents of the note
     * @throws GradeDataAccessException if the user's note can not be saved for any reason
     */
    String saveMediaCollections(List<MediaCollection<? extends AbstractMedia>> mediaCollectionsList)
            throws GradeDataAccessException;

    /**
     * Returns the note associated with the user. The password
     * is not checked, so anyone can read the information.
     *
     * @return the contents of the note
     * @throws GradeDataAccessException if the user's note can not be loaded for any reason
     */
    String loadNote() throws GradeDataAccessException;
}
