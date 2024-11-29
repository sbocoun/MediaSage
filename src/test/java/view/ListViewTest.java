package view;

import interface_adapter.list.ListController;
import interface_adapter.list.ListViewModel;
import interface_adapter.list.ListState;

import javax.swing.table.DefaultTableModel;

import junit.framework.TestCase;
import use_case.list.removeMedia.RemoveController;

public class ListViewTest extends TestCase {
    private ListView listView;
    private MockRemoveController mockRemoveController;
    private MockListViewModel mockListViewModel;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create mock implementations of ListController and ListViewModel
        mockRemoveController = new MockRemoveController();
        mockListViewModel = new MockListViewModel();

        // Initialize ListView with mocks
        listView = new ListView(mockListViewModel);
        listView.setRemoveController(mockRemoveController);

        // Set up the table with sample data
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][] {
                        {"The Matrix", "A computer hacker learns about the true nature of reality."},
                        {"The Blues Brothers", "Two brothers on a mission from God to save their orphanage."}
                },
                new Object[] {"Name", "Description"}
        );
        listView.getMediaListTable().setModel(tableModel);
    }

    public void testRemoveSelectedMovieSuccess() {
        // Set up mock state
        mockListViewModel.setCurrentCollectionName("Movie Watchlist");

        // Simulate selecting the first row in the table
        listView.getMediaListTable().setRowSelectionInterval(0, 0); // Select "The Matrix"

        // Call removeSelectedMovie
        listView.removeSelectedMovie();

        // Verify that the movie was removed
        assertEquals("The Matrix", mockRemoveController.removedMovie);
        assertEquals("Movie Watchlist", mockRemoveController.collectionName);
    }

    public void testRemoveSelectedMovieNoRowSelected() {
        // no row selected
        listView.getMediaListTable().clearSelection();
        listView.removeSelectedMovie();

        // Verify that no movie was removed
        assertNull(mockRemoveController.removedMovie);
        assertNull(mockRemoveController.collectionName);
    }

    public void testRemoveSelectedMovie_NullCollectionName() {
        // Simulate a null collection name
        mockListViewModel.setCurrentCollectionName(null);

        // Call removeSelectedMovie without selecting a row
        listView.getMediaListTable().setRowSelectionInterval(0, 0);
        listView.removeSelectedMovie();

        // Verify that no movie was removed
        assertNull("No movie should be removed when collection name is null", mockRemoveController.removedMovie);
        assertNull("No collection should be processed when collection name is null", mockRemoveController.collectionName);
    }

    private static class MockRemoveController extends RemoveController {
        public String removedMovie = null;
        public String collectionName = null;

        public MockRemoveController() {
            super(null);
        }

        @Override
        public void removeMovie(String desiredCollectionName, String movieName) {
            this.collectionName = desiredCollectionName;
            this.removedMovie = movieName;
        }
    }


    private static class MockListViewModel extends ListViewModel {
        private String currentCollectionName;

        public void setCurrentCollectionName(String collectionName) {
            this.currentCollectionName = collectionName;
        }

        @Override
        public ListState getState() {
            ListState state = new ListState();
            state.setCurrentCollectionName(currentCollectionName);
            return state;
        }
    }
}