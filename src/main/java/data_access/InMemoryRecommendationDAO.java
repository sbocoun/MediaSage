package data_access;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import use_case.generate_recommendations.GenDataAccessInterface;

/**
 * An in-memory implementation of the recommendation API.
 */
public class InMemoryRecommendationDAO implements GenDataAccessInterface {

    // Always returns the same sample recommendation from file.
    @Override
    public JSONArray getRecommendation(List<String> query, String sourceType, String returnType) {
        try {
            final String rawString = Files.readString(Paths.get(getClass().getClassLoader()
                    .getResource("taste-dive-sample-response.json").toURI()));
            return new JSONObject(rawString).getJSONObject("similar").getJSONArray("results");
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void setApiKey(String apiKey) {
        throw new UnsupportedOperationException("Not applicable.");
    }
}
