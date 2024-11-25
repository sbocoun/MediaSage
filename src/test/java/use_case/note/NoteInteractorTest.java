package use_case.note;

import data_access.InMemoryUserDAO;
import data_access.grade_api.UserRepository;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class NoteInteractorTest {
    private UserRepository userRepo;

    @BeforeEach
    public void setup() {
        userRepo = new InMemoryUserDAO();
        User user = new User("test", "test");
        userRepo.save(user);
    }

    @Test
    void testExecuteRefreshSuccess() {

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
    void testSaveSuccess() {
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