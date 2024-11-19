package use_case.note;

/**
 * Exception thrown when there is an error with accessing the Grade API.
 */
public class GradeDataAccessException extends Exception {
    public GradeDataAccessException(String string) {
        super(string);
    }
}
