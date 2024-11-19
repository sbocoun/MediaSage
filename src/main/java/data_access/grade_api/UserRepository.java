package data_access.grade_api;

import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.note.NoteDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * Facade for the Grade API data access interface.
 */
public interface UserRepository extends
        NoteDataAccessInterface,
        SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {
}
