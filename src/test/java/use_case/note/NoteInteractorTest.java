package use_case.note;

import data_access.InMemoryUserDAO;
import data_access.grade_api.UserRepository;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteInteractorTest {
    private UserRepository userRepo;

    @Before
    public void setup() {
        userRepo = new InMemoryUserDAO();
        User user = new User("test", "test");
        userRepo.save(user);
    }

    @Test
    public void testExecuteRefreshSuccess() {

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

        NoteInteractor noteInteractor = new NoteInteractor(userRepo, noteOB);
        noteInteractor.executeRefresh();
    }

    @Test
    public void testSaveSuccess() {
        NoteOutputBoundary noteOB = new NoteOutputBoundary() {
            @Override
            public void prepareSuccessView(String message) {
                assertEquals("edited test", message);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail(errorMessage);
            }
        };

        NoteInteractor noteInteractor = new NoteInteractor(userRepo, noteOB);
        noteInteractor.executeSave("edited test");
    }
}