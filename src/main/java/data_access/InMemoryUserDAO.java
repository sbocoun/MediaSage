package data_access;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import data_access.grade_api.UserRepository;
import data_access.grade_api.incoming_data_formatting.UserBuilder;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.User;

/**
 * In-memory implementation of user data access.
 */
public class InMemoryUserDAO implements UserRepository {
    private static final String UNSUPPORTED = "Not implemented yet for in-memory user object.";
    private final Map<String, User> users = new HashMap<>();
    private User currentUser;

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
        this.currentUser = users.get(username);
        if (currentUser.getAllMediaCollections() == null) {
            currentUser.setMediaCollections(new ArrayList<>());
        }
        return currentUser;
    }

    @Override
    public String getCurrentUsername() {
        final String result;
        if (currentUser != null) {
            result = currentUser.getName();
        }
        else {
            result = "";
        }
        return result;
    }

    @Override
    public void clearCurrentUser() {
        this.currentUser = null;
    }

    @Override
    public void changePassword(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public List<MediaCollection<? extends AbstractMedia>> saveMediaCollections(
            List<MediaCollection<? extends AbstractMedia>> mediaCollectionsList) {
        currentUser.setMediaCollections(mediaCollectionsList);
        save(currentUser);
        return get(currentUser.getName()).getAllMediaCollections();
    }

    @Override
    public List<MediaCollection<? extends AbstractMedia>> loadMediaCollections() {
        return currentUser.getAllMediaCollections();
    }

    @Override
    public String convertCollectionsListToString(List<MediaCollection<? extends AbstractMedia>> mediaCollectionList) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }

    @Override
    public List<MediaCollection<? extends AbstractMedia>> convertStringToMediaCollections(
            String mediaCollectionsString) {
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

    @Override
    @Nullable
    public User getCurrentUser() {
        return currentUser;
    }
}
