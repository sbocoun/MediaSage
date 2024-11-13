package data_access;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

/**
 * The DAO for the TMDB, responsible for getting information about movies and shows.
 * See
 *  * <a href= "https://developer.themoviedb.org/docs/getting-started">
 *  the documentation</a>
 * for more details.
 */
public class MovieDBDataAccessObject implements MovieDBDataAccessInterface {

    private static final String MESSAGE = "message";
    private static final String GENRES = "genres";
    private static final String RUNTIME = "runtime";
    private static final int CAST_LIMIT = 5;
    private static final String ACCEPT = "accept";
    private static final String CONTENT_TYPE = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private String apiKey;

    /**
     * Get the complete details of a movie from the TMDB.
     * @param movieName The name of the movie to be looked up.
     * @return a JSONObject containing the movie genres, cast, runtime, rating, and description.
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     */
    public JSONObject getCompleteMovieData(String movieName) throws MovieDBDataAccessException {
        final int movieID = getMovieID(movieName);
        final JSONObject completeMovieData = getMovieDetails(movieID);
        completeMovieData.put("cast", getMovieCast(movieID));
        return completeMovieData;
    }

    /**
     * Get the ID of a movie from the TMDB.
     * @param movieName The name of the movie to be looked up.
     * @return the TMDB ID of the corresponding movie.
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     * @throws RuntimeException if there's an error formatting the JSON output.
     */
    private int getMovieID(String movieName) throws MovieDBDataAccessException {

        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=" + movieName
                        + "&include_adult=true&language=en-US&page=1&api_key=" + apiKey)
                .get()
                .build();

        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.isSuccessful()) {
                return responseBody.getJSONArray("results").getJSONObject(0).getInt("id");
            }
            else {
                throw new MovieDBDataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the details of a movie from the TMDB.
     * @param movieID The ID of the movie to be looked up.
     * @return a JSONObject containing the movie's genres (in a JSONArray),
     *      its runtime (in minutes), a description, a rating (out of 10.0).
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     * @throws RuntimeException if there's an error formatting the JSON output.
     */
    private JSONObject getMovieDetails(int movieID) throws MovieDBDataAccessException {

        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieID + "?language=en-US&api_key=" + apiKey)
                .get()
                .addHeader(ACCEPT, CONTENT_TYPE)
                .addHeader(AUTHORIZATION, BEARER + apiKey)
                .build();

        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.isSuccessful()) {
                final JSONObject criticalMovieDetails = new JSONObject();
                final JSONArray genres = responseBody.getJSONArray(GENRES);
                final JSONArray parsedGenres = new JSONArray();
                for (int i = 0; i < genres.length(); i++) {
                    parsedGenres.put(genres.getJSONObject(i).getString("name"));
                }
                criticalMovieDetails.put(GENRES, parsedGenres);
                criticalMovieDetails.put("rating", responseBody.getDouble("vote_average"));
                criticalMovieDetails.put("description", responseBody.getString("overview"));
                criticalMovieDetails.put(RUNTIME, responseBody.getInt(RUNTIME));
                return criticalMovieDetails;
            }
            else {
                throw new MovieDBDataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the cast of a movie from the TMDB.
     * @param movieID The ID of the movie to be looked up.
     * @return a JSONArray containing names of {@value #CAST_LIMIT} of the movie's cast members.
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     * @throws RuntimeException if there's an error formatting the JSON output.
     */
    private JSONArray getMovieCast(int movieID) throws MovieDBDataAccessException {

        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieID + "/credits?language=en-US&api_key=" + apiKey)
                .get()
                .addHeader(ACCEPT, CONTENT_TYPE)
                .addHeader(AUTHORIZATION, BEARER + apiKey)
                .build();

        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.isSuccessful()) {
                final JSONArray cast = responseBody.getJSONArray("cast");
                final JSONArray parsedCast = new JSONArray();
                for (int i = 0; i < CAST_LIMIT; i++) {
                    parsedCast.put(cast.getJSONObject(i).getString("name"));
                }
                return parsedCast;
            }
            else {
                throw new MovieDBDataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Load the api key from the resources/apikey.
     * @throws RuntimeException if there's an error reading the apikey file.
     */
    public void loadApiKeyFromFile() {
        try {
            this.apiKey = Files.readString(Paths.get(getClass().getClassLoader()
                    .getResource("TMDB_apikey").toURI()));
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
