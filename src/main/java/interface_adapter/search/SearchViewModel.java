package interface_adapter.search;

import interface_adapter.ViewModel;
import interface_adapter.note.NoteState;

/**
 * The ViewModel for the NoteView.
 */
public class NoteViewModel extends ViewModel<NoteState> {
    public NoteViewModel() {
        super("note");
        setState(new NoteState());
    }
}
