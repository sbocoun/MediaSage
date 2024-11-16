package data_access;

import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.note.NoteDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * A repository of user information.
 */
public interface UserRepository extends SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        NoteDataAccessInterface {
}
