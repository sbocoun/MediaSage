package app;

import java.awt.*;

import javax.swing.*;

import data_access.DBUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.note.NoteController;
import interface_adapter.note.NotePresenter;
import interface_adapter.note.NoteViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.note.NoteDataAccessInterface;
import use_case.note.NoteInteractor;
import use_case.note.NoteOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

/**
 * Builder for the Note Application.
 */
public class AppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;
    private NoteDataAccessInterface noteDAO;
    private NoteViewModel noteViewModel = new NoteViewModel();
    private NoteView noteView;
    private NoteInteractor noteInteractor;
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    // thought question: is the hard dependency below a problem?
    private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;

    /**
     * Ensures the views to be added will adhere to the card format.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Sets the NoteDAO to be used in this application.
     * @param noteDataAccess the DAO to use
     * @return this builder
     */
    public AppBuilder addNoteDAO(NoteDataAccessInterface noteDataAccess) {
        noteDAO = noteDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Note Use Case and connects the NoteView to its
     * controller.
     *
     * <p>This method must be called after addNoteView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addNoteView
     */
    public AppBuilder addNoteUseCase() {
        final NoteOutputBoundary noteOutputBoundary = new NotePresenter(noteViewModel);
        noteInteractor = new NoteInteractor(
                noteDAO, noteOutputBoundary);

        final NoteController controller = new NoteController(noteInteractor);
        if (noteView == null) {
            throw new RuntimeException("addNoteView must be called before addNoteUseCase");
        }
        noteView.setNoteController(controller);
        return this;
    }

    /**
     * Creates the NoteView and underlying NoteViewModel.
     * @return this builder
     */
    public AppBuilder addNoteView() {
        noteViewModel = new NoteViewModel();
        noteView = new NoteView(noteViewModel);
        cardPanel.add(noteView, noteView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, signupViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Builds the application and initially sets the login view to be displayed.
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame application = new JFrame();
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setTitle("MediaSage");
        application.setSize(WIDTH, HEIGHT);

        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;

    }
}
