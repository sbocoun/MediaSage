package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.note.NoteViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final SignupViewModel signupViewModel;
    private final NoteViewModel noteViewModel;
    private final ViewManagerModel userViewManagerModel;
    private final ViewManagerModel debugViewManagerModel;

    public LoginPresenter(ViewManagerModel userViewManagerModel,
                          ViewManagerModel debugViewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          SignupViewModel signupViewModel,
                          NoteViewModel noteViewModel,
                          LoginViewModel loginViewModel) {
        this.userViewManagerModel = userViewManagerModel;
        this.debugViewManagerModel = debugViewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.signupViewModel = signupViewModel;
        this.noteViewModel = noteViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        this.userViewManagerModel.setState(loggedInViewModel.getViewName());
        this.userViewManagerModel.firePropertyChanged();
        this.debugViewManagerModel.setState(noteViewModel.getViewName());
        this.debugViewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {
        userViewManagerModel.setState(signupViewModel.getViewName());
        userViewManagerModel.firePropertyChanged();
    }
}
