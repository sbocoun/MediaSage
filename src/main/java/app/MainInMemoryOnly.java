package app;

import data_access.InMemoryRecommendationDAO;
import data_access.InMemoryUserDAO;
import data_access.grade_api.UserRepository;
import use_case.generate_recommendations.GenDataAccessInterface;

/**
 * A media recommendation application, but with dummy api calls.
 */
public class MainInMemoryOnly {

    /**
     * The entrypoint of the application where all external api calls are replaced by dummy implementations.
     *
     * @param args args
     */
    public static void main(String[] args) {
        final UserRepository inMemoryUserDAO = new InMemoryUserDAO();
        final GenDataAccessInterface inMemoryRecommendationDAO = new InMemoryRecommendationDAO();

        final AppBuilder builder = new AppBuilder();
        builder.addUserDAO(inMemoryUserDAO)
                .addGenDAO(inMemoryRecommendationDAO)
                .addBlankView()
                .addNoteView()
                .addLoginView()
                .addSignupView()
                .addLoggedInView()
                .addNoteUseCase()
                .addGenUseCase()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .build().setVisible(true);
    }
}
