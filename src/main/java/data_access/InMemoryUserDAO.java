package data_access;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import data_access.grade_api.GradeDataAccessException;
import data_access.grade_api.UserRepository;
import data_access.grade_api.incoming_data_formatting.UserBuilder;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.User;

/**
 * In-memory implementation of user data access.
 */
public class InMemoryUserDAO implements UserRepository {
    private static final String UNSUPPORTED = "Not supported for in-memory user object.";
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
    public String saveMediaCollections(List<MediaCollection<? extends AbstractMedia>> mediaCollectionsList) throws GradeDataAccessException {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }

    @Override
    public String loadNote() {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }

    /**
     * Load the sample user response from resources to the in-memory user repository.
     *
     * @param userFilename file name for the json file containing a user response from the Grade API
     */
    public void loadUserFromFile(String userFilename) {
        final UserBuilder userBuilder = new UserBuilder();
        try {
            final JSONObject rawUser = new JSONObject(Files.readString(Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader()
                            .getResource(userFilename)).toURI()))).getJSONObject("user");
            final User user = userBuilder.createUser(rawUser);
            save(user);
        }
        catch (IOException | URISyntaxException ex) {
            System.out.println("Reading user sample response failed. Check if "
                    + "both src/main/resources/" + userFilename + " exist.");
        }
    }
}
