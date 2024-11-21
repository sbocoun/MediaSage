package use_case.note;

import java.util.List;

import data_access.grade_api.GradeDataAccessException;
import entity.AbstractMedia;
import entity.MediaCollection;

/**
 * The "Use Case Interactor" for our two note-related use cases of refreshing
 * the contents of the note and saving the contents of the note. Since they
 * are closely related, we have combined them here for simplicity.
 */
public class NoteInteractor implements NoteInputBoundary {

    private final NoteDataAccessInterface noteDataAccessInterface;
    private final NoteOutputBoundary noteOutputBoundary;

    public NoteInteractor(NoteDataAccessInterface noteDataAccessInterface,
                          NoteOutputBoundary noteOutputBoundary) {
        this.noteDataAccessInterface = noteDataAccessInterface;
        this.noteOutputBoundary = noteOutputBoundary;
    }

    /**
     * Executes the refresh note use case.
     */
    @Override
    public void executeRefresh() {
        try {
            final List<MediaCollection<? extends AbstractMedia>> mediaCollections =
                    noteDataAccessInterface.loadMediaCollections();
            noteOutputBoundary.prepareSuccessView(
                    noteDataAccessInterface.convertCollectionsListToString(mediaCollections));
        }
        catch (GradeDataAccessException ex) {
            noteOutputBoundary.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Executes the save note use case.
     */
    @Override
    public void executeSave(String mediaCollectionsString) {
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections =
                noteDataAccessInterface.convertStringToMediaCollections(mediaCollectionsString);
        try {
            final List<MediaCollection<? extends AbstractMedia>> updatedMediaCollections =
                    noteDataAccessInterface.saveMediaCollections(mediaCollections);
            noteOutputBoundary.prepareSuccessView(
                    noteDataAccessInterface.convertCollectionsListToString(updatedMediaCollections));
        }
        catch (GradeDataAccessException ex) {
            noteOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}
