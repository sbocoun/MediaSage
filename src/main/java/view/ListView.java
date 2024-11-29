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
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import interface_adapter.generate_recommendations.GenController;
import interface_adapter.list.ListController;
import interface_adapter.list.ListState;
import interface_adapter.list.ListTableModel;
import interface_adapter.list.ListViewModel;
import interface_adapter.list_update.ListUpdateController;
import view.list.update.UserRatingUpdateListener;

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
    private final JLabel statusLabel = new JLabel();
    private final JTextArea recommendBox = new JTextArea();
    private final JTextField filterField = new JTextField(10);
    private final JTable mediaListTable = new JTable();
    private final List<JRadioButton> radioButtonList = new ArrayList<>();
    private final List<String> movieDescriptions = new ArrayList<>();
    private ListController listController;
    private ListUpdateController listUpdateController;
    private GenController genController;
    private boolean isUserAction = true;

    public ListView(ListViewModel listViewModel) {
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
        scrollRecommendBox.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT / 2));
        bottomPanel.add(scrollRecommendBox);
        bottomPanel.add(statusLabel);

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
    private void removeSelectedMovie() {
        for (int i = 0; i < radioButtonList.size(); i++) {
            if (radioButtonList.get(i).isSelected()) {
                radioButtonList.remove(i);
                movieDescriptions.remove(i);
                mediaListTable.remove(i);
                break;
            }
        }

        revalidate();
        repaint();
    }

    /**
     * Moves selected movie to a different list.
     */
    private void moveSelectedMovie() {
        for (int i = 0; i < radioButtonList.size(); i++) {
            if (radioButtonList.get(i).isSelected()) {
                final String movieName = "Movie " + (i + 1);
                final String selectedList = (String) mediaCollectionSelector.getSelectedItem();
                System.out.println("Moving " + movieName + " to " + selectedList);
                break;
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
        else if (evt.getPropertyName().contains("update")) {
            updateStatusLabel(evt, state);
        }
        else if ("error".equals(evt.getPropertyName())) {
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
}
