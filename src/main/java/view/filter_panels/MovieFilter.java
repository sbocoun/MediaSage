package view.filter_panels;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.filter_list.FilterState;
import interface_adapter.filter_list.FilterViewModel;

/**
 * Panel for filtering movies.
 */
public class MovieFilter extends JPanel implements Filter {

    private static final String KEYWORDS = "keywords";
    private static final String GENRES = "genres";
    private static final String ACTORS = "actors";
    private final JTextField keywordField = new JTextField(15);
    private final JTextField genreField = new JTextField();
    private final JTextField actorField = new JTextField();
    private final FilterViewModel filterViewModel;

    public MovieFilter(FilterViewModel filterViewModel) {

        this.filterViewModel = filterViewModel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createPanelHelper("Keywords", keywordField);
        createPanelHelper("Genres", genreField);
        createPanelHelper("Actors", actorField);

        addKeywordListener();
        addGenreListener();
        addActorListener();
    }

    private void createPanelHelper(String labelText, Component component) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(component);
        this.add(panel);
    }

    private void addKeywordListener() {
        keywordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper(KEYWORDS, keywordField);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper(KEYWORDS, keywordField);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper(KEYWORDS, keywordField);
            }
        });
    }

    private void addGenreListener() {
        genreField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper(GENRES, genreField);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper(GENRES, genreField);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper(GENRES, genreField);
            }
        });
    }

    private void addActorListener() {
        actorField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper(ACTORS, actorField);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper(ACTORS, actorField);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper(ACTORS, actorField);
            }
        });
    }

    private String normalizeInput(String text) {
        // Split the input string by spaces or commas
        final String[] words = text.split("[,\\s]+");
        // Join the array of words with commas
        return String.join(",", words);
    }

    private void documentListenerHelper(String criteriaName, JTextField field) {
        final FilterState currentState = filterViewModel.getState();
        currentState.setFilterCriteria(criteriaName, normalizeInput(field.getText()));
        filterViewModel.setState(currentState);
    }

    @Override
    public void clearFilter() {
        keywordField.setText("");
        genreField.setText("");
        actorField.setText("");
        final FilterState currentState = filterViewModel.getState();
        currentState.resetFilterCriteria();
        filterViewModel.setState(currentState);
    }
}
