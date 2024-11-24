package use_case.note;

import data_access.InMemoryUserDAO;
import data_access.grade_api.UserRepository;
import entity.User;
import interface_adapter.filter_list.FilterListOutputBoundary;
import interface_adapter.filter_list.FilterListOutputData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FilterInteractorTest {
    private UserRepository userRepo;

    @Before
    public void setup() {
        userRepo = new InMemoryUserDAO();
        User user = new User("test", "test");
        userRepo.save(user);
    }

    @Test
    public void testExecuteRefreshSuccess() {

        FilterListOutputBoundary filterOB = new FilterListOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterListOutputData filterListOutputData) {
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