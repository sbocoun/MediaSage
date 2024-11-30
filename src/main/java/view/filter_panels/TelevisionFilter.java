package view.filter_panels;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.filter_list.FilterState;
import interface_adapter.filter_list.FilterViewModel;

/**
 * Panel for filtering television shows.
 */
public class TelevisionFilter extends JPanel implements Filter {

    private static final String KEYWORDS = "keywords";
    private static final String GENRES = "genres";
    private static final String ACTORS = "actors";
    // Text fields for the filter criteria. Set to empty by default to avoid null values.
    private final JTextField keywordField = new JTextField("");
    private final JTextField genreField = new JTextField("");
    private final JTextField actorField = new JTextField("");
    private final FilterViewModel filterViewModel;

    public TelevisionFilter(FilterViewModel filterViewModel) {
        this.filterViewModel = filterViewModel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createPanelHelper("Keywords", keywordField);
        createPanelHelper("Genres", genreField);
        createPanelHelper("Actors", actorField);

        addListener(KEYWORDS, keywordField);
        addListener(GENRES, genreField);
        addListener(ACTORS, actorField);
    }

    private void createPanelHelper(String labelText, Component component) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(component);
        this.add(panel);
    }

    private void addListener(String criteriaName, JTextField field) {
        // Ensures the relevant field in the filter criteria is initialized.
        documentListenerHelper(criteriaName, field);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper(criteriaName, field);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper(criteriaName, field);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper(criteriaName, field);
            }
        });
    }

    private void documentListenerHelper(String criteriaName, JTextField field) {
        final FilterState currentState = filterViewModel.getState();
        currentState.setFilterCriteria(criteriaName, field.getText());
        filterViewModel.setState(currentState);
    }

    @Override
    public void clearFilter() {
        keywordField.setText("");
        genreField.setText("");
        actorField.setText("");
        final FilterState currentState = filterViewModel.getState();
        currentState.clearFilterCriteria();
        // Ensures the relevant fields in the filter criteria are initialized and
        // prevents the filter interactor from throwing a null pointer exception
        // if the user tries to filter without entering any criteria.
        currentState.setFilterCriteria(KEYWORDS, "");
        currentState.setFilterCriteria(GENRES, "");
        currentState.setFilterCriteria(ACTORS, "");
        filterViewModel.setState(currentState);
    }
}

