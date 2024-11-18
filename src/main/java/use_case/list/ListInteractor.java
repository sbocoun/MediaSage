package use_case.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_access.grade_api.UserRepository;
import entity.MediaCollection;
import entity.Movie;
import entity.User;

import javax.print.attribute.standard.Media;

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
        final List<MediaCollection<Movie>> movieCollections = user.getMovieCollections();

        if (movieCollections.isEmpty()) {
            final MediaCollection<Movie> newCollection = new MediaCollection<>(
                    "movie night", "to-watch", Movie.class, new ArrayList<>());
            movieCollections.add(newCollection);
        }

        MediaCollection<Movie> collectionToDisplay = null;
        String collectionNameToDisplay = "";
        final List<String> availableCollections = new ArrayList<>();

        if (!listInputData.getCollectionNameToDisplay().isBlank()) {
            for (MediaCollection<Movie> movieCollection : movieCollections) {
                if (movieCollection.getName().equals(listInputData.getCollectionNameToDisplay())) {
                    collectionToDisplay = movieCollection;
                    collectionNameToDisplay = movieCollection.getName();
                }
                availableCollections.add(movieCollection.getName());
            }
            if (collectionNameToDisplay.isBlank()) {
                final ListOutputData listOutputData = new ListOutputData(
                        "Collection " + listInputData.getCollectionNameToDisplay() + " not found.",
                        availableCollections);
                listPresenter.prepareFailView(listOutputData);
            }
        }
        if (listInputData.getCollectionNameToDisplay().isBlank()) {
            collectionToDisplay = movieCollections.get(0);
            collectionNameToDisplay = movieCollections.get(0).getName();
        }

        final ListOutputData listOutputData = new ListOutputData(
                collectionsToTableData(collectionToDisplay), collectionNameToDisplay, availableCollections);
        listPresenter.prepareSuccessView(listOutputData);
    }

    /**
     * Entrypoint for calling the list interactor upon login to update the list view.
     *
     * @param user the user containing the information used to refresh the list view
     */
    public void execute(User user) {
        final List<MediaCollection<Movie>> movieCollections = user.getMovieCollections();

        if (movieCollections.isEmpty()) {
            final MediaCollection<Movie> newCollection = new MediaCollection<>(
                    "movie night", "to-watch", Movie.class, new ArrayList<>());
            movieCollections.add(newCollection);
        }

        final List<String> availableCollections = new ArrayList<>();
        for (MediaCollection<Movie> movieCollection : movieCollections) {
            availableCollections.add(movieCollection.getName());
        }

        final List<Map<String, Object>> collectionDataToDisplay = collectionsToTableData(movieCollections.get(0));

        final ListOutputData listOutputData = new ListOutputData(
                collectionDataToDisplay, movieCollections.get(0).getName(), availableCollections);
        listPresenter.prepareSuccessView(listOutputData);
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
