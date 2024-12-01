package use_case.filter;

import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.Movie;
import entity.Rating;
import interface_adapter.filter_list.FilterPresenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.filter_list.FilterInteractor;
import use_case.filter_list.FilterListInputData;
import use_case.filter_list.FilterListOutputData;
import use_case.filter_list.filter_strategies.FilterStrategy;
import use_case.filter_list.filter_strategies.MovieFilterStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FilterInteractorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FilterPresenter filterPresenter;

    @Mock
    private MediaCollection<AbstractMedia> mediaCollection;

    @InjectMocks
    private FilterInteractor filterInteractor;

    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this); // Open mocks for this test class
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close(); // Ensure mocks are closed after each test
        }
    }

    // Simple interactor test for demonstration using the MovieFilterStrategy.
    // More detailed tests are found in the corresponding Strategy test.
    @Test
    void testFilterInteractor() {
        // Arrange
        FilterListInputData inputData = new FilterListInputData();
        inputData.setCollectionType("entity.Movie");
        inputData.setCollectionName("testCollection");
        inputData.setFilters(Map.of("genres", Set.of("action"), "actors", Set.of(""), "keywords", Set.of("")));

        when(userRepository.getCurrentUsername()).thenReturn("testUser");
        when(userRepository.getNamedCollection("testCollection", "entity.Movie")).thenReturn(mediaCollection);
        when(mediaCollection.getMediaList()).thenReturn(List.of(
                new Movie("Test Movie",
                        List.of("Action", "Adventure"),
                        new Rating(50),
                        new Rating(75),
                        "Description",
                        List.of(new String[]{"Actor1", "Actor2"}),
                        156)));

        FilterStrategy movieFilterStrategy = mock(MovieFilterStrategy.class);
        when(movieFilterStrategy.meetsCriteria(any(), any())).thenReturn(true);

        // Act
        filterInteractor.execute(inputData);

        // Assert
        ArgumentCaptor<FilterListOutputData> captor = ArgumentCaptor.forClass(FilterListOutputData.class);
        verify(filterPresenter).prepareSuccessView(captor.capture());
        FilterListOutputData outputData = captor.getValue();
        assertEquals(List.of("Test Movie"), outputData.getFilteredMediaNames());
    }

    // Ensures a fail view is generated if a strategy for the corresponding collection type is not found.
    @Test
    void testFilterWhenMediaUnsupported() {
        // Arrange
        FilterListInputData inputData = new FilterListInputData();
        inputData.setCollectionType("unsupported.Type");
        inputData.setCollectionName("testCollection");
        inputData.setFilters(Map.of("genres", Set.of(""), "actors", Set.of(""), "keywords", Set.of("")));

        when(userRepository.getCurrentUsername()).thenReturn("testUser");

        // Act
        filterInteractor.execute(inputData);


        // Assert
        verify(filterPresenter).prepareFailView("Unsupported collection type: unsupported.Type");
    }

    @Test
    void testFilterWhenUserIsVoid() {
        // Arrange
        FilterListInputData inputData = new FilterListInputData();
        inputData.setCollectionType("entity.Movie");
        inputData.setCollectionName("testCollection");
        inputData.setFilters(Collections.emptyMap());

        when(userRepository.getCurrentUsername()).thenReturn("");
        when(userRepository.getNamedCollection("testCollection", "entity.Movie")).thenReturn(null);

        // Act
        filterInteractor.execute(inputData);

        // Assert
        verify(filterPresenter).prepareFailView("User is not logged in.");
    }
}