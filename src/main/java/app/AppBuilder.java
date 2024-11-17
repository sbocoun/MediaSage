package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import data_access.DBUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.generate_recommendations.GenController;
import interface_adapter.generate_recommendations.GenPresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.note.BlankViewModel;
import interface_adapter.note.NoteController;
import interface_adapter.note.NotePresenter;
import interface_adapter.note.NoteViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.generate_recommendations.GenDataAccessInterface;
import use_case.generate_recommendations.GenInteractor;
import use_case.generate_recommendations.GenOutputBoundary;
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
    public static final int HEIGHT = 450;
    public static final int WIDTH = 400;
    private NoteInteractor noteInteractor;
    private GenInteractor genInteractor;
    private final JTabbedPane tabPanel = new JTabbedPane();
    private final JPanel cardPanel = new JPanel();
    private final JPanel userPanel = new JPanel();
    private final JPanel mediaPanel = new JPanel();
    private final JPanel searchPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel userViewManagerModel = new ViewManagerModel();
    private final ViewManagerModel mediaViewManagerModel = new ViewManagerModel();
    private final ViewManagerModel searchViewManagerModel = new ViewManagerModel();
    private final ViewManager userViewManager = new ViewManager(userPanel, cardLayout, userViewManagerModel);
    private final ViewManager mediaViewManager = new ViewManager(mediaPanel, cardLayout, mediaViewManagerModel);
    private final ViewManager searchViewManager = new ViewManager(searchPanel, cardLayout, searchViewManagerModel);
    // thought question: is the hard dependency below a problem?
    private DBUserDataAccessObject userDataAccessObject;
    private GenDataAccessInterface genDataAccessInterface;

    private NoteView noteView;
    private NoteViewModel noteViewModel;
    private BlankView blankView;
    private BlankViewModel blankViewModel;
    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;

    /**
     * Adds the initial tabs and card layout views.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        mediaPanel.setLayout(cardLayout);
        userPanel.setLayout(cardLayout);
        tabPanel.addTab("Media", mediaPanel);
        tabPanel.addTab("Search", searchPanel);
        tabPanel.addTab("User", userPanel);
    }

    /**
     * Adds the data access object for user information.
     *
     * @param userDAO the data access object for user information
     * @return this builder
     */
    public AppBuilder addUserDAO(DBUserDataAccessObject userDAO) {
        this.userDataAccessObject = userDAO;
        return this;
    }

    /**
     * Adds the generate movies data access object.
     *
     * @param genDAO the note data access interface to use
     * @return this builder
     */
    public AppBuilder addGenDAO(GenDataAccessInterface genDAO) {
        this.genDataAccessInterface = genDAO;
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
        final NoteOutputBoundary noteOutputBoundary = new NotePresenter(noteViewModel, mediaViewManagerModel);
        noteInteractor = new NoteInteractor(userDataAccessObject, noteOutputBoundary);
        final NoteController controller = new NoteController(noteInteractor);
        if (noteView == null) {
            throw new RuntimeException("addNoteView must be called before addNoteUseCase");
        }
        noteView.setNoteController(controller);
        return this;
    }

    /**
     * Creates the objects for the Generate Movie Recommendations use case and
     * connects the NoteView to its controller.
     *
     * <p>This method must be called after addNoteView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addNoteView
     */
    public AppBuilder addGenUseCase() {
        final GenOutputBoundary genOutputBoundary = new GenPresenter(noteViewModel);
        genInteractor = new GenInteractor(genDataAccessInterface, genOutputBoundary);
        final GenController genController = new GenController(genInteractor);
        if (noteView == null) {
            throw new RuntimeException("addNoteView must be called before addGenUseCase");
        }
        noteView.setGenController(genController);
        return this;
    }

    /**
     * Creates the blank view for logged-out users.
     *
     * @return this builder
     */
    public AppBuilder addBlankView() {
        blankViewModel = new BlankViewModel();
        blankView = new BlankView(blankViewModel);
        mediaPanel.add(blankView, blankView.getViewName());
        return this;
    }

    /**
     * Creates the NoteView and underlying NoteViewModel.
     * @return this builder
     */
    public AppBuilder addNoteView() {
        noteViewModel = new NoteViewModel();
        noteView = new NoteView(noteViewModel);
        mediaPanel.add(noteView, noteView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the user panel.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        userPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        userPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        userPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(userViewManagerModel,
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
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(userViewManagerModel,
                mediaViewManagerModel, loggedInViewModel, signupViewModel, noteViewModel, loginViewModel);
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
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(userViewManagerModel,
                mediaViewManagerModel, blankViewModel, loggedInViewModel, loginViewModel);

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

        application.add(tabPanel);

        userViewManagerModel.setState(loginView.getViewName());
        userViewManagerModel.firePropertyChanged();

        return application;

    }
}
