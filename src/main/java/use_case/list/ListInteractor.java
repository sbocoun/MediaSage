package use_case.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_access.grade_api.UserRepository;
import entity.MediaCollection;
import entity.Movie;
import entity.User;

/**
 * The list display Interactor.
 */
public class ListInteractor implements ListInputBoundary {
    private final UserRepository userDataAccessObject;
    private final ListOutputBoundary listPresenter;

    public ListInteractor(UserRepository signupDataAccessInterface,
                          ListOutputBoundary listOutputBoundary) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.listPresenter = listOutputBoundary;
    }

    @Override
    public void execute(ListInputData listInputData) {
        final User user = userDataAccessObject.get(userDataAccessObject.getCurrentUsername());
        final List<MediaCollection<Movie>> movieCollections = populateCollectionIfEmpty(user);
        final MediaCollection<Movie> desiredCollection = findCollectionWithName(movieCollections,
                listInputData.getNameOfDesiredCollection());
        final String nameOfDesiredCollection = desiredCollection.getName();
        final List<String> availableCollections = getAvailableCollections(movieCollections);

        final ListOutputData listOutputData = new ListOutputData(
                collectionsToTableData(desiredCollection),
                nameOfDesiredCollection,
                availableCollections);
        listPresenter.prepareSuccessView(listOutputData);
    }

    /**
     * Entrypoint for calling the list interactor upon login to update the list view.
     *
     * @param user the user containing the information used to refresh the list view
     */
    public void execute(User user) {
        final List<MediaCollection<Movie>> movieCollections = populateCollectionIfEmpty(user);
        final List<String> availableCollections = getAvailableCollections(movieCollections);
        final List<Map<String, Object>> collectionDataToDisplay = collectionsToTableData(movieCollections.get(0));
        final ListOutputData listOutputData = new ListOutputData(
                collectionDataToDisplay, movieCollections.get(0).getName(), availableCollections);
        listPresenter.prepareSuccessView(listOutputData);
    }

    /**
     * Return the collection corresponding to collectionName.
     * @param movieCollections the list of movie collections to search from
     * @param collectionName the name of the collection to find
     * @return the collection corresponding to collectionName
     */
    private MediaCollection<Movie> findCollectionWithName(List<MediaCollection<Movie>> movieCollections,
                                                          String collectionName) {
        // failsafe case for when there's no desired collection in input data, such as a new user account
        MediaCollection<Movie> result = movieCollections.get(0);
        for (MediaCollection<Movie> collection : movieCollections) {
            if (collection.getName().equals(collectionName)) {
                result = collection;
                // there shouldn't be any duplicates, but return the first one found just in case
                break;
            }
        }
        return result;
    }

    private List<String> getAvailableCollections(List<MediaCollection<Movie>> movieCollections) {
        final List<String> result = new ArrayList<>();
        for (MediaCollection<Movie> movieCollection : movieCollections) {
            result.add(movieCollection.getName());
        }
        return result;
    }

    /**
     * Populate the movie collections with a default movie collection.
     * @param user the user to retrieve the movie collections from
     * @return a list of movie collections, with at least one collection
     */
    private List<MediaCollection<Movie>> populateCollectionIfEmpty(User user) {
        final List<MediaCollection<Movie>> result = user.getMovieCollections();
        if (result.isEmpty()) {
            final MediaCollection<Movie> newCollection = new MediaCollection<>(
                    "movie night", "to-watch", Movie.class, new ArrayList<>());
            result.add(newCollection);
        }
        return result;
    }

    private List<Map<String, Object>> collectionsToTableData(MediaCollection<Movie> movieCollection) {
        final List<Map<String, Object>> result = new ArrayList<>();
        for (Movie movie : movieCollection) {
            final Map<String, Object> movieMap = new HashMap<>();
            movieMap.put("name", movie.getName());
            movieMap.put("runtime", movie.getMinuteRuntime());
            movieMap.put("description", movie.getDescription());
            movieMap.put("genres", movie.getGenres());
            movieMap.put("cast", movie.getCastMembers());
            movieMap.put("user-rating", movie.getUserRating());
            movieMap.put("external-rating", movie.getExternalRating());
            result.add(movieMap);
        }
        return result;
    }
}
