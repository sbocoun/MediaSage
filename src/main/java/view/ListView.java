package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import interface_adapter.filter_list.FilterController;
import interface_adapter.filter_list.FilterViewModel;
import interface_adapter.generate_recommendations.GenController;
import interface_adapter.list.ListController;
import interface_adapter.list.ListState;
import interface_adapter.list.ListTableModel;
import interface_adapter.list.ListViewModel;
import interface_adapter.list.move_media.MoveController;
import interface_adapter.list.remove_media.RemoveController;
import interface_adapter.list_update.ListUpdateController;
import view.filter_panels.FilterPanelManager;
import view.list.update.UserRatingUpdateListener;

/**
 * View for each of the MediaCollections.
 */
public class ListView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final int TABLE_WIDTH = 800;
    private static final int TABLE_HEIGHT = 200;
    private final JComboBox<String> mediaCollectionSelector = new JComboBox<>();
    private final JButton removeButton = new JButton("Remove");
    private final JButton moveToButton = new JButton("Move to");
    private final JButton recommendButton = new JButton("Generate Recommendation");
    private final JLabel statusLabel = new JLabel();
    private final JTextArea recommendBox = new JTextArea();
    private final JTable mediaListTable = new JTable();

    // Use case controllers
    private ListController listController;
    private ListUpdateController listUpdateController;
    private MoveController moveController;
    private RemoveController removeController;
    private GenController genController;
    private FilterController filterController;

    // View Models
    private final ListViewModel listViewModel;
    private final FilterViewModel filterViewModel;

    private boolean isUserAction = true;

    private final FilterPanelManager filterPanelManager;
    private final JButton filterButton = new JButton("Apply Filters");
    private final JButton clearButton = new JButton("Clear Filters");

    public ListView(ListViewModel listViewModel, FilterViewModel filterViewModel) {
        listViewModel.addPropertyChangeListener(this);
        filterViewModel.addPropertyChangeListener(this);
        this.listViewModel = listViewModel;
        this.filterViewModel = filterViewModel;
        filterPanelManager = new FilterPanelManager(filterViewModel);

        setLayout(new BorderLayout());
        final JPanel topPanel = new JPanel(new BorderLayout());

        final JPanel collectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        collectionPanel.add(mediaCollectionSelector);

        topPanel.add(collectionPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        buildFiltersPanel();

        mediaListTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        final JScrollPane scrollPane = new JScrollPane(mediaListTable);
        add(scrollPane, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        buildButtonsPanel(bottomPanel);

        final JScrollPane scrollRecommendBox = new JScrollPane(recommendBox);
        scrollRecommendBox.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT / 2));
        bottomPanel.add(scrollRecommendBox);
        bottomPanel.add(statusLabel);

        add(bottomPanel, BorderLayout.SOUTH);
        buildActionListeners();
    }

    /**
     * Builds the button panels below the media table.
     * @param bottomPanel the panel to add the buttons to
     */
    private void buildButtonsPanel(JPanel bottomPanel) {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(moveToButton);
        buttonPanel.add(recommendButton);
        bottomPanel.add(buttonPanel);
    }

    /**
     * Builds the filter panel on the right side of the view.
     */
    private void buildFiltersPanel() {
        final JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.add(filterPanelManager.getFilterPanelContainer());
        filterPanel.add(filterButton);
        filterPanel.add(clearButton);
        this.add(filterPanel, BorderLayout.EAST);
    }

    /**
     * Builds all the action listeners for buttons in the View.
     */
    private void buildActionListeners() {
        removeButton.addActionListener(this);
        moveToButton.addActionListener(this);
        mediaCollectionSelector.addActionListener(this);
        filterButton.addActionListener(this);
        clearButton.addActionListener(this);

        recommendButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(recommendButton)) {
                        genController.execute(recommendBox.getText());
                    }
                }
        );

        mediaCollectionSelector.addActionListener(
                evt -> {
                    if (evt.getSource().equals(mediaCollectionSelector) && isUserAction) {
                        listController.executeCollectionSwitch((String) mediaCollectionSelector.getSelectedItem());
                    }
                }
        );

        filterButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(filterButton)) {
                        filterController.execute(
                                filterViewModel.getState().getFilterCriteria(),
                                listViewModel.getState().getCurrentMediaType(),
                                listViewModel.getState().getCurrentCollectionName()
                        );
                    }
                }
        );

        clearButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(clearButton)) {
                        // Clears the input of the currently displayed filter panel,
                        // then executes the filter controller with the cleared filter criteria.
                        filterPanelManager.clearFilterPanel();
                        filterController.execute(filterViewModel.getState().getFilterCriteria(),
                                listViewModel.getState().getCurrentMediaType(),
                                listViewModel.getState().getCurrentCollectionName());
                    }
                }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeButton) {
            removeSelectedMovie();
        }
        else if (e.getSource() == moveToButton) {
            moveSelectedMovie();
        }
    }

    /**
     * Removes the selected movie from the list.
     */
    void removeSelectedMovie() {
        final int selectedRow = mediaListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "No movie selected. Please select a movie to remove.",
                    "Selection Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        else {
            final ListState state = listViewModel.getState();
            final String collectionName = state.getCurrentCollectionName();
            if (collectionName == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "The collection name is null. Cannot remove the movie.",
                        "Collection Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            else {
                final Object value = mediaListTable.getValueAt(selectedRow, 0);
                if (value == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The selected movie has no name or is invalid. Cannot remove.",
                            "Media Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    final int confirmation = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to remove \"" + value + "\" from the collection?",
                            "Confirm Removal",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        removeController.removeMovie(collectionName, value.toString());
                        JOptionPane.showMessageDialog(
                                null,
                                "\"" + value + "\" has been successfully removed from the collection.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Movie removal canceled.",
                                "Cancel",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        }
    }

    /**
     * Moves the selected movie to a different list.
     *
     * @throws IllegalStateException if the required components are missing.
     */
    private void moveSelectedMovie() {
        if (mediaListTable == null || listViewModel == null
                || mediaCollectionSelector == null || moveController == null) {
            throw new IllegalStateException("Initialization error: One or more required components are missing.");
        }

        final int selectedRow = mediaListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "No movie selected. Please select a movie to move.",
                    "Selection Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        else {
            final ListState state = listViewModel.getState();
            final String currentCollectionName = state.getCurrentCollectionName();
            final Object movieNameObject = mediaListTable.getValueAt(selectedRow, 0);
            if (movieNameObject == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "The selected movie has no name. Cannot move.",
                        "Media Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            else {
                final String movieName = movieNameObject.toString();
                final List<String> availableCollections = state.getAvailableCollections();
                if (availableCollections.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "No available collections to move the movie to.",
                            "Collection Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    final String targetCollectionName = (String) JOptionPane.showInputDialog(
                            null,
                            "Select a collection to move the movie to:",
                            "Move Movie",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            availableCollections.toArray(),
                            availableCollections.get(0)
                    );
                    if (targetCollectionName == null || targetCollectionName.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No target collection selected. Cannot move.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    else {
                        moveController.moveMovie(currentCollectionName, targetCollectionName, movieName);
                        JOptionPane.showMessageDialog(
                                null,
                                "Successfully moved \"" + movieName + "\" from \""
                                        + currentCollectionName + "\" to \"" + targetCollectionName + "\".",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        }

    }

    /**
     * Sets the recommendation generation controller.
     * @param genController the recommendation generation controller
     */
    public void setGenController(GenController genController) {
        this.genController = genController;
    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    public void setListUpdateController(ListUpdateController listUpdateController) {
        this.listUpdateController = listUpdateController;
    }

    /**
     * Update the view.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == listViewModel) {
            handleListViewModelChange(evt);
        }
        else if (evt.getSource() == filterViewModel) {
            handleFilterViewModelChange(evt);
        }
    }

    /**
     * Handles changes in the filter view model.
     * @param evt the property change event
     */
    private void handleFilterViewModelChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("filtered media")) {
            // Updates the RowFilter to filter out anything not in the filteredMediaNames list
            final TableRowSorter<?> sorter = (TableRowSorter<?>) mediaListTable.getRowSorter();
            final RowFilter<TableModel, Integer> rf = new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                    // Assumes the first column of the table is the name of the media
                    final String name = (String) entry.getValue(0);
                    return filterViewModel.getState().getFilteredMediaNames().contains(name);
                }
            };
            sorter.setRowFilter(rf);
        }
        else if ("error".equals(evt.getPropertyName())) {
            JOptionPane.showMessageDialog(null,
                    filterViewModel.getState().getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleListViewModelChange(PropertyChangeEvent evt) {
        isUserAction = false;
        final ListState state = (ListState) evt.getNewValue();
        if ("logout".equals(evt.getPropertyName())) {
            clearTable();
        }
        else if ("display data".equals(evt.getPropertyName())) {
            repopulateMediaCollectionSelection(state.getAvailableCollections());
            mediaCollectionSelector.setSelectedItem(state.getCurrentCollectionName());
            filterPanelManager.updateFilterPanel(state.getCurrentMediaType());
            filterPanelManager.clearFilterPanel();
            refreshTable(state);
        }
        else if ("recommendation".equals(evt.getPropertyName())) {
            setRecommendationFields(state);
        }
        else if (evt.getPropertyName().contains("update")) {
            updateStatusLabel(evt, state);
        }
        else if ("error".equals(evt.getPropertyName())) {
            if (state.getAvailableCollections() != null) {
                repopulateMediaCollectionSelection(state.getAvailableCollections());
            }
            JOptionPane.showMessageDialog(null,
                    state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        isUserAction = true;
    }

    private void updateStatusLabel(PropertyChangeEvent evt, ListState state) {
        final int delay = 3000;
        if ("update successful".equals(evt.getPropertyName())) {
            setEphemeralLabel(state.getSuccessMessage(), delay);
        }
        else {
            setEphemeralLabel(state.getErrorMessage(), delay);
        }
    }

    /**
     * Sets the status label with message, which disappears after @time in milliseconds.
     *
     * @param message                 the message to display
     * @param messageTimeMilliseconds message will disappear after the set amount of milliseconds
     */
    public void setEphemeralLabel(String message, int messageTimeMilliseconds) {
        statusLabel.setText(message);
        final Timer timer = new Timer(messageTimeMilliseconds, evt -> statusLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Repopulate the list of media collections.
     * @param collectionNames the list of media collection names
     */
    private void repopulateMediaCollectionSelection(List<String> collectionNames) {
        mediaCollectionSelector.removeAllItems();
        for (String name : collectionNames) {
            mediaCollectionSelector.addItem(name);
        }
    }

    /**
     * Repopulate the list of media collections.
     *
     * @param state the new state containing the list of media to use to populate the table
     */
    private void refreshTable(ListState state) {
        final TableModel currentTableModel = mediaListTable.getModel();
        final ListTableModel newTableModel = state.getTableModel();
        if (currentTableModel instanceof ListTableModel castedCurrentTableModel
                && castedCurrentTableModel.getColumnNames().size() == newTableModel.getColumnNames().size()) {
            castedCurrentTableModel.replaceTable(newTableModel);
        }
        else {
            SwingUtilities.invokeLater(() -> {
                // add the anonymously defined RatingUpdateListener here whenever the TableModel gets replaced
                newTableModel.addTableModelListener(new UserRatingUpdateListener(this));
                mediaListTable.setModel(newTableModel);
                // Necessary to update the row sorter in case the number of columns has changed
                // (e.g. when switching between different types of media collections)
                mediaListTable.setRowSorter(new TableRowSorter<>(newTableModel));
                newTableModel.fireTableDataChanged();
            });
        }
    }

    /**
     * Clear the table.
     */
    private void clearTable() {
        SwingUtilities.invokeLater(() -> mediaListTable.setModel(new DefaultTableModel()));
        mediaCollectionSelector.removeAllItems();
    }

    private void setRecommendationFields(ListState state) {
        recommendBox.setText(state.getGeneratedRecommendations());
        recommendBox.updateUI();
    }

    /**
     * Return the JTable used to display media in collections.
     * @return the JTable used to display media in collections
     */
    public JTable getMediaListTable() {
        return mediaListTable;
    }

    /**
     * Return the ListUpdateController used to submit list update information to the interactor.
     * @return the ListUpdateController
     */
    public ListUpdateController getListUpdateController() {
        return listUpdateController;
    }

    /**
     * Return the media collection selection JCombobox used to display the name of the current collection
     * and switch collections.
     * @return the media collection selection JCombobox
     */
    public JComboBox<String> getMediaCollectionSelector() {
        return mediaCollectionSelector;
    }

    /**
     * Return if the List View is modified by user action or as part of the program functions,
     * to avoid triggering Listener loops.
     * False means the modification comes from an automated process.
     * @return if the list view is modified by a user or not
     */
    public boolean getIsUserAction() {
        return isUserAction;
    }

    /**
     * Sets if the List View is modified by user action or as part of the program functions,
     * to avoid triggering Listener loops.
     * False means the List View is being modified.
     * @param isUserAction set to false if the List View is going to be modified by the program
     */
    public void setIsUserAction(boolean isUserAction) {
        this.isUserAction = isUserAction;
    }

    public void setRemoveController(RemoveController removeController) {
        this.removeController = removeController;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }

    public void setFilterController(FilterController filterController) {
        this.filterController = filterController;
    }
}
