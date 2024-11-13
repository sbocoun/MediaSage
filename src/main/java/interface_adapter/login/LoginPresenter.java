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
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          SignupViewModel signupViewModel,
                          NoteViewModel noteViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.signupViewModel = signupViewModel;
        this.noteViewModel = noteViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the notes view.
        this.noteViewModel.getState().setNote(response.getNotes());
        this.noteViewModel.getState().setUsername(response.getUsername());
        this.noteViewModel.getState().setError(null);
        this.noteViewModel.firePropertyChanged();

        // final LoggedInState loggedInState = loggedInViewModel.getState();
        // loggedInState.setUser(response.getUser());
        // this.loggedInViewModel.setState(loggedInState);
        // this.loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setState(noteViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
