package use_case.list;

import java.util.ArrayList;
import java.util.List;

import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
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
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections = populateCollectionIfEmpty(user);
        final MediaCollection<? extends AbstractMedia> desiredCollection = findCollectionWithName(mediaCollections,
                listInputData.getNameOfDesiredCollection());
        final String nameOfDesiredCollection = desiredCollection.getName();
        final List<String> availableCollections = getAvailableCollections(mediaCollections);

        final ListOutputData listOutputData = new ListOutputData(
                collectionsToTableRowData(desiredCollection),
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
        final List<MediaCollection<? extends AbstractMedia>> movieCollections = populateCollectionIfEmpty(user);
        final List<String> availableCollections = getAvailableCollections(movieCollections);
        final List<List<Object>> collectionDataToDisplay = collectionsToTableRowData(movieCollections.get(0));
        final ListOutputData listOutputData = new ListOutputData(
                collectionDataToDisplay, movieCollections.get(0).getName(), availableCollections);
        listPresenter.prepareSuccessView(listOutputData);
    }

    /**
     * Return the collection corresponding to collectionName.
     * @param mediaCollections the list of movie collections to search from
     * @param collectionName the name of the collection to find
     * @return the collection corresponding to collectionName
     */
    private MediaCollection<? extends AbstractMedia> findCollectionWithName(
            List<MediaCollection<? extends AbstractMedia>> mediaCollections, String collectionName) {
        // failsafe case for when there's no desired collection in input data, such as a new user account
        MediaCollection<? extends AbstractMedia> result = mediaCollections.get(0);
        for (MediaCollection<? extends AbstractMedia> collection : mediaCollections) {
            if (collection.getName().equals(collectionName)) {
                result = collection;
                // there shouldn't be any duplicates, but return the first one found just in case
                break;
            }
        }
        return result;
    }

    private List<String> getAvailableCollections(List<MediaCollection<? extends AbstractMedia>> movieCollections) {
        final List<String> result = new ArrayList<>();
        for (MediaCollection<? extends AbstractMedia> movieCollection : movieCollections) {
            result.add(movieCollection.getName());
        }
        return result;
    }

    /**
     * Populate the movie collections with a default movie collection.
     * @param user the user to retrieve the movie collections from
     * @return a list of movie collections, with at least one collection
     */
    private List<MediaCollection<? extends AbstractMedia>> populateCollectionIfEmpty(User user) {
        final List<MediaCollection<? extends AbstractMedia>> result = user.getAllMediaCollections();
        if (result.isEmpty()) {
            final MediaCollection<? extends AbstractMedia> newCollection = new MediaCollection<>(
                    "movie night", "to-watch", Movie.class, new ArrayList<>());
            result.add(newCollection);
        }
        return result;
    }

    private List<List<Object>> collectionsToTableRowData(MediaCollection<? extends AbstractMedia> movieCollection) {
        final List<List<Object>> result = new ArrayList<>();
        final TableRowDataBuilder builder = new TableRowDataBuilder();
        for (AbstractMedia media : movieCollection) {
            result.add(builder.createTableRowData(media));
        }
        return result;
    }
}
