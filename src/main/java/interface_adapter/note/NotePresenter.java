package interface_adapter.note;

import interface_adapter.ViewManagerModel;
import use_case.note.NoteOutputBoundary;

/**
 * The presenter for our Note viewing and editing program.
 */
public class NotePresenter implements NoteOutputBoundary {

    private final NoteViewModel noteViewModel;
    private final ViewManagerModel userViewManagerModel;

    public NotePresenter(NoteViewModel noteViewModel, ViewManagerModel userViewManagerModel) {
        this.noteViewModel = noteViewModel;
        this.userViewManagerModel = userViewManagerModel;
    }

    /**
     * Prepares the success view for the Note related Use Cases.
     *
     * @param note the output data
     */
    @Override
    public void prepareSuccessView(String note) {
        noteViewModel.getState().setNote(note);
        noteViewModel.getState().setError(null);
        noteViewModel.firePropertyChanged();

        userViewManagerModel.setState(noteViewModel.getViewName());
        userViewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Note related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        noteViewModel.getState().setError(errorMessage);
        noteViewModel.firePropertyChanged();
    }
}
