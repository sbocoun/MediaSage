package interface_adapter.note;

/**
 * The State for the debug view. Contains the string representation of a list of media collections.
 *
 */
public class NoteState {
    private String username;
    private String note = "";
    private String error;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
