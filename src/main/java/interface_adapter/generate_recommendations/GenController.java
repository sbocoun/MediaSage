package interface_adapter.generate_recommendations;

import use_case.generate_recommendations.GenInputBoundary;

public class GenController {

    private final GenInputBoundary genInteractor;

    public GenController(GenInputBoundary genInteractor) {
        this.genInteractor = genInteractor;
    }

    /**
     * Executes the generate movie recommendations use case.
     * @param movies the movies to generate a recommendation from.
     */
    public void execute(String movies) {
        genInteractor.execute(movies);
    }
}
