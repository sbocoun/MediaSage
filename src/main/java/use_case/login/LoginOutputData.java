package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String notes;
    private final boolean useCaseFailed;

    public LoginOutputData(String username, String notes, boolean useCaseFailed) {
        this.username = username;
        this.notes = notes;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getNotes() {
        return notes;
    }
}
