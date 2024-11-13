package use_case.generate_recommendations;

/**
 * The output boundary for the Generate Movie Recommendations use case.
 */
public interface GenOutputBoundary {
    /**
     * Prepares the success view for the movie recommendations Use Case.
     * @param message the output data
     */
    void prepareSuccessView(String message);

    /**
     * Prepares the failure view for the movie recommendations Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
