package use_case.list_update;

import java.util.List;

import data_access.grade_api.GradeDataAccessException;
import data_access.grade_api.UserRepository;
import entity.AbstractMedia;
import entity.MediaCollection;

/**
 * The list update Interactor, used to update the user's media collection list with new changes from the app.
 */
public class ListUpdateInteractor implements ListUpdateInputBoundary {
    private final UserRepository userDataAccessObject;
    private final ListUpdateOutputBoundary listUpdatePresenter;

    public ListUpdateInteractor(UserRepository userDAO,
                                ListUpdateOutputBoundary listUpdatePresenter) {
        this.userDataAccessObject = userDAO;
        this.listUpdatePresenter = listUpdatePresenter;
    }

    /**
     * Updates the user rating for a media in the user's media collection, then saves the changes to the database.
     * @param listUpdateInputData the input data containing the information needed to update the user collection
     */
    @Override
    public void executeRatingUpdate(ListUpdateInputData listUpdateInputData) {
        final List<MediaCollection<? extends AbstractMedia>> mediaCollections =
                userDataAccessObject.get(userDataAccessObject.getCurrentUsername()).getAllMediaCollections();
        final MediaCollection<? extends AbstractMedia> mediaCollection = getRelevantCollection(
                mediaCollections, listUpdateInputData.getCollectionName());
        final AbstractMedia media = getRelevantMedia(mediaCollection, listUpdateInputData.getMediaName());
        media.setUserRatingScore(listUpdateInputData.getNewRating());
        try {
            userDataAccessObject.saveMediaCollections(mediaCollections);
            final ListUpdateOutputData listUpdateOutputData = new ListUpdateOutputData();
            listUpdateOutputData.setSuccessMessage("Changes saved successfully.");
            listUpdatePresenter.prepareSuccessView(listUpdateOutputData);
        }
        catch (GradeDataAccessException ex) {
            final ListUpdateOutputData listUpdateOutputData = new ListUpdateOutputData();
            listUpdateOutputData.setSuccessMessage("Error saving changes: " + ex.getMessage());
            listUpdatePresenter.prepareFailView(listUpdateOutputData);
        }
    }

    /**
     * Get the abstract media from the media collection that matches the name provided.
     * @param mediaCollection   the media collections to find the specific abstract media from
     * @param mediaName         the name of the abstract media to get
     * @return the abstract media with the matching name
     */
    private AbstractMedia getRelevantMedia(MediaCollection<? extends AbstractMedia> mediaCollection, String mediaName) {
        AbstractMedia result = null;
        for (AbstractMedia media : mediaCollection) {
            if (mediaName.equals(media.getName())) {
                result = media;
            }
        }
        return result;
    }

    /**
     * Get the collection from the currently logged-in user that matches the name provided.
     * @param mediaCollections       the list of media collections to find the specific collection from
     * @param relevantCollectionName the name of the collection to get
     * @return the collection with the matching name
     */
    private MediaCollection<? extends AbstractMedia> getRelevantCollection(
            List<MediaCollection<? extends AbstractMedia>> mediaCollections,
            String relevantCollectionName) {
        MediaCollection<? extends AbstractMedia> result = null;
        for (MediaCollection<? extends AbstractMedia> collection : mediaCollections) {
            if (relevantCollectionName.equals(collection.getName())) {
                result = collection;
            }
        }
        return result;
    }
}
