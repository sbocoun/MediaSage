package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.note.BlankViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private final ViewManagerModel userViewManagerModel;
    private final ViewManagerModel mediaViewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final BlankViewModel blankViewModel;
    private final LoginViewModel loginViewModel;

    public LogoutPresenter(ViewManagerModel userViewManagerModel,
                          ViewManagerModel mediaViewManagerModel,
                          BlankViewModel blankViewModel,
                          LoggedInViewModel loggedInViewModel,
                           LoginViewModel loginViewModel) {
        this.userViewManagerModel = userViewManagerModel;
        this.mediaViewManagerModel = mediaViewManagerModel;
        this.blankViewModel = blankViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {
        // We need to switch to the login view, which should have
        // an empty username and password.

        // We also need to set the username in the LoggedInState to
        // the empty string.

        // Have prepareSuccessView update the LoggedInState
        // 1. get the LoggedInState out of the appropriate View Model,
        // 2. set the username in the state to the empty string
        // 3. set the state in the LoggedInViewModel to the updated state
        // 4. firePropertyChanged so that the View that is listening is updated.
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername("");
        loggedInViewModel.setState(loggedInState);
        this.userViewManagerModel.firePropertyChanged();

        // Have prepareSuccessView update the LoginState
        // 5. get the LoginState out of the appropriate View Model,
        // 6. set the username and password in the state to the empty string
        // 7. set the state in the LoginViewModel to the updated state
        // 8. firePropertyChanged so that the View that is listening is updated.
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername("");
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        this.userViewManagerModel.firePropertyChanged();
        this.mediaViewManagerModel.setState(blankViewModel.getViewName());
        this.mediaViewManagerModel.firePropertyChanged();

        // This code tells the View Manager to switch to the LoginView.
        this.userViewManagerModel.setState(loginViewModel.getViewName());
        this.userViewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that logout can't fail.
        // Thought question: is this a reasonable assumption?
        // Answer: I guess it'd depend on the reliability of our database.
        // Imagine if someone went in and deleted the username value during program operation.

    }
}
