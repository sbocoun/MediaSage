package view;

import interface_adapter.list.ListState;
import interface_adapter.list.ListViewModel;
import interface_adapter.filter_list.FilterViewModel;
import junit.framework.TestCase;
import use_case.list.moveMedia.MoveController;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Method;
import java.util.List;

public class ListViewMoveButtonTest extends TestCase {
    private ListView listView;
    private MockMoveController mockMoveController;
    private MockListViewModel mockListViewModel;
    private FilterViewModel mockFilterViewModel;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mockMoveController = new MockMoveController();
        mockListViewModel = new MockListViewModel();
        mockFilterViewModel = new FilterViewModel();

        listView = new ListView(mockListViewModel, mockFilterViewModel);
        // Set up mock move controller
        listView.setMoveController(mockMoveController);

        // Populate table data
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][] {
                        {"The Matrix", "A computer hacker learns about the true nature of reality."},
                        {"The Blues Brothers", "Two brothers on a mission from God to save their orphanage."}
                },
                new Object[] {"Name", "Description"}
        );
        listView.getMediaListTable().setModel(tableModel);

        // Set up mock collections
        mockListViewModel.setAvailableCollections(List.of("Movie Watchlist", "Favorite Movies"));
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
        Method method = ListView.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(listView);
    }

    private void setMediaCollectionSelectorSelectedItem(String item) throws Exception {
        var field = ListView.class.getDeclaredField("mediaCollectionSelector");
        field.setAccessible(true);
        javax.swing.JComboBox<String> selector = (javax.swing.JComboBox<String>) field.get(listView);
        selector.setSelectedItem(item);
    }

    private static class MockMoveController extends MoveController {
        public String movedMovie = null;
        public String sourceCollectionName = null;
        public String targetCollectionName = null;

        public MockMoveController() {
            super(null); // Pass null if not needed for mock
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
}
