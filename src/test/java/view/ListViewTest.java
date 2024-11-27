package view;

import interface_adapter.list.ListController;
import interface_adapter.list.ListViewModel;
import interface_adapter.list.ListState;

import javax.swing.table.DefaultTableModel;

import junit.framework.TestCase;

public class ListViewTest extends TestCase {
    private ListView listView;
    private MockListController mockListController;
    private MockListViewModel mockListViewModel;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create mock implementations of ListController and ListViewModel
        mockListController = new MockListController();
        mockListViewModel = new MockListViewModel();

        // Initialize ListView with mocks
        listView = new ListView(mockListViewModel);
        listView.setListController(mockListController);

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
        assertEquals("The Matrix", mockListController.removedMovie);
        assertEquals("Movie Watchlist", mockListController.collectionName);
    }

    public void testRemoveSelectedMovieNoRowSelected() {
        // no row selected
        listView.getMediaListTable().clearSelection();
        listView.removeSelectedMovie();

        // Verify that no movie was removed
        assertNull(mockListController.removedMovie);
        assertNull(mockListController.collectionName);
    }

    public void testRemoveSelectedMovie_NullCollectionName() {
        // Simulate a null collection name
        mockListViewModel.setCurrentCollectionName(null);

        // Call removeSelectedMovie without selecting a row
        listView.getMediaListTable().setRowSelectionInterval(0, 0);
        listView.removeSelectedMovie();

        // Verify that no movie was removed
        assertNull("No movie should be removed when collection name is null", mockListController.removedMovie);
        assertNull("No collection should be processed when collection name is null", mockListController.collectionName);
    }

    // Mock classes to simulate ListController and ListViewModel behavior
    private static class MockListController extends ListController {
        public String removedMovie = null;
        public String collectionName = null;

        public MockListController() {
            super(null, null); // Pass null since we won't use interactors in this mock
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
