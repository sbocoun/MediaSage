package use_case.list.moveMedia;

import data_access.grade_api.GradeDataAccessException;
import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.Rating;
import entity.User;
import interface_adapter.filter_list.FilterViewModel;
import junit.framework.TestCase;
import interface_adapter.list.ListState;
import interface_adapter.list.ListViewModel;
import interface_adapter.list.move_media.MoveController;
import org.jetbrains.annotations.Nullable;
import use_case.list.ListOutputBoundary;
import use_case.list.ListOutputData;
import view.ListView;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the MoveInteractor.
 */
public class MoveInteractorTest extends TestCase {

    private MoveInteractor moveInteractor;
    private MockMoveController mockMoveController;
    private MockListViewModel mockListViewModel;
    private ListView listView;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create mocks and initialize ListView
        mockMoveController = new MockMoveController();
        mockListViewModel = new MockListViewModel();
        listView = new ListView(mockListViewModel, new FilterViewModel());
        listView.setMoveController(mockMoveController);

        // Populate table data
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                        {"The Matrix", "A computer hacker learns about the true nature of reality."},
                        {"The Blues Brothers", "Two brothers on a mission from God to save their orphanage."}
                },
                new Object[]{"Name", "Description"}
        );
        listView.getMediaListTable().setModel(tableModel);

        // Set up mock collections
        mockListViewModel.setAvailableCollections(List.of("Movie Watchlist", "Favorite Movies"));

        // Mock move interactor
        moveInteractor = new MoveInteractor(new MockUserRepository(), new MockOutputBoundary());
    }

    public void testMoveSelectedMovieSuccess() throws Exception {
        mockListViewModel.setCurrentCollectionName("Movie Watchlist");
        listView.getMediaListTable().setRowSelectionInterval(0, 0); // Select "The Matrix"
        setMediaCollectionSelectorSelectedItem("Favorite Movies");

        invokePrivateMethod("moveSelectedMovie");

        assertEquals("The Matrix", mockMoveController.movedMovie);
        assertEquals("Movie Watchlist", mockMoveController.sourceCollectionName);
        assertEquals("Favorite Movies", mockMoveController.targetCollectionName);
    }

    public void testMoveSelectedMovieNoRowSelected() throws Exception {
        listView.getMediaListTable().clearSelection();
        setMediaCollectionSelectorSelectedItem("Favorite Movies");

        invokePrivateMethod("moveSelectedMovie");

        assertNull(mockMoveController.movedMovie);
        assertNull(mockMoveController.sourceCollectionName);
        assertNull(mockMoveController.targetCollectionName);
    }

    public void testMoveSelectedMovieNoAvailableCollections() throws Exception {
        mockListViewModel.setAvailableCollections(List.of());
        mockListViewModel.setCurrentCollectionName("Movie Watchlist");
        listView.getMediaListTable().setRowSelectionInterval(0, 0); // Select "The Matrix"
        setMediaCollectionSelectorSelectedItem("Favorite Movies");

        invokePrivateMethod("moveSelectedMovie");

        assertNull(mockMoveController.movedMovie);
        assertNull(mockMoveController.sourceCollectionName);
        assertNull(mockMoveController.targetCollectionName);
    }

    private void invokePrivateMethod(String methodName) throws Exception {
        var method = ListView.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(listView);
    }

    private void setMediaCollectionSelectorSelectedItem(String item) throws Exception {
        var field = ListView.class.getDeclaredField("mediaCollectionSelector");
        field.setAccessible(true);
        var selector = (javax.swing.JComboBox<String>) field.get(listView);
        selector.setSelectedItem(item);
    }

    private static class MockMoveController extends MoveController {
        public String movedMovie = null;
        public String sourceCollectionName = null;
        public String targetCollectionName = null;

        public MockMoveController() {
            super(null);
        }

        @Override
        public void moveMovie(String sourceCollectionName, String targetCollectionName, String movieName) {
            this.movedMovie = movieName;
            this.sourceCollectionName = sourceCollectionName;
            this.targetCollectionName = targetCollectionName;
        }
    }

    private static class MockListViewModel extends ListViewModel {
        private String currentCollectionName;
        private List<String> availableCollections;

        public void setCurrentCollectionName(String collectionName) {
            this.currentCollectionName = collectionName;
        }

        public void setAvailableCollections(List<String> collections) {
            this.availableCollections = collections;
        }

        @Override
        public ListState getState() {
            ListState state = new ListState();
            state.setCurrentCollectionName(currentCollectionName);
            state.setAvailableCollections(availableCollections);
            return state;
        }
    }

    private static class MockOutputBoundary implements ListOutputBoundary {

        @Override
        public void prepareSuccessView(ListOutputData outputData) {

        }

        @Override
        public void prepareLogoutView() {

        }

        @Override
        public void prepareFailView(ListOutputData listOutputData) {

        }
    }

    private static class MockUserRepository implements UserRepository {
        private final List<MediaCollection<?>> collections;

        public MockUserRepository() {
            collections = List.of(
                    new MediaCollection<>("Movie Watchlist", "To Watch", TestMedia.class,
                            List.of(new TestMedia("The Matrix"), new TestMedia("The Blues Brothers"))),
                    new MediaCollection<>("Favorite Movies", "Watched", TestMedia.class, new ArrayList<>())
            );
        }

        @Override
        public User get(String username) {
            return new User(username, collections.toString());
        }

        @Override
        public String getCurrentUsername() {
            return "testUser";
        }

        @Override
        public void clearCurrentUser() {

        }

        @Nullable
        @Override
        public User getCurrentUser() {
            return null;
        }

        @Override
        public void changePassword(User user) {

        }

        @Override
        public <T extends AbstractMedia> MediaCollection<T> getNamedCollection(String collectionName, String mediaType) {
            return null;
        }

        @Override
        public List<MediaCollection<? extends AbstractMedia>> saveMediaCollections(List<MediaCollection<?
                extends AbstractMedia>> mediaCollectionsList){
            return List.of();
        }

        @Override
        public List<MediaCollection<? extends AbstractMedia>> loadMediaCollections(){
            return List.of();
        }

        @Override
        public String convertCollectionsListToString(List<MediaCollection<? extends AbstractMedia>> mediaCollectionList) {
            return "";
        }

        @Override
        public List<MediaCollection<? extends AbstractMedia>> convertStringToMediaCollections(String mediaCollectionsString) {
            return List.of();
        }

        @Override
        public boolean existsByName(String username) {
            return false;
        }

        @Override
        public void save(User user) {

        }
    }

    public static class TestMedia extends AbstractMedia {
        public TestMedia(String name) {
            super(name, List.of("Genre"), new Rating(0), null);
        }
    }

}
