package use_case.generate_recommendations;

/**
 * The Input Boundary for our movie-generation use case.
 */
public interface GenInputBoundary {
    /**
     * Executes the generate movie recommendations use case.
     * @param movies the movies to generate a recommendation from.
     */
    void execute(String movies);
}