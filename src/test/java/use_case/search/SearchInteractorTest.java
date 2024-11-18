package use_case.search;

import entity.AbstractMedia;
import entity.Movie;
import entity.Rating;
import org.junit.Test;
import use_case.note.DataAccessException;
import use_case.search.SearchDataAccessInterface;
import use_case.search.SearchInputData;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SearchInteractorTest {

    @Test
    public void testExecuteSearchSuccess() {
        // Mock DAO implementation with a simple Map for data storage
        SearchDataAccessInterface searchDAO = new SearchDataAccessInterface() {

            private final Map<String, AbstractMedia> mediaStore = new HashMap<>();

            {
                // Prepopulate the mock DAO with some test data
                addMedia(new Movie("Inception", List.of("Sci-Fi", "Thriller"), new Rating(90),
                        "A mind-bending thriller", List.of("Leonardo DiCaprio"), 148));
            }

            @Override
            public AbstractMedia searchByName(String name) {
                return mediaStore.get(name);
            }

            @Override
            public void addMedia(AbstractMedia media) {
                mediaStore.put(media.getName(), media);
            }
        };

        SearchOutputBoundary outputBoundary = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                assertEquals("Inception", outputData.getMedia().getName());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail(errorMessage);
            }
        };

        SearchInteractor interactor = new SearchInteractor(searchDAO, outputBoundary);
        SearchInputData inputData = new SearchInputData("Inception");

        interactor.execute(inputData);
    }

    @Test
    public void testExecuteSearchNoResults() {
        // Mock DAO implementation with no matching data
        SearchDataAccessInterface searchDAO = new SearchDataAccessInterface() {

            private final Map<String, AbstractMedia> mediaStore = new HashMap<>();

            @Override
            public AbstractMedia searchByName(String name) {
                return mediaStore.get(name);
            }

            @Override
            public void addMedia(AbstractMedia media) {
                mediaStore.put(media.getName(), media);
            }
        };

        SearchOutputBoundary outputBoundary = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                fail("Expected failure but got success view");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No results found for: Unknown Movie", errorMessage);
            }
        };

        SearchInteractor interactor = new SearchInteractor(searchDAO, outputBoundary);
        SearchInputData inputData = new SearchInputData("Unknown Movie");

        interactor.execute(inputData);
    }

    @Test
    public void testExecuteSearchDataAccessException() {
        // Mock DAO implementation that throws a DataAccessException
        SearchDataAccessInterface searchDAO = new SearchDataAccessInterface() {

            @Override
            public AbstractMedia searchByName(String name) throws DataAccessException {
                throw new DataAccessException("Database error");
            }

            @Override
            public void addMedia(AbstractMedia media) {
                // Do nothing
            }
        };

        SearchOutputBoundary outputBoundary = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                fail("Expected failure but got success view");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("An error occurred during the search: Database error", errorMessage);
            }
        };

        SearchInteractor interactor = new SearchInteractor(searchDAO, outputBoundary);
        SearchInputData inputData = new SearchInputData("Some Movie");

        interactor.execute(inputData);
    }
}