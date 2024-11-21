package use_case.logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        // Save the DAO and Presenter in the instance variables.
        // Which parameter is the DAO and which is the presenter?
        userDataAccessObject = userDataAccessInterface;
        logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        // Implement the logic of the Logout Use Case (depends on the LogoutInputData.java)
        // * get the username out of the input data,
        // * set the username to null in the DAO
        // * instantiate the `LogoutOutputData`, which needs to contain the username.
        // * tell the presenter to prepare a success view.
        if (userDataAccessObject.getCurrentUsername() == null) {
            logoutPresenter.prepareFailView("Username not found.");
        }
        else {
            final LogoutOutputData logoutOutputData = new LogoutOutputData(userDataAccessObject.getCurrentUsername(),
                    false);
            userDataAccessObject.clearCurrentUser();
            logoutPresenter.prepareSuccessView(logoutOutputData);
        }
    }
}

