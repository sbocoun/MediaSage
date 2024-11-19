package use_case.generate_recommendations;

/**
 * Exception thrown when there is an error with accessing data from the recommendation API.
 */
public class GenDataAccessException extends Exception {
    public GenDataAccessException(String string) {
        super(string);
    }
}
