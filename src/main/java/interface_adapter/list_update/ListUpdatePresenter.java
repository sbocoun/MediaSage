package interface_adapter.list_update;

import interface_adapter.list.ListState;
import interface_adapter.list.ListViewModel;
import use_case.list_update.ListUpdateOutputBoundary;
import use_case.list_update.ListUpdateOutputData;

/**
 * The presenter for the status of the media collection update.
 */
public class ListUpdatePresenter implements ListUpdateOutputBoundary {
    private final ListViewModel listViewModel;

    public ListUpdatePresenter(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
    }

    /**
     * Prepares the success view for the media collection update related Use Cases.
     *
     * @param listOutputData output data containing the success message
     */
    @Override
    public void prepareSuccessView(ListUpdateOutputData listOutputData) {
        final ListState listState = listViewModel.getState();
        listState.setSuccessMessage(listOutputData.getSuccessMessage());
        listViewModel.setState(listState);
        listViewModel.firePropertyChanged("update successful");
    }

    /**
     * Prepares the failure view for the media collection update related Use Cases.
     *
     * @param listOutputData output data containing the error message
     */
    @Override
    public void prepareFailView(ListUpdateOutputData listOutputData) {
        final ListState listState = listViewModel.getState();
        listState.setErrorMessage(listOutputData.getErrorMessage());
        listViewModel.setState(listState);
        listViewModel.firePropertyChanged("update failed");
    }
}
