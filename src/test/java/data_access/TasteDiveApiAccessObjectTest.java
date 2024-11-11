package data_access;

import org.json.JSONArray;
import org.junit.Test;
import use_case.note.DataAccessException;
import use_case.recommendation.RecommendationApiInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TasteDiveApiAccessObjectTest {

    @Test
    public void testApi() {
        RecommendationApiInterface api = new TasteDiveRecommendation();
        api.loadApiKeyFromFile();
        List<String> base = new ArrayList<>();
        base.add("Alien Romulus");
        base.add("late night with the devil");
        try {
            api.getRecommendation(base, "movie", "movie");
        } catch (DataAccessException ex) {
            fail(ex.getMessage());
        }
    }
}
