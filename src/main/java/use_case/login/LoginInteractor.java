package use_case.login;

import entity.User;
import use_case.list.ListInteractor;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;
    // jank solution
    private ListInteractor listInteractor;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           ListInteractor listInteractor) {
        this(userDataAccessInterface, loginOutputBoundary);
        this.listInteractor = listInteractor;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final User user = userDataAccessObject.get(username);
            final String pwd = user.getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
                listInteractor.execute(user);
                userDataAccessObject.setCurrentUsername(user.getName());
                userDataAccessObject.setCurrentPassword(pwd);
                final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
