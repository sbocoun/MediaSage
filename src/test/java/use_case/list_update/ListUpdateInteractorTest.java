package use_case.list_update;

import data_access.grade_api.GradeDataAccessException;
import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.Movie;
import entity.Rating;
import entity.User;
import interface_adapter.list_update.ListUpdatePresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListUpdateInteractorTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private ListUpdatePresenter listUpdatePresenter;

    @InjectMocks
    private ListUpdateInteractor listUpdateInteractor;
    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        // a default logged-in user with a movie collection with a single movie
        User user = new User("test", "test");

        user.setMediaCollections(setupMediaCollections());
        when(userRepo.getCurrentUser()).thenReturn(user);
        when(userRepo.getCurrentUser()).thenReturn(user);

    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testUpdateRating() {
        ListUpdateInputData inputData = new ListUpdateInputData();
        inputData.setMediaName("test movie");
        inputData.setCollectionName("Collection 1");
        inputData.setNewRating(95);

        listUpdateInteractor.executeRatingUpdate(inputData);

        ArgumentCaptor<ListUpdateOutputData> captor = ArgumentCaptor.forClass(ListUpdateOutputData.class);
        verify(listUpdatePresenter).prepareSuccessView(captor.capture());
        ListUpdateOutputData outputData = captor.getValue();
        assertEquals("Changes saved successfully.", outputData.getSuccessMessage());
    }

    @Test
    void testNotLoggedIn () {
        ListUpdateInputData inputData = new ListUpdateInputData();
        inputData.setMediaName("test movie");
        inputData.setCollectionName("Collection 1");
        inputData.setNewRating(95);

        when(userRepo.getCurrentUser()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> listUpdateInteractor.executeRatingUpdate(inputData));
    }

    @Test
    void testWrongMediaCollectionNameProvided() {
        ListUpdateInputData inputData = new ListUpdateInputData();
        inputData.setMediaName("test movie");
        inputData.setCollectionName("non-existent collection");
        inputData.setNewRating(95);

        assertThrows(RuntimeException.class, () -> listUpdateInteractor.executeRatingUpdate(inputData));
    }

    @Test
    void testWrongMediaNameProvided() {
        ListUpdateInputData inputData = new ListUpdateInputData();
        inputData.setMediaName("non-existent movie");
        inputData.setCollectionName("Collection 1");
        inputData.setNewRating(95);

        assertThrows(RuntimeException.class, () -> listUpdateInteractor.executeRatingUpdate(inputData));
    }

    @Test
    void testApiAccessException() throws GradeDataAccessException {
        ListUpdateInputData inputData = new ListUpdateInputData();
        inputData.setMediaName("test movie");
        inputData.setCollectionName("Collection 1");
        inputData.setNewRating(95);

        when(userRepo.saveMediaCollections(any())).thenThrow(new GradeDataAccessException("error message"));
        listUpdateInteractor.executeRatingUpdate(inputData);

        ArgumentCaptor<ListUpdateOutputData> captor = ArgumentCaptor.forClass(ListUpdateOutputData.class);
        verify(listUpdatePresenter).prepareFailView(captor.capture());
        ListUpdateOutputData outputData = captor.getValue();
        assertEquals("Error saving changes: error message", outputData.getErrorMessage());
    }

    private List<MediaCollection<? extends AbstractMedia>> setupMediaCollections() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("test movie",
                new ArrayList<>(List.of("Action", "Sci-Fi")),
                new Rating(-1),
                new Rating(87),
                "descriptions",
                new ArrayList<>(List.of("cast 1", "cast 2")),
                136));
        return new ArrayList<>(List.of(
                new MediaCollection<>("Collection 1", "watched", Movie.class, movieList)));
    }

}
