package interface_adapter.note;

import use_case.note.NoteInputBoundary;

/**
 * Controller for our Note related Use Cases.
 */
public class NoteController {

    private final NoteInputBoundary noteInteractor;

    public NoteController(NoteInputBoundary noteInteractor) {
        this.noteInteractor = noteInteractor;
    }

    /**
     * Executes the mediaCollectionsString Refresh Use Case.
     */
    public void executeRefresh() {
        noteInteractor.executeRefresh();
    }

    /**
     * Executes the Media Collections Save Use Cases.
     *
     * @param mediaCollectionsString string representation of the Media Collections to save
     */
    public void executeSave(String mediaCollectionsString) {
        noteInteractor.executeSave(mediaCollectionsString);
    }
}
