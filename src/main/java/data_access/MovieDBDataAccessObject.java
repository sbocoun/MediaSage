package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Movie;
import entity.Rating;
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
     *
     * @param movieName The name of the movie to be looked up.
     * @return a JSONObject containing the movie genres, cast, runtime, rating, and description.
     * @throws MovieDBDataAccessException if the TMDB API is unsuccessfully called.
     */
    public Movie getMovie(String movieName) throws MovieDBDataAccessException {
        final int movieID = getMovieID(movieName);
        final JSONObject completeMovieData = getMovieDetails(movieID);
        final double ratingNormalized = completeMovieData.getDouble("vote_average") * 10;

        final String name = completeMovieData.getString("title");
        final List<String> genres = getGenres(completeMovieData);
        final Rating rating = new Rating((int) ratingNormalized);
        final String description = completeMovieData.getString("overview");
        final List<String> castMembers = getMovieCast(movieID);
        final int minuteRunTime = completeMovieData.getInt(RUNTIME);
        return new Movie(name, genres, rating, description, castMembers, minuteRunTime);
    }

    /**
     * Get the genres of a movie in the given JSON data.
     * @param movieData JSON information from which to retrieve the genres
     * @return list of genres
     * @throws JSONException if the JSON information doesn't contain the correct information/format
     */
    private List<String> getGenres(JSONObject movieData) throws JSONException {
        final List<String> result = new ArrayList<>();
        final JSONArray genres = movieData.getJSONArray(GENRES);
        for (int i = 0; i < genres.length(); i++) {
            result.add(genres.getJSONObject(i).getString("name"));
        }
        return result;
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
                return responseBody;
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
    private List<String> getMovieCast(int movieID) throws MovieDBDataAccessException {

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
                final List<String> parsedCast = new ArrayList<>();
                for (int i = 0; i < cast.length() && parsedCast.size() < CAST_LIMIT; i++) {
                    if (cast.getJSONObject(i).getString("known_for_department").equals("Acting")) {
                        parsedCast.add(cast.getJSONObject(i).getString("name"));
                    }
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
     * Load the api key to be used for api calls.
     *
     * @param apikey the TMDB API key to be used
     */
    public void setApiKey(String apikey) {
        this.apiKey = apikey;
    }
}
