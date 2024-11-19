package use_case.note;

import data_access.grade_api.GradeDataAccessException;

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
     * @param note the note to be saved
     * @return the contents of the note
     * @throws GradeDataAccessException if the user's note can not be saved for any reason
     */
    String saveNote(String note) throws GradeDataAccessException;

    /**
     * Returns the note associated with the user. The password
     * is not checked, so anyone can read the information.
     *
     * @return the contents of the note
     * @throws GradeDataAccessException if the user's note can not be loaded for any reason
     */
    String loadNote() throws GradeDataAccessException;

    /**
     * Set the user whose notes the app retrieves.
     * @param username the username of the user
     */
    void setCurrentUsername(String username);

    /**
     * Sets the password of the user for authentication.
     * @param password the password of the user
     */
    void setCurrentPassword(String password);

}
