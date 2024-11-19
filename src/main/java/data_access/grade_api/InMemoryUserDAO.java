package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.note.DataAccessException;

/**
 * In-memory implementation of user data access.
 */
public class InMemoryUserDAO implements UserRepository {
    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;
    private String currentPassword;

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @Override
    public void changePassword(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public String saveNote(String note) throws DataAccessException {
        if (!users.get(currentUsername).getPassword().equals(currentPassword)) {
            throw new DataAccessException("Incorrect password");
        }
        users.get(currentUsername).setNotes(note);
        return users.get(currentUsername).getNotes();
    }

    @Override
    public String loadNote() {
        return users.get(currentUsername).getNotes();
    }
}
