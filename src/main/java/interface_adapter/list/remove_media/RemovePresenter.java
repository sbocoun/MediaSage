package interface_adapter.list.remove_media;

import use_case.list.removeMedia.RemoveOutputBoundary;
import use_case.list.removeMedia.RemoveOutputData;

/**
 * The presenter for the Remove Use Case.
 */
public class RemovePresenter implements RemoveOutputBoundary {
    private final RemoveViewModel removeViewModel;

    public RemovePresenter(RemoveViewModel removeViewModel) {
        this.removeViewModel = removeViewModel;
    }

    /**
     * Prepares the success view for the remove use case.
     *
     * @param removeOutputData output data containing the result of the remove operation
     */
    @Override
    public void prepareSuccessView(RemoveOutputData removeOutputData) {
        final RemoveState removeState = removeViewModel.getState();
        removeState.setErrorMessage("");
        removeState.setCurrentCollectionName(removeOutputData.getCollectionName());
        removeState.setAvailableCollections(removeOutputData.getAvailableCollections());
        removeViewModel.setState(removeState);
        removeViewModel.firePropertyChanged("remove success");
    }

    /**
     * Prepares the failure view for the remove use case.
     *
     * @param errorMessage the error message to display
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final RemoveState removeState = removeViewModel.getState();
        removeState.setErrorMessage(errorMessage);
        removeViewModel.firePropertyChanged("remove error");
    }
}
