package view;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * View for searching media with various filters.
 */
public class SearchView extends JPanel implements PropertyChangeListener {
    private static final int MARGIN_SIZE = 5;
    private final String viewName = "search";
    private final SearchViewModel searchViewModel;
    private final SearchController searchController;

    private final JComboBox<String> categoryDropdown;
    private final JTextField keywordField = new JTextField(20);
    private final JTextField genreField = new JTextField(20);
    private final JTextField castField = new JTextField(20);
    private final JButton keywordSearchButton = new JButton("Search by Keyword");
    private final JButton genreAddButton = new JButton("Add Genre");
    private final JButton castAddButton = new JButton("Add Cast");
    private final JButton mainSearchButton = new JButton("Search");

    private final JTextArea resultsArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel("");

    // Panel to display current query
    private final JPanel queryPanel = new JPanel();
    // List to store query filters
    private final List<String> queryFilters = new ArrayList<>();

    public SearchView(SearchViewModel searchViewModel, SearchController searchController) {
        this.searchViewModel = searchViewModel;
        this.searchController = searchController;
        this.searchViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Category selection panel
        final JPanel categoryPanel = new JPanel(new FlowLayout());
        categoryPanel.add(new JLabel("Category:"));
        categoryDropdown = new JComboBox<>(new String[]{"Movie", "TV Show"});
        categoryPanel.add(categoryDropdown);
        add(categoryPanel);

        // Keyword search panel
        final JPanel keywordPanel = new JPanel(new FlowLayout());
        keywordPanel.add(new JLabel("Keyword:"));
        keywordPanel.add(keywordField);
        keywordPanel.add(keywordSearchButton);
        add(keywordPanel);

        // Genre filter panel
        final JPanel genrePanel = new JPanel(new FlowLayout());
        genrePanel.add(new JLabel("Genre:"));
        genrePanel.add(genreField);
        genrePanel.add(genreAddButton);
        add(genrePanel);

        // Cast filter panel
        final JPanel castPanel = new JPanel(new FlowLayout());
        castPanel.add(new JLabel("Cast:"));
        castPanel.add(castField);
        castPanel.add(castAddButton);
        add(castPanel);

        // Query display panel
        final JPanel queryDisplayPanel = new JPanel();
        queryDisplayPanel.setLayout(new BoxLayout(queryDisplayPanel, BoxLayout.Y_AXIS));
        queryDisplayPanel.add(new JLabel("Query:"));
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        queryDisplayPanel.add(queryPanel);
        add(queryDisplayPanel);

        // Results area
        resultsArea.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane);

        // Error label
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        // Main search button
        add(mainSearchButton);

        // Action listeners for buttons
        genreAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchView.this.addFilter("Genre", genreField.getText());
            }
        });
        castAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchView.this.addFilter("Cast", castField.getText());
            }
        });

        // Action listener for keyword search button (search by name)
        keywordSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String keyword = keywordField.getText().trim();

                if (keyword.isEmpty()) {
                    showError("Please enter a movie name to search.");
                }
                else {
                    clearError();
                    searchController.execute(keyword);
                }
            }
        });

        // Action listener for main search button
        mainSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String keyword = keywordField.getText().trim();
                final List<String> genres = new ArrayList<>();
                if (!genreField.getText().trim().isEmpty()) {
                    genres.add(genreField.getText().trim());
                }
                final List<String> cast = new ArrayList<>();
                if (!castField.getText().trim().isEmpty()) {
                    cast.add(castField.getText().trim());
                }

                if (keyword.isEmpty() && genres.isEmpty() && cast.isEmpty()) {
                    showError("At least one filter (Keyword, Genre, Cast) is required.");
                }
                else {
                    clearError();
                    searchViewModel.performSearch(keyword, genres, cast);
                }
            }
        });
    }

    /**
     * Adds a filter to the query and updates the query panel.
     *
     * @param type  the type of filter (e.g., Genre, Cast)
     * @param value the value of the filter
     */
    private void addFilter(String type, String value) {
        if (value.isEmpty()) {
            showError(type + " filter cannot be empty.");
        }
        else {
            clearError();
            queryFilters.add(type + ": " + value);
            final JLabel filterLabel = new JLabel(type + ": " + value);
            filterLabel.setOpaque(true);
            filterLabel.setBackground(Color.LIGHT_GRAY);
            filterLabel.setBorder(BorderFactory.createEmptyBorder(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE));
            queryPanel.add(filterLabel);
            queryPanel.revalidate();
            queryPanel.repaint();
        }
    }

    /**
     * Reacts to changes in SearchViewModel's properties.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("searchResults".equals(evt.getPropertyName())) {
            updateResultsDisplay((String) evt.getNewValue());
        }
        else if ("errorMessage".equals(evt.getPropertyName())) {
            showError((String) evt.getNewValue());
        }
    }

    /**
     * Updates the results display area.
     *
     * @param results the search results to display
     */
    private void updateResultsDisplay(String results) {
        resultsArea.setText(results);
    }

    /**
     * Displays an error message.
     *
     * @param errorMessage the error message to display
     */
    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    /**
     * Clears the error message.
     */
    private void clearError() {
        errorLabel.setText("");
    }

    public String getViewName() {
        return viewName;
    }
}
