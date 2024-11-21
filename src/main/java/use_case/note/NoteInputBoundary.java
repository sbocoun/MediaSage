package use_case.note;

/**
 * The Input Boundary for our "Media Collections String"-related use cases. Since they are closely related,
 * we have included them both in the same interface for simplicity.
 */
public interface NoteInputBoundary {

    /**
     * Executes the refresh Media Collections String use case.
     */
    void executeRefresh();

    /**
     * Executes the save Media Collections String use case.
     *
     * @param mediaCollectionsString the string representation of the media collections to save
     */
    void executeSave(String mediaCollectionsString);
}
