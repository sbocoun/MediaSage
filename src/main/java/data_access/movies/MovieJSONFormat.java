package data_access.movies;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Movie;
import entity.Rating;

/**
 * Utility class to convert between JSON representation of a movie as returned by
 * The Movie Database, and a Movie entity representation as used by the program.
 */
public final class MovieJSONFormat {
    private static final String GENRES = "genres";
    private static final String RUNTIME = "runtime";
    private static final int CAST_LIMIT = 5;

    private MovieJSONFormat() {
        throw new IllegalStateException("Utility class.");
    }

    /**
     * Return a Movie entity representation of the provided data.
     * @param movieDetails movie details json response as returned by The Movie Database
     * @param movieCast movie cast json response as returned by The Movie Database
     * @return a Movie entity
     */
    public static Movie parseMovie(JSONObject movieDetails, JSONObject movieCast) {
        final double ratingNormalized = movieDetails.getDouble("vote_average") * 10;

        final String name = movieDetails.getString("title");
        final List<String> genres = parseGenres(movieDetails);
        final Rating externalRating = new Rating((int) ratingNormalized);
        final Rating userRating = new Rating(-1);
        final String description = movieDetails.getString("overview");
        final List<String> castMembers = parseCast(movieCast);
        final int minuteRunTime = movieDetails.getInt(RUNTIME);
        return new Movie(name, genres, userRating, externalRating, description, castMembers, minuteRunTime);
    }

    /**
     * Return the genres of a movie in the given JSON data.
     * @param movieData JSON information from which to retrieve the genres
     * @return list of genres
     */
    public static List<String> parseGenres(JSONObject movieData) {
        final List<String> result = new ArrayList<>();
        final JSONArray genres = movieData.getJSONArray(GENRES);
        for (int i = 0; i < genres.length(); i++) {
            result.add(genres.getJSONObject(i).getString("name"));
        }
        return result;
    }

    /**
     * Return the (Acting) cast members of a movie in the given JSON data.
     * @param castData movie cast json response as returned by The Movie Database
     * @return a list of cast member names
     */
    public static List<String> parseCast(JSONObject castData) {
        final JSONArray cast = castData.getJSONArray("cast");
        final List<String> parsedCast = new ArrayList<>();
        for (int i = 0; i < cast.length() && parsedCast.size() < CAST_LIMIT; i++) {
            if (cast.getJSONObject(i).getString("known_for_department").equals("Acting")) {
                parsedCast.add(cast.getJSONObject(i).getString("name"));
            }
        }
        return parsedCast;
    }

}
