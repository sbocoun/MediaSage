package use_case.search;

import data_access.movies.MovieDBDataAccessException;
import entity.Movie;
import entity.Rating;
import org.junit.Before;
import org.junit.Test;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SearchInteractorTest {

    private MovieDBDataAccessInterface movieDBDataAccessMock;
    private SearchInteractor searchInteractor;

    @Before
    public void setup() {
        // Mock the MovieDBDataAccessInterface
        movieDBDataAccessMock = mock(MovieDBDataAccessInterface.class);
        searchInteractor = new SearchInteractor(movieDBDataAccessMock);
    }

    @Test
    public void testExecuteSearchSuccessWithKeyword() throws MovieDBDataAccessException {
        // Setup test data
        String searchKeyword = "Inception";
        List<String> genres = List.of("Sci-Fi");
        List<String> cast = List.of("Leonardo DiCaprio");

        // Create mock movie response with valid Rating objects
        Rating userRating = new Rating(85);  // assuming 85 as the user rating score
        Rating externalRating = new Rating(90);  // assuming 90 as the external rating score
        Movie movie = new Movie("Inception", Arrays.asList("Sci-Fi", "Action"), userRating, externalRating,
                "A mind-bending thriller", cast, 148);

        // Mock the getMovie method to return the mock movie
        when(movieDBDataAccessMock.getMovie(searchKeyword)).thenReturn(movie);

        // Prepare input data
        SearchByCriteriaInputData inputData = new SearchByCriteriaInputData("movie", new ArrayList<>(List.of(searchKeyword)), genres, cast);

        // Execute interactor
        SearchByCriteriaOutputData outputData = searchInteractor.execute(inputData);

        // Verify that movie is in the result
        String expectedDetails = "Title: Inception\n" +
                "Description: A mind-bending thriller\n" +
                "Runtime: 148 minutes\n" +
                "Genres: Sci-Fi, Action\n" +
                "Cast: Leonardo DiCaprio\n" +
                "User Rating: 85\n" +
                "External Rating: 90";
        assertEquals(expectedDetails, outputData.getMovie());
    }

    @Test
    public void testExecuteSearchNoResultsForKeyword() throws MovieDBDataAccessException {
        // Setup test data
        String searchKeyword = "NonExistentMovie";
        List<String> genres = List.of("Action");
        List<String> cast = List.of("Bruce Willis");

        // Mock the getMovie method to throw an exception for non-existent movie
        when(movieDBDataAccessMock.getMovie(searchKeyword)).thenThrow(new MovieDBDataAccessException("Movie not found"));

        // Prepare input data
        SearchByCriteriaInputData inputData = new SearchByCriteriaInputData("movie", List.of(searchKeyword), genres, cast);

        // Execute the interactor and expect an empty movie result (no movie found)
        SearchByCriteriaOutputData outputData = searchInteractor.execute(inputData);

        // Verify that no movie is returned (empty string as movie)
        assertEquals("", outputData.getMovie());
    }

    @Test(expected = MovieDBDataAccessException.class)
    public void testExecuteSearchThrowsException() throws MovieDBDataAccessException {
        // Setup test data
        String searchKeyword = "Inception";
        List<String> genres = List.of("Sci-Fi");
        List<String> cast = List.of("Leonardo DiCaprio");

        // Mock the getMovie method to throw an exception
        when(movieDBDataAccessMock.getMovie(searchKeyword)).thenThrow(new MovieDBDataAccessException("API Error"));

        // Prepare input data
        SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(searchKeyword, Collections.singletonList("movie"), genres, cast);

        // Execute the interactor and expect an exception
        searchInteractor.execute(inputData);
    }
}
