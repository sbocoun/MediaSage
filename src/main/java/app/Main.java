package app;

import data_access.TasteDiveRecommendation;
import data_access.grade_api.DBUserDataAccessObject;
import data_access.movies.MovieDBDataAccessObject;
import use_case.generate_recommendations.GenDataAccessInterface;
import use_case.generate_recommendations.MovieDBDataAccessInterface;

/**
 * An application where we can view and edit the list of media collections that a user has stored,
 * search an internet database for new media, and get recommendation for the user's media collections.
 */
public class Main {
    static final boolean DEBUG = true;

    /**
     * The main entry point of the application.
     *
     * @param args commandline arguments are ignored
     */

    public static void main(String[] args) {

        final Configurator configurator = new Configurator();
        final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject();
        final GenDataAccessInterface genDataAccessInterface = new TasteDiveRecommendation();
        genDataAccessInterface.setApiKey(configurator.getTasteDiveApiKey());
        final MovieDBDataAccessInterface movieDBDataAccess = new MovieDBDataAccessObject();
        movieDBDataAccess.setApiKey(configurator.getTmdbApiKey());

        final AppBuilder builder = new AppBuilder(DEBUG);
        builder.addUserDAO(userDataAccessObject)
                .addGenDAO(genDataAccessInterface)
                .addMovieDAO(movieDBDataAccess)
                .addBlankView()
                .addLoginView()
                .addSignupView()
                .addSearchView()
                .addLoggedInView()
                .addNoteView()
                .addListView()
                .addNoteUseCase()
                .addListUseCase()
                .addListUpdateUseCase()
                .addFilterListUseCase()
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
