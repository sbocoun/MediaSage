package use_case.list.removeMedia;

/**
 * Output Data for the remove media Use Case.
 */
public class RemoveOutputData {
    private final boolean success;
    private final String message;

    public RemoveOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}