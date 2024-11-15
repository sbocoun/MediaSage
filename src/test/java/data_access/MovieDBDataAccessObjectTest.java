package data_access;

import app.Configurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

import static org.junit.Assert.*;

public class MovieDBDataAccessObjectTest {

    private static JSONObject movieDetails;
    public static final String MOVIE_NAME = "The Matrix";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MovieDBDataAccessInterface api = new MovieDBDataAccessObject();
        Configurator configurator = new Configurator();
        api.setApiKey(configurator.getTmdbApiKey());
        // Filename should be TMDB_apikey
        movieDetails = api.getCompleteMovieData(MOVIE_NAME);
    }

    @Test
    public void testHasRuntime() {
        assertEquals(136, movieDetails.getInt("runtime"));
    }

    @Test
    public void testHasRating() {
        assertEquals(8.2, movieDetails.getDouble("rating"), 1.0);
    }

    @Test
    public void testHasDescription() {
        assertEquals("Set in the 22nd century, The Matrix tells " +
                "the story of a computer hacker who joins a group of underground insurgents fighting the vast and " +
                "powerful computers who now rule the earth.", movieDetails.getString("description"));
    }

    @Test
    public void testHasCast() {
            JSONArray cast = new JSONArray(new String[] {"Keanu Reeves","Laurence Fishburne","Carrie-Anne Moss",
                    "Hugo Weaving", "Gloria Foster"});
            assertTrue(cast.similar(movieDetails.getJSONArray("cast")));
        }

    @Test
    public void testHasGenres() {
        JSONArray genres = new JSONArray(new String[] {"Action", "Science Fiction"});
        assertTrue(genres.similar(movieDetails.getJSONArray("genres")));
    }
}
