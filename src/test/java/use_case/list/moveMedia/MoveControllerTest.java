package use_case.list.moveMedia;

import interface_adapter.list.ListState;
import interface_adapter.list.ListViewModel;
import interface_adapter.list.move_media.MoveController;
import junit.framework.TestCase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MoveControllerTest extends TestCase {
    private MockMoveController mockMoveController;
    private MockListViewModel mockListViewModel;
    private MockListView mockListView;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create mock implementations
        mockMoveController = new MockMoveController();
        mockListViewModel = new MockListViewModel();

        // Initialize the MockListView with mocks
        mockListView = new MockListView(mockListViewModel, mockMoveController);

        // Set up the table with sample data
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                        {"The Matrix", "A computer hacker learns about the true nature of reality."},
                        {"The Blues Brothers", "Two brothers on a mission from God to save their orphanage."}
                },
                new Object[]{"Name", "Description"}
        );
        mockListView.mediaListTable.setModel(tableModel);

        // Add items to the mediaCollectionSelector
        mockListView.mediaCollectionSelector.addItem("Watchlist");
        mockListView.mediaCollectionSelector.addItem("Favorites");
        mockListView.mediaCollectionSelector.setSelectedIndex(1); // Set "Favorites" as selected
    }

    public void testMoveMovie_ValidInput() {
        // Arrange
        mockListViewModel.setCurrentCollectionName("Watchlist");

        // Simulate selecting the first row in the table
        mockListView.mediaListTable.setRowSelectionInterval(0, 0); // Select "The Matrix"

        // Act
        mockListView.moveSelectedMovie();

        // Assert
        assertEquals("The Matrix", mockMoveController.movedMovie);
        assertEquals("Watchlist", mockMoveController.sourceCollectionName);
        assertEquals("Favorites", mockMoveController.targetCollectionName);
    }

    public void testMoveMovie_NoRowSelected() {
        // No row selected
        mockListView.mediaListTable.clearSelection();

        // Act
        mockListView.moveSelectedMovie();

        // Assert
        assertNull(mockMoveController.movedMovie);
        assertNull(mockMoveController.sourceCollectionName);
        assertNull(mockMoveController.targetCollectionName);
    }

    public void testMoveMovie_InvalidTargetCollection() {
        // Arrange
        mockListViewModel.setCurrentCollectionName("Favorites");

        // Simulate selecting the first row in the table
        mockListView.mediaListTable.setRowSelectionInterval(0, 0); // Select "The Matrix"

        // Act
        mockListView.moveSelectedMovie();

        // Assert
        assertNull(mockMoveController.movedMovie);
        assertNull(mockMoveController.sourceCollectionName);
        assertNull(mockMoveController.targetCollectionName);
    }

    public void testMoveMovie_NullMovieName() {
        // Arrange
        mockListViewModel.setCurrentCollectionName("Watchlist");

        // Simulate selecting a row with a null movie name
        mockListView.mediaListTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, "A computer hacker learns about the true nature of reality."}
                },
                new Object[]{"Name", "Description"}
        ));
        mockListView.mediaListTable.setRowSelectionInterval(0, 0);

        // Act
        mockListView.moveSelectedMovie();

        // Assert
        assertNull(mockMoveController.movedMovie);
        assertNull(mockMoveController.sourceCollectionName);
        assertNull(mockMoveController.targetCollectionName);
    }

    // Mock implementations
    private static class MockMoveController extends MoveController {
        public String sourceCollectionName;
        public String targetCollectionName;
        public String movedMovie;

        public MockMoveController() {
            super(null); // Pass null since we won't use a real interactor
        }

        @Override
        public void moveMovie(String sourceCollectionName, String targetCollectionName, String movieName) {
            this.sourceCollectionName = sourceCollectionName;
            this.targetCollectionName = targetCollectionName;
            this.movedMovie = movieName;
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

    private static class MockListView {
        private final JTable mediaListTable = new JTable();
        private final JComboBox<String> mediaCollectionSelector = new JComboBox<>();
        private final MockListViewModel listViewModel;
        private final MockMoveController moveController;

        public MockListView(MockListViewModel listViewModel, MockMoveController moveController) {
            this.listViewModel = listViewModel;
            this.moveController = moveController;
        }

        public void moveSelectedMovie() {
            int selectedRow = mediaListTable.getSelectedRow();
            if (selectedRow == -1) {
                System.err.println("No movie selected. Cannot move.");
                return;
            }

            ListState state = listViewModel.getState();
            String currentCollectionName = state.getCurrentCollectionName();
            Object movieNameObject = mediaListTable.getValueAt(selectedRow, 0);

            if (movieNameObject == null) {
                System.err.println("Movie name is null. Cannot move.");
                return;
            }

            String movieName = movieNameObject.toString();
            String targetCollectionName = (String) mediaCollectionSelector.getSelectedItem();

            if (targetCollectionName == null || targetCollectionName.equals(currentCollectionName)) {
                System.err.println("Invalid collection choice. Cannot move.");
                return;
            }

            moveController.moveMovie(currentCollectionName, targetCollectionName, movieName);
        }
    }
}

