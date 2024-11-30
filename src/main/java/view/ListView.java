package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import interface_adapter.generate_recommendations.GenController;
import interface_adapter.list.ListController;
import interface_adapter.list.ListState;
import interface_adapter.list.ListTableModel;
import interface_adapter.list.ListViewModel;
import use_case.list.moveMedia.MoveController;
import use_case.list.removeMedia.RemoveController;

/**
 * View for each of the MediaCollections.
 */
public class ListView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final int TABLE_WIDTH = 500;
    private static final int TABLE_HEIGHT = 200;
    private final JComboBox<String> mediaCollectionSelector = new JComboBox<>();
    private final JButton filterButton = new JButton("Filter");
    private final JButton removeButton = new JButton("Remove");
    private final JButton moveToButton = new JButton("Move to");
    private final JButton recommendButton = new JButton("Generate Recommendation");
    private final JTextArea recommendBox = new JTextArea();
    private final JTextField filterField = new JTextField(10);
    private final JTable mediaListTable = new JTable();
    private final List<JRadioButton> radioButtonList = new ArrayList<>();
    private final List<String> movieDescriptions = new ArrayList<>();
    private ListController listController;
    private MoveController moveController;
    private RemoveController removeController;
    private GenController genController;
    private boolean isUserAction = true;

    private final ListViewModel listViewModel;

    public ListView(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
        listViewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout());
        final JPanel topPanel = new JPanel(new BorderLayout());

        final JPanel collectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        collectionPanel.add(mediaCollectionSelector);

        final JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by description:"));
        filterPanel.add(filterField);
        filterPanel.add(filterButton);

        topPanel.add(collectionPanel, BorderLayout.WEST);
        topPanel.add(filterPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        mediaListTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        final JScrollPane scrollPane = new JScrollPane(mediaListTable);
        add(scrollPane, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        buildButtonsPanel(bottomPanel);

        final JScrollPane scrollRecommendBox = new JScrollPane(recommendBox);
        scrollRecommendBox.setSize(TABLE_WIDTH, TABLE_HEIGHT / 2);
        bottomPanel.add(scrollRecommendBox);

        add(bottomPanel, BorderLayout.SOUTH);

        // Test
        addItem("Movie 1", "Test1");
        addItem("Movie 2", "Test2");
        addItem("Movie 3", "Test3");

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
     * Builds all the action listeners for buttons in the View.
     */
    private void buildActionListeners() {
        filterButton.addActionListener(this);
        removeButton.addActionListener(this);
        moveToButton.addActionListener(this);
        mediaCollectionSelector.addActionListener(this);

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
    }

    /**
     * Adds an item to the list with a thumbnail, name, and description.
     *
     * @param name        Name of the movie
     * @param description Description of the movie
     */
    private void addItem(String name, String description) {
        final JPanel itemPanel = new JPanel(new BorderLayout());

        // select item
        final JRadioButton radioButton = new JRadioButton();
        radioButtonList.add(radioButton);
        itemPanel.add(radioButton, BorderLayout.WEST);

        final JLabel thumbnailLabel = new JLabel("Thumbnail");
        itemPanel.add(thumbnailLabel, BorderLayout.CENTER);

        final JPanel nameDescPanel = new JPanel(new GridLayout(2, 1));
        nameDescPanel.add(new JLabel(name));
        nameDescPanel.add(new JLabel(description));
        itemPanel.add(nameDescPanel, BorderLayout.EAST);

        mediaListTable.add(itemPanel);
        movieDescriptions.add(description);

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == filterButton) {
            final String filterText = filterField.getText().toLowerCase();
            filterMoviesByDescription(filterText);
        }
        else if (e.getSource() == removeButton) {
            removeSelectedMovie();
        }
        else if (e.getSource() == moveToButton) {
            moveSelectedMovie();
        }
    }

    /**
     * Filters the movie list based on the given description text.
     *
     * @param filterText The text to filter descriptions by
     */
    private void filterMoviesByDescription(String filterText) {
        mediaListTable.removeAll();

        for (int i = 0; i < movieDescriptions.size(); i++) {
            final String description = movieDescriptions.get(i).toLowerCase();
            if (description.contains(filterText)) {
                addFilteredItem(i);
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Adds a filtered item to the itemListPanel.
     *
     * @param index The index of the item to add
     */
    private void addFilteredItem(int index) {
        final JPanel itemPanel = new JPanel(new BorderLayout());

        // Use the existing radio button and description data
        final JRadioButton radioButton = radioButtonList.get(index);
        itemPanel.add(radioButton, BorderLayout.WEST);

        final JLabel thumbnailLabel = new JLabel("Thumbnail");
        itemPanel.add(thumbnailLabel, BorderLayout.CENTER);

        final JPanel nameDescPanel = new JPanel(new GridLayout(2, 1));
        nameDescPanel.add(new JLabel("Movie " + (index + 1)));
        nameDescPanel.add(new JLabel(movieDescriptions.get(index)));
        itemPanel.add(nameDescPanel, BorderLayout.EAST);

        mediaListTable.add(itemPanel);
    }

    /**
     * Removes the selected movie from the list.
     */
    void removeSelectedMovie() {
        final int selectedRow = mediaListTable.getSelectedRow();
        if (selectedRow != -1) {
            final ListState state = listViewModel.getState();
            final String collectionName = state.getCurrentCollectionName();

            if (collectionName == null) {
                System.err.println("Collection name is null. Cannot remove movie.");
            }
            else {
                final Object value = mediaListTable.getValueAt(selectedRow, 0);
                if (value != null) {
                    removeController.removeMovie(collectionName, value.toString());
                }
                else {
                    System.err.println("No movie selected");
                }
            }
        }
        else {
            System.err.println("No row selected");
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

    /**
     * Update the view.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        isUserAction = false;
        final ListState state = (ListState) evt.getNewValue();
        if ("logout".equals(evt.getPropertyName())) {
            clearTable();
        }
        else if ("display data".equals(evt.getPropertyName())) {
            repopulateMediaCollectionSelection(state.getAvailableCollections());
            mediaCollectionSelector.setSelectedItem(state.getCurrentCollectionName());
            refreshTable(state);
        }
        else if ("recommendation".equals(evt.getPropertyName())) {
            setRecommendationFields(state);
        }
        isUserAction = true;
    }

    /**
     * Repopulate the list of media collections.
     * @param collectionNames the list of media collection names
     */
    public void repopulateMediaCollectionSelection(List<String> collectionNames) {
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
    public void refreshTable(ListState state) {
        final TableModel currentTableModel = mediaListTable.getModel();
        final ListTableModel newTableModel = state.getTableModel();
        if (currentTableModel instanceof ListTableModel castedCurrentTableModel
                && castedCurrentTableModel.getColumnNames().size() == newTableModel.getColumnNames().size()) {
            castedCurrentTableModel.replaceTable(newTableModel);
        }
        else {
            SwingUtilities.invokeLater(() -> {
                mediaListTable.setModel(newTableModel);
                newTableModel.fireTableDataChanged();
            });
        }
    }

    /**
     * Clear the table.
     */
    public void clearTable() {
        SwingUtilities.invokeLater(() -> mediaListTable.setModel(new DefaultTableModel()));
        mediaCollectionSelector.removeAllItems();
    }

    private void setRecommendationFields(ListState state) {
        recommendBox.setText(state.getGeneratedRecommendations());
    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    public void setRemoveController(RemoveController removeController) {
        this.removeController = removeController;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }

    public JTable getMediaListTable() {
        return mediaListTable;
    }

}
