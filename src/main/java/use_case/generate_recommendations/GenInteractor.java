package use_case.generate_recommendations;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import use_case.note.DataAccessException;

/**
 * The "Use Case Interactor" for the "generate movie recommendations" use case.
 */
public class GenInteractor implements GenInputBoundary {

    private final GenDataAccessInterface genDataAccessInterface;
    private final GenOutputBoundary genOutputBoundary;

    public GenInteractor(GenDataAccessInterface genDataAccessInterface, GenOutputBoundary genOutputBoundary) {
        this.genDataAccessInterface = genDataAccessInterface;
        this.genOutputBoundary = genOutputBoundary;
    }

    /**
     * Executes the generate movie recommendations use case.
     * @param movies the text to generate a recommendation from.
     */
    @Override
    public void execute(String movies) {
        try {
            final List<String> formattedMovies = Arrays.asList(movies.split("\\R"));
            // Assumes the note contains only movie names separated by a newline character.
            final JSONArray response = genDataAccessInterface
                    .getRecommendation(formattedMovies, "movie", "movie");
            final StringBuilder recommendedMovies = new StringBuilder();
            for (int i = 0; i < response.length(); i++) {
                final JSONObject movieRepresentation = (JSONObject) response.get(i);
                final String movieName = movieRepresentation.getString("name");
                recommendedMovies.append(movieName);
                recommendedMovies.append("\n");
            }
            genOutputBoundary.prepareSuccessView(recommendedMovies.toString());
        }
        catch (DataAccessException ex) {
            genOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}
