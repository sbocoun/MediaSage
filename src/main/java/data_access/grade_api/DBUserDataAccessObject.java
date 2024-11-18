package data_access.grade_api;

import java.io.IOException;

import org.json.JSONObject;

import entity.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The DAO for user data.
 */
public class DBUserDataAccessObject implements UserRepository {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String INFO = "info";
    private static final String NOTE = "note";
    private String currUsername;
    private String currPassword;

    @Override
    public User get(String username) {
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final JSONObject responseBody = getGradeApiData(request);
            final UserBuilder userBuilder = new UserBuilder();
            return userBuilder.createUser(responseBody.getJSONObject("user"));
        }
        catch (GradeDataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currUsername = name;
    }

    @Override
    public void setCurrentPassword(String password) {
        this.currPassword = password;
    }

    @Override
    public boolean existsByName(String username) {
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final JSONObject responseBody = getGradeApiData(request);
            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
        }
        catch (GradeDataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            getGradeApiData(request);
        }
        catch (GradeDataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changePassword(User user) {
        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            getGradeApiData(request);
            this.currPassword = user.getPassword();
        }
        catch (GradeDataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getCurrentUsername() {
        return currUsername;
    }

    @Override
    public String saveNote(String note) throws GradeDataAccessException {
        // POST METHOD
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, getCurrentUsername());
        requestBody.put(PASSWORD, this.currPassword);
        final JSONObject extra = new JSONObject();
        extra.put("note", note);
        requestBody.put(INFO, extra);
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            getGradeApiData(request);
            return loadNote();
        }
        catch (GradeDataAccessException ex) {
            requestBody.remove(PASSWORD);
            throw new GradeDataAccessException(ex.getMessage() + "%nSanitized Query:%n" + requestBody);
        }
    }

    @Override
    public String loadNote() throws GradeDataAccessException {
        // Make an API call to get the user object.
        final String username = getCurrentUsername();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        final JSONObject responseBody = getGradeApiData(request);
        final JSONObject userJSONObject = responseBody.getJSONObject("user");
        final JSONObject data = userJSONObject.getJSONObject(INFO);
        String note = "";
        if (data.has(NOTE)) {
            note = data.getString(NOTE);
        }
        return note;
    }

    /**
     * Wrapper for making a request to the Grade API.
     * @param request the request to make to the Grade API
     * @return the JSON response from the API
     * @throws GradeDataAccessException if Grade API is not available or request is malformed
     */
    private JSONObject getGradeApiData(Request request) throws GradeDataAccessException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                final String error = "Got a null response while calling the Grade API.";
                throw new GradeDataAccessException(error);
            }
            final JSONObject responseBody = new JSONObject(response.body().string());
            final int responseCode = responseBody.getInt(STATUS_CODE_LABEL);
            if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new GradeDataAccessException("message could not be found or password was incorrect");
            }
            else if (!response.isSuccessful() && responseCode != NOT_FOUND) {
                throw new GradeDataAccessException("database error: " + responseBody.getString(MESSAGE));
            }
            else {
                return responseBody;
            }
        }
        catch (IOException ex) {
            throw new GradeDataAccessException(ex.getMessage());
        }
    }
}
