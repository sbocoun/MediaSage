package view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.note.BlankViewModel;

/**
 * The View for when the user is viewing a note in the program.
 */
public class BlankView extends JPanel {

    private final String viewName = "blank";
    private final BlankViewModel blankViewModel;
    private final JLabel blankPanelText = new JLabel("You're not logged in.");

    public BlankView(BlankViewModel blankViewModel) {
        this.blankViewModel = blankViewModel;
        blankPanelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(blankPanelText);
    }

    public String getViewName() {
        return viewName;
    }
}

