package app;

import data_access.InMemoryRecommendationDAO;
import data_access.InMemoryUserDAO;
import use_case.generate_recommendations.GenDataAccessInterface;

/**
 * A media recommendation application, but with dummy api calls.
 */
public class MainInMemoryOnly {
    static final boolean DEBUG = true;

    /**
     * The entrypoint of the application where all external api calls are replaced by dummy implementations.
     *
     * @param args args
     */
    public static void main(String[] args) {
        // User repository, but in-memory
        final InMemoryUserDAO inMemoryUserDAO = new InMemoryUserDAO();
        inMemoryUserDAO.loadUserFromFile("grade-api-sample-response.json");
        final GenDataAccessInterface inMemoryRecommendationDAO = new InMemoryRecommendationDAO();

        final AppBuilder builder = new AppBuilder(DEBUG);
        builder.addUserDAO(inMemoryUserDAO)
                .addGenDAO(inMemoryRecommendationDAO)
                .addBlankView()
                .addLoginView()
                .addSignupView()
                .addSearchView()
                .addLoggedInView()
                .addListView()
                .addNoteView()
                .addNoteUseCase()
                .addListUseCase()
                .addGenUseCase()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .addMoveMediaUseCase()
                .addRemoveMediaUseCase()
                .build().setVisible(true);
    }
}
