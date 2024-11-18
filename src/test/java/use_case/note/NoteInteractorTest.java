package use_case.note;

import entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteInteractorTest {

    @Test
    public void testExecuteRefreshSuccess() {

        NoteDataAccessInterface noteDAO = new NoteDataAccessInterface() {


            @Override
            public String saveNote(String note) throws DataAccessException {
                return "";
            }

            @Override
            public String loadNote() throws DataAccessException {
                return "";
            }

            @Override
            public void setCurrentUsername(String username) {

            }

            @Override
            public void setCurrentPassword(String password) {

            }

            @Override
            public String saveNote(User user, String note) {
                return "";
            }


            @Override
            public String loadNote(User user) {
                return "test";
            }
        };

        NoteOutputBoundary noteOB = new NoteOutputBoundary() {
            @Override
            public void prepareSuccessView(String message) {
                assertEquals("test", message);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail(errorMessage);
            }
        };

        NoteInteractor noteInteractor = new NoteInteractor(noteDAO, noteOB);

        noteInteractor.executeRefresh();


    }
}