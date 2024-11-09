package data_access;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.note.DataAccessException;
import use_case.recommendation.RecommendationApiInterface;

/**
 * The DAO for getting recommendations from TasteDive.
 * See
 * <a href="https://tastedive.com/read/api">API | TasteDive</a> for details on the API.
 */
public class TasteDiveRecommendation implements RecommendationApiInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String BASE_URL = "https://tastedive.com/api/similar";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String MESSAGE = "message";
    private String apiKey;

    /**
     * Get a recommendation from TasteDive with a list of media names.
     *
     * @param query list of media names
     * @param sourceType the type of media to base recommendations from
     * @param returnType the type of media to recommend
     * @param verbose if the response should contain extra information about the movie
     * @return the list of media recommendations returned by TasteDive API
     * @throws DataAccessException error accessing the API
     */
    public JSONArray getRecommendation(List<String> query,
                                       String sourceType,
                                       String returnType,
                                       int verbose) throws DataAccessException {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final String url = BASE_URL + "?k=" + this.apiKey
                + "&q=" + sourceType + ":" + String.join(",", query)
                + "&type=" + returnType
                + "&verbose=" + verbose;
        final Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                return responseBody.getJSONObject("similar").getJSONArray("results");
            }
            else {
                throw new DataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get a recommendation from TasteDive with a list of media names.
     * {@code verbose} defaults to 1.
     * {@see TasteDiveApiAccess#getRecommendation(String,String,int)}
     *
     * @param query list of media names
     * @param sourceType the type of media to base recommendations from
     * @param returnType the type of media to recommend
     * @return the list of media recommendations returned by TasteDive API
     * @throws DataAccessException error accessing the API
     */
    public JSONArray getRecommendation(List<String> query,
                                       String sourceType,
                                       String returnType) throws DataAccessException {
        return this.getRecommendation(query, sourceType, returnType, 1);
    }

    /**
     * Set api key at runtime.
     * @param apiKey the api key
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Load the api key from the resources/apikey.
     */
    public void loadApiKeyFromFile() {
        try {
            this.apiKey = Files.readString(Paths.get(getClass().getClassLoader().getResource("apikey").toURI()));
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
