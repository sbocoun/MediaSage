package data_access.movies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import entity.Movie;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

/**
 * An in-memory implementation of movie database access.
 */
public class InMemoryMovieDAO implements MovieDBDataAccessInterface {
    private final Map<String, Movie> movies = new HashMap<>();

    @Override
    public void setApiKey(String apikey) {
        throw new UnsupportedOperationException("Not applicable here.");
    }

    /**
     * Get a movie from the in-memory database.
     *
     * @param movieName The name of the movie to be looked up.
     * @return a Movie entity corresponding to the name of the movie
     * @throws MovieDBDataAccessException if the movie is not found
     */
    @Override
    public Movie getMovie(String movieName) throws MovieDBDataAccessException {
        if (!movies.containsKey(movieName)) {
            throw new MovieDBDataAccessException(movieName + " not found.");
        }
        return movies.get(movieName);
    }

    /**
     * Add a movie to the in-memory database.
     * @param movie a movie
     */
    public void addMovie(Movie movie) {
        movies.put(movie.getName(), movie);
    }

    /**
     * Load the sample movie response from resources to the in-memory database.
     */
    public void loadMovieFromFile() {
        try {
            final JSONObject rawMovieDetails = new JSONObject(Files.readString(Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader()
                            .getResource("tmdb-sample-response.json")).toURI())));
            final JSONObject rawMovieCast = new JSONObject(Files.readString(Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader()
                            .getResource("tmdb-sample-cast-response.json")).toURI())));
            addMovie(MovieJSONFormat.parseMovie(rawMovieDetails, rawMovieCast));
        }
        catch (IOException | URISyntaxException ex) {
            System.out.println("Reading movie DB sample response failed. Check if "
                    + "both src/main/resources/tmdb-sample-response.json "
                    + "and src/main/resources/tmdb-sample-cast-response.json exist.");
        }
    }
}
