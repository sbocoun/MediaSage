package use_case.list_update;

/**
 * Output Data for the media collection list display Use Case.
 */
public class ListUpdateOutputData {
    private String errorMessage = "";
    private String successMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
