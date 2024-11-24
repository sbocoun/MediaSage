package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import interface_adapter.note.NoteController;
import interface_adapter.note.NoteState;
import interface_adapter.note.NoteViewModel;

/**
 * The View for when the user is viewing a note in the program.
 */
public class NoteView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JLabel noteName = new JLabel("debug view");
    private final JTextArea noteInputField = new JTextArea();
    private final JButton saveButton = new JButton("Save");
    private final JButton refreshButton = new JButton("Refresh");
    private NoteController noteController;

    public NoteView(NoteViewModel noteViewModel) {

        noteName.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteViewModel.addPropertyChangeListener(this);

        final JPanel buttons = new JPanel();
        buttons.add(saveButton);
        buttons.add(refreshButton);
        buttons.add(new JButton("Generate Recommendations"));

        saveButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(saveButton)) {
                        noteController.executeSave(noteInputField.getText());

                    }
                }
        );

        refreshButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(refreshButton)) {
                        noteController.executeRefresh();

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(noteName);
        this.add(new JScrollPane(noteInputField));
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final NoteState state = (NoteState) evt.getNewValue();
        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFields(NoteState state) {
        noteName.setText("note for " + state.getUsername());
        noteInputField.setText(state.getNote());
    }

    public String getViewName() {
        return "debug";
    }

    public void setNoteController(NoteController controller) {
        this.noteController = controller;
    }
}

