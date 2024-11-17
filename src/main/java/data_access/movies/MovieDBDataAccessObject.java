package data_access.movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
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
     * Get the ID of a movie from TMDB.
     * @param movieName The name of the movie to be looked up.
     * @return TMDB ID of the corresponding movie.
     * @throws MovieDBDataAccessException if TMDB API is unsuccessfully called.
     */
    private int getMovieID(String movieName) throws MovieDBDataAccessException {
        final Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=" + movieName
                        + "&include_adult=true&language=en-US&page=1&api_key=" + apiKey)
                .get()
                .build();
        final JSONObject responseBody = getExternalData(request);
        return responseBody.getJSONArray("results").getJSONObject(0).getInt("id");
    }

    /**
     * Get the details of a movie from TMDB.
     * @param movieID The ID of the movie to be looked up.
     * @return a JSONObject containing the movie's genres (in a JSONArray),
     *      its runtime (in minutes), a description, a rating (out of 10.0).
     * @throws MovieDBDataAccessException if TMDB API is unsuccessfully called.
     */
    private JSONObject getMovieDetails(int movieID) throws MovieDBDataAccessException {
        final Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieID + "?language=en-US&api_key=" + apiKey)
                .get()
                .addHeader(ACCEPT, CONTENT_TYPE)
                .addHeader(AUTHORIZATION, BEARER + apiKey)
                .build();
        return getExternalData(request);
    }

    /**
     * Get the cast of a movie from TMDB.
     * @param movieID The ID of the movie to be looked up.
     * @return the JSON response as returned by The Movie Database.
     * @throws MovieDBDataAccessException if TMDB API is unsuccessfully called.
     */
    private JSONObject getMovieCast(int movieID) throws MovieDBDataAccessException {
        final Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieID + "/credits?language=en-US&api_key=" + apiKey)
                .get()
                .addHeader(ACCEPT, CONTENT_TYPE)
                .addHeader(AUTHORIZATION, BEARER + apiKey)
                .build();

        return getExternalData(request);
    }

    /**
     * A call to TMDB API.
     *
     * @param request the request for TMDB
     * @return the JSON response from TMDB
     * @throws MovieDBDataAccessException the error provided by the API
     */
    @NotNull
    private JSONObject getExternalData(Request request) throws MovieDBDataAccessException {
        final OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            final Request sanitizedRequest = new Request.Builder(request).removeHeader(AUTHORIZATION).build();
            if (response.body() == null) {
                final String error = "Got a null response while calling TMDB API. "
                        + "Request without authorization: " + sanitizedRequest;
                throw new MovieDBDataAccessException(error);
            }
            final JSONObject responseBody = new JSONObject(response.body().string());
            if (response.isSuccessful()) {
                return responseBody;
            }
            else {
                throw new MovieDBDataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException ex) {
            throw new MovieDBDataAccessException("IOException occurred while calling TMDB API: " + ex.getMessage());
        }
    }

    /**
     * Load the api key to be used for api calls.
     *
     * @param apikey TMDB API key to be used
     */
    public void setApiKey(String apikey) {
        this.apiKey = apikey;
    }
}
