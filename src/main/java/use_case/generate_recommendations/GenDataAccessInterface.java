package use_case.generate_recommendations;

import java.util.List;

import org.json.JSONArray;

/**
 * Interface for the recommendation API object. It consists of methods for
 * getting a recommendation, and setting/loading an api key.
 */
public interface GenDataAccessInterface {
    /**
     * Get a recommendation with a list of media names.
     *
     * @param query list of media names
     * @param sourceType the type of media to base recommendations from
     * @param returnType the type of media to recommend
     * @return the list of media recommendations
     * @throws GenDataAccessException error accessing the API
     */
    JSONArray getRecommendation(List<String> query,
                                String sourceType,
                                String returnType) throws GenDataAccessException;

    /**
     * Set api key at runtime.
     * @param apiKey the api key
     */
    void setApiKey(String apiKey);
}
