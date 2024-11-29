package use_case.list.removeMedia;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.User;
import use_case.list.ListOutputBoundary;
import use_case.list.ListOutputData;
import use_case.list.TableRowDataBuilder;

/**
 * The list display Interactor.
 */
public final class RemoveInteractor implements RemoveInputBoundary {
    private final UserRepository userRepository;
    private final ListOutputBoundary listPresenter;

    public RemoveInteractor(UserRepository userRepository, ListOutputBoundary presenter) {
        this.userRepository = userRepository;
        this.listPresenter = presenter;
    }

    /**
     * Entrypoint for calling the remove interactor to remove a media item from a collection.
     * @param input The input data containing the collection name and the media name to be removed.
     */
    @Override
    public void execute(RemoveMediaInput input) {
        final User user = userRepository.get(userRepository.getCurrentUsername());
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections = user.getAllMediaCollections();
        final MediaCollection<? extends AbstractMedia> collection = findCollectionWithName(
                mediaCollections, input.getCollectionName());
        final AbstractMedia mediaToRemove = findMediaByName(collection, input.getMediaName());

        if (collection == null) {
            listPresenter.prepareFailView(new ListOutputData(
                    "Collection " + input.getCollectionName() + " not found.",
                    getAvailableCollections(mediaCollections)
            ));
        }
        else if (mediaToRemove == null) {
            listPresenter.prepareFailView(new ListOutputData(
                    "Media with the name " + input.getMediaName() + " not found in the collection.",
                    getAvailableCollections(mediaCollections)
            ));
        }
        else {
            collection.removeMedia(mediaToRemove);
            listPresenter.prepareSuccessView(new ListOutputData(
                    collectionsToTableRowData(collection),
                    collection.getName(),
                    collection.getCollectionType(),
                    getAvailableCollections(mediaCollections)
            ));
        }
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
     * Return the media corresponding to name.
     * @param collection the list of movie collections we are in
     * @param mediaName the name of the media to find
     * @return the corresponding media object
     */
    @Nullable
    private AbstractMedia findMediaByName(MediaCollection<? extends AbstractMedia> collection, String mediaName) {
        AbstractMedia result = null;
        for (AbstractMedia media : collection) {
            if (media.getName().equalsIgnoreCase(mediaName)) {
                result = media;
                break;
            }
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
