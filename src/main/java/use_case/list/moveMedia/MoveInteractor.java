package use_case.list.moveMedia;

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
 * The interactor for moving a media item between collections.
 */
public class MoveInteractor implements MoveInputBoundary {
    private final UserRepository userRepository;
    private final ListOutputBoundary listPresenter;

    public MoveInteractor(UserRepository userRepository, ListOutputBoundary listPresenter) {
        this.userRepository = userRepository;
        this.listPresenter = listPresenter;
    }

    /**
     * Entrypoint for calling the move button interactor to display a media collection with the given name.
     * @param input the input data containing the name of a media collection to display
     */
    @Override
    public void execute(MoveInputData input) {
        final User user = userRepository.get(userRepository.getCurrentUsername());
        final List<MediaCollection<? extends AbstractMedia>> collections = user.getAllMediaCollections();

        // Find the source and target collections
        final MediaCollection<?> sourceCollection = findCollectionByName(collections, input.getSourceCollectionName());
        final MediaCollection<?> targetCollection = findCollectionByName(collections, input.getTargetCollectionName());

        if (sourceCollection == null || targetCollection == null) {
            throw new IllegalArgumentException("Source or target collection not found.");
        }

        final AbstractMedia movieToMove = findMediaByName(sourceCollection, input.getMovieName());
        if (movieToMove == null) {
            throw new IllegalArgumentException("Movie not found in source collection.");
        }

        sourceCollection.removeMedia(movieToMove);
        targetCollection.addMedia(movieToMove);

        listPresenter.prepareSuccessView(new ListOutputData(
                collectionsToTableRowData(targetCollection),
                targetCollection.getName(),
                targetCollection.getMediaType().getName(),
                getAvailableCollections(collections)
        ));
    }

    /**
     * Return the collection corresponding to collectionName.
     * @param mediaCollections the list of movie collections to search from
     * @param collectionName the name of the collection to find
     * @return the collection corresponding to collectionName
     */
    @Nullable
    private MediaCollection<? extends AbstractMedia> findCollectionByName(
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

    private List<String> getAvailableCollections(List<MediaCollection<? extends AbstractMedia>> collections) {
        return collections.stream().map(MediaCollection::getName).toList();
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
