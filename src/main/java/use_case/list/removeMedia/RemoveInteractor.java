package use_case.list.removeMedia;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;
import entity.User;

/**
 * The list display Interactor.
 */
public class RemoveInteractor implements RemoveInputBoundary {
    private final UserRepository userRepository;
    private final RemoveOutputBoundary removePresenter;

    private RemoveInteractor(UserRepository userRepository, RemoveOutputBoundary presenter) {
        this.userRepository = userRepository;
        this.removePresenter = removePresenter;
    }

    /**
     * Entrypoint for calling the remove interactor to remove a media item from a collection.
     * @param input The input data containing the collection name and the media name to be removed.
     */
    @Override
    public void execute(RemoveMediaInput input) {
        final User user = userRepository.get(userRepository.getCurrentUsername());
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections = user.getAllMediaCollections();

        // Fetch the current user
        User user = userRepository.get(userRepository.getCurrentUsername());
        List<MediaCollection<? extends AbstractMedia>> mediaCollections = user.getAllMediaCollections();

        // Find the collec2ution by its name
        MediaCollection<? extends AbstractMedia> collection = findCollectionWithName(
                mediaCollections, input.getCollectionName()
        );

        if (collection == null) {
            presenter.present(new RemoveOutputData(false, "Collection not found: " + input.getCollectionName()));
            return;
        }

        // Attempt to remove the specified media
        boolean removed = collection.removeMediaByName(input.getMediaName());

        if (removed) {
            presenter.present(new RemoveOutputData(true, "Media removed successfully: " + input.getMediaName()));
        }
        else {
            presenter.present(new RemoveOutputData(false, "Media not found: " + input.getMediaName()));
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
}
