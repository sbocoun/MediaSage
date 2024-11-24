package use_case.list;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

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

    public ListInteractor(UserRepository userDataAccessObject,
                          ListOutputBoundary listOutputBoundary) {
        this.userDataAccessObject = userDataAccessObject;
        this.listPresenter = listOutputBoundary;
    }

    /**
     * Entrypoint for calling the list interactor to display a media collection with the given name.
     * @param listInputData the input data containing the name of a media collection to display
     */
    @Override
    public void execute(ListInputData listInputData) {
        final User user = userDataAccessObject.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("Not currently logged-in.");
        }
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections = user.getAllMediaCollections();
        final MediaCollection<? extends AbstractMedia> desiredCollection = findCollectionWithName(mediaCollections,
                listInputData.getNameOfDesiredCollection());
        if (desiredCollection == null) {
            final ListOutputData listOutputData = new ListOutputData(
                    "Collection with the name " + listInputData.getNameOfDesiredCollection() + " not found.",
                    getAvailableCollections(mediaCollections));
            listPresenter.prepareFailView(listOutputData);
        }
        else {
            final ListOutputData listOutputData = new ListOutputData(
                    collectionsToTableRowData(desiredCollection),
                    desiredCollection.getName(),
                    desiredCollection.getMediaType().getName(),
                    getAvailableCollections(mediaCollections));
            listPresenter.prepareSuccessView(listOutputData);
        }
    }

    /**
     * Entrypoint for calling the list interactor upon login to update the list view.
     *
     * @param mediaCollections the media collection used to refresh the list view
     */
    public void executeOnLogin(List<MediaCollection<? extends AbstractMedia>> mediaCollections) {
        final List<MediaCollection<? extends AbstractMedia>> movieCollections =
                populateCollectionIfEmpty(mediaCollections);
        final ListOutputData listOutputData = new ListOutputData(
                collectionsToTableRowData(movieCollections.get(0)),
                movieCollections.get(0).getName(),
                movieCollections.get(0).getMediaType().getName(),
                getAvailableCollections(movieCollections));
        listPresenter.prepareSuccessView(listOutputData);
    }

    /**
     * Return the collection corresponding to collectionName.
     * @param mediaCollections the list of movie collections to search from
     * @param collectionName the name of the collection to find
     * @return the collection corresponding to collectionName
     */
    @Nullable
    private MediaCollection<? extends AbstractMedia> findCollectionWithName(
            List<MediaCollection<? extends AbstractMedia>> mediaCollections, String collectionName) {
        MediaCollection<? extends AbstractMedia> result = null;
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
     * Populate the media collections with a default movie collection.
     * @param mediaCollections the media collections
     * @return a list of media collections, with at least one movie collection
     */
    private List<MediaCollection<? extends AbstractMedia>> populateCollectionIfEmpty(
            List<MediaCollection<? extends AbstractMedia>> mediaCollections) {
        if (mediaCollections.isEmpty()) {
            final MediaCollection<? extends AbstractMedia> newCollection = new MediaCollection<>(
                    "movie night", "to-watch", Movie.class, new ArrayList<>());
            mediaCollections.add(newCollection);
        }
        return mediaCollections;
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
