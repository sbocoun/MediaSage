package use_case.filter;

import data_access.grade_api.UserRepository;
import entity.*;
import interface_adapter.filter_list.FilterPresenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import use_case.filter_list.FilterInteractor;
import use_case.filter_list.FilterListInputData;
import use_case.filter_list.FilterListOutputData;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TelevisionStrategyTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FilterPresenter filterPresenter;

    @Mock
    private MediaCollection<AbstractMedia> mediaCollection;

    @InjectMocks
    private FilterInteractor filterInteractor;
    private Television testShow;
    private Television testShow2;

    private AutoCloseable mocks;

    @Captor
    private ArgumentCaptor<FilterListOutputData> captor;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        filterInteractor = new FilterInteractor(filterPresenter, userRepository);
        testShow = new Television("Test Show",
                List.of("Action", "Adventure"),
                new Rating(50),
                new Rating(75),
                "Description",
                List.of("Actor 1", "Actor 2"));
        testShow2 = new Television("Test Show 2",
                List.of("Action", "Sci-Fi"),
                new Rating(50),
                new Rating(75),
                "Addendum",
                List.of("Actor 2", "Actor 3"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close(); // Ensure mocks are closed after each test
        }
    }

   @Test
       void testTelevisionFilterJustActors() {
        testTelevisionStrategy(Map.of("genres", Set.of(""), "actors", Set.of("actor", "1"), "keywords", Set.of("")), List.of("Test Show"));
    }

    @Test
    void testTelevisionFilterJustGenres() {
        testTelevisionStrategy(Map.of("genres", Set.of("adventure"), "actors", Set.of(""), "keywords", Set.of("")), List.of("Test Show"));
    }

    @Test
    void testTelevisionFilterJustKeywords() {
        testTelevisionStrategy(Map.of("genres", Set.of(""), "actors", Set.of(""), "keywords", Set.of("description")), List.of("Test Show"));
    }

    @Test
    void testTelevisionFilterMultipleGenres() {
        testTelevisionStrategy(Map.of("genres", Set.of("action"), "actors", Set.of("actor", "1"), "keywords", Set.of("description")), List.of("Test Show"));
    }

    @Test
    void testTelevisionFilterNoFilters() {
        testTelevisionStrategy(Map.of("genres", Set.of(""), "actors", Set.of(""), "keywords", Set.of("")), List.of("Test Show", "Test Show 2"));
    }

    void testTelevisionStrategy(Map<String, Set<String>> filters, List<String> expectedFilteredMediaNames) {
        // Arrange
        FilterListInputData inputData = new FilterListInputData();
        inputData.setCollectionType("entity.Television");
        inputData.setCollectionName("testCollection");
        inputData.setFilters(filters);

        when(userRepository.getCurrentUsername()).thenReturn("testUser");
        when(userRepository.getNamedCollection("testCollection", "entity.Television")).thenReturn(mediaCollection);
        when(mediaCollection.getMediaList()).thenReturn(List.of(testShow, testShow2));

        // Act
        filterInteractor.execute(inputData);

        // Assert
        verify(filterPresenter).prepareSuccessView(captor.capture());
        FilterListOutputData outputData = captor.getValue();
        assertEquals(expectedFilteredMediaNames, outputData.getFilteredMediaNames());
    }
}
