package interface_adapter.search;

import interface_adapter.ViewModel;
import interface_adapter.note.NoteState;

/**
 * The ViewModel for the NoteView.
 */
public class SearchViewModel extends ViewModel<NoteState> {
    public SearchViewModel() {
        super("note");
        setState(new NoteState());
    }
}
