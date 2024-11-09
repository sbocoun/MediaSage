package interface_adapter.generate_recommendations;

import interface_adapter.note.NoteViewModel;
import use_case.generate_recommendations.GenOutputBoundary;

public class GenPresenter implements GenOutputBoundary {

    private final NoteViewModel noteViewModel;

    public GenPresenter(NoteViewModel noteViewModel) {
        this.noteViewModel = noteViewModel;
    }

    /**
     * Prepares the success view for the generate recommendations use case.
     *
     * @param recommendations the output data
     */
    @Override
    public void prepareSuccessView(String recommendations) {
        noteViewModel.getState().setNote(recommendations);
        noteViewModel.getState().setError(null);
        noteViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the generate recommendations Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        noteViewModel.getState().setError(errorMessage);
        noteViewModel.firePropertyChanged();
    }
}
