package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListView extends JPanel implements ActionListener {

    private final JComboBox<String> listSelector = new JComboBox<>(new String[]{"Watched", "PLan", "Favourites"});
    private final JLabel currentListLabel = new JLabel("Watched");
    private final JButton filterButton = new JButton("Filter");
    private final JButton removeButton = new JButton("Remove");
    private final JButton moveToButton = new JButton("Move to");
    private final JTextField filterField = new JTextField(10);
    private final JPanel itemListPanel = new JPanel();
    private final List<JRadioButton> radioButtonList = new ArrayList<>();
    private final List<String> movieDescriptions = new ArrayList<>();

    public ListView() {
        setLayout(new BorderLayout());
        final JPanel topPanel = new JPanel(new BorderLayout());

        JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        listPanel.add(listSelector);

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by description:"));
        filterPanel.add(filterField);
        filterPanel.add(filterButton);

        topPanel.add(listPanel, BorderLayout.WEST);
        topPanel.add(filterPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemListPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(removeButton);
        bottomPanel.add(moveToButton);
        add(bottomPanel, BorderLayout.SOUTH);

        filterButton.addActionListener(this);
        removeButton.addActionListener(this);
        moveToButton.addActionListener(this);
        listSelector.addActionListener(this);

        // Test
        addItem("Movie 1", "Test1");
        addItem("Movie 2", "Test2");
        addItem("Movie 3", "Test3");
    }

    /**
     * Adds an item to the list with a thumbnail, name, and description.
     *
     * @param name        Name of the movie
     * @param description Description of the movie
     */
    private void addItem(String name, String description) {
        JPanel itemPanel = new JPanel(new BorderLayout());

        // select item
        JRadioButton radioButton = new JRadioButton();
        radioButtonList.add(radioButton);
        itemPanel.add(radioButton, BorderLayout.WEST);

        JLabel thumbnailLabel = new JLabel("Thumbnail");
        itemPanel.add(thumbnailLabel, BorderLayout.CENTER);

        JPanel nameDescPanel = new JPanel(new GridLayout(2, 1));
        nameDescPanel.add(new JLabel(name));
        nameDescPanel.add(new JLabel(description));
        itemPanel.add(nameDescPanel, BorderLayout.EAST);

        itemListPanel.add(itemPanel);
        movieDescriptions.add(description);

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == filterButton) {
            String filterText = filterField.getText().toLowerCase();
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
        itemListPanel.removeAll();

        for (int i = 0; i < movieDescriptions.size(); i++) {
            String description = movieDescriptions.get(i).toLowerCase();
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
        JPanel itemPanel = new JPanel(new BorderLayout());

        // Use the existing radio button and description data
        JRadioButton radioButton = radioButtonList.get(index);
        itemPanel.add(radioButton, BorderLayout.WEST);

        JLabel thumbnailLabel = new JLabel("Thumbnail");
        itemPanel.add(thumbnailLabel, BorderLayout.CENTER);

        JPanel nameDescPanel = new JPanel(new GridLayout(2, 1));
        nameDescPanel.add(new JLabel("Movie " + (index + 1)));
        nameDescPanel.add(new JLabel(movieDescriptions.get(index)));
        itemPanel.add(nameDescPanel, BorderLayout.EAST);

        itemListPanel.add(itemPanel);
    }

    /**
     * Removes the selected movie from the list.
     */
    private void removeSelectedMovie() {
        for (int i = 0; i < radioButtonList.size(); i++) {
            if (radioButtonList.get(i).isSelected()) {
                radioButtonList.remove(i);
                movieDescriptions.remove(i);
                itemListPanel.remove(i);
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
                String movieName = "Movie " + (i + 1);
                String selectedList = (String) listSelector.getSelectedItem();
                System.out.println("Moving " + movieName + " to " + selectedList);
                break;
            }
        }
    }
}
