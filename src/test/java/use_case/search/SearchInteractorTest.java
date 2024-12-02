package use_case.search;

import data_access.movies.MovieDBDataAccessException;
import entity.Movie;
import entity.Rating;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SearchInteractorTest {

    @Mock
    private MovieDBDataAccessInterface movieDBDataAccessMock;

    @Mock
    private SearchOutputBoundary searchOutputBoundaryMock;

    private SearchInteractor searchInteractor;
    private AutoCloseable mocks;

    @Before
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        searchInteractor = new SearchInteractor(movieDBDataAccessMock, searchOutputBoundaryMock);
    }

    @Test
    public void testExecute_SuccessfulSearch() throws MovieDBDataAccessException {
        String keyword = "Inception";
        Movie mockMovie = new Movie(
                "Inception",
                List.of("Sci-Fi", "Action"),
                new Rating(85),
                new Rating(90),
                "A mind-bending thriller",
                List.of("Leonardo DiCaprio"),
                148
        );
        when(movieDBDataAccessMock.getMovie(keyword)).thenReturn(mockMovie);

        SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(
                "movie",
                List.of(keyword),
                List.of(),
                List.of()
        );

        searchInteractor.execute(inputData);

        // Assert
        ArgumentCaptor<SearchByCriteriaOutputData> outputCaptor = ArgumentCaptor.forClass(SearchByCriteriaOutputData.class);
        verify(searchOutputBoundaryMock).displaySearchResults(outputCaptor.capture());
        SearchByCriteriaOutputData outputData = outputCaptor.getValue();

        assertEquals(mockMovie.toString(), outputData.getSearchResults());
    }

    @Test
    public void testExecute_EmptyKeyword() {
        SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(
                "movie",
                List.of(),
                List.of(),
                List.of()
        );

        searchInteractor.execute(inputData);

        // Assert
        ArgumentCaptor<SearchByCriteriaOutputData> outputCaptor = ArgumentCaptor.forClass(SearchByCriteriaOutputData.class);
        verify(searchOutputBoundaryMock).displaySearchResults(outputCaptor.capture());
        SearchByCriteriaOutputData outputData = outputCaptor.getValue();

        assertEquals("", outputData.getSearchResults());
    }

    @Test
    public void testExecute_ExceptionHandling() throws MovieDBDataAccessException {
        String keyword = "NonExistentMovie";
        when(movieDBDataAccessMock.getMovie(keyword)).thenThrow(new MovieDBDataAccessException("Movie not found"));

        SearchByCriteriaInputData inputData = new SearchByCriteriaInputData(
                "movie",
                List.of(keyword),
                List.of(),
                List.of()
        );

        searchInteractor.execute(inputData);

        // Assert
        ArgumentCaptor<SearchByCriteriaOutputData> outputCaptor = ArgumentCaptor.forClass(SearchByCriteriaOutputData.class);
        verify(searchOutputBoundaryMock).displaySearchResults(outputCaptor.capture());
        SearchByCriteriaOutputData outputData = outputCaptor.getValue();

        assertEquals("Error: Could not retrieve movie details.", outputData.getSearchResults());
    }
}
