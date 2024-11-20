package data_access;

import java.util.ArrayList;
import java.util.List;

import app.Configurator;
import data_access.movies.MovieDBDataAccessObject;
import entity.Movie;
import org.junit.BeforeClass;
import org.junit.Test;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

import static org.junit.Assert.*;

public class MovieDBDataAccessObjectTest {

    private static Movie movieDetails;
    public static final String MOVIE_NAME = "The Matrix";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MovieDBDataAccessInterface api = new MovieDBDataAccessObject();
        Configurator configurator = new Configurator();
        api.setApiKey(configurator.getTmdbApiKey());
        // Filename should be TMDB_apikey
        movieDetails = api.getMovie(MOVIE_NAME);
    }

    @Test
    public void testHasRuntime() {
        assertEquals(136, movieDetails.getMinuteRuntime());
    }

    @Test
    public void testHasRating() {
        assertEquals(82, movieDetails.getExternalRating().getScore());
    }

    @Test
    public void testHasDescription() {
        assertEquals("Set in the 22nd century, The Matrix tells " +
                "the story of a computer hacker who joins a group of underground insurgents fighting the vast and " +
                "powerful computers who now rule the earth.", movieDetails.getDescription());
    }

    @Test
    public void testHasCast() {
        List<String> cast = new ArrayList<>(List.of(new String[]{"Keanu Reeves", "Laurence Fishburne",
                "Carrie-Anne Moss", "Hugo Weaving", "Gloria Foster"}));
        assertEquals(cast, movieDetails.getCastMembers());
        }

    @Test
    public void testHasGenres() {
        List<String> genres = new ArrayList<>(List.of(new String[]{"Action", "Science Fiction"}));
        assertEquals(genres, movieDetails.getGenres());
    }
}
