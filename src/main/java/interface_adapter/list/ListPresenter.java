package interface_adapter.list;

import use_case.list.ListOutputBoundary;
import use_case.list.ListOutputData;

/**
 * The presenter for displaying the media collection list.
 */
public class ListPresenter implements ListOutputBoundary {
    private final ListViewModel listViewModel;

    public ListPresenter(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
    }

    /**
     * Prepares the success view for the media collection display related Use Cases.
     *
     * @param listOutputData output data containing the data used to update the display
     */
    @Override
    public void prepareSuccessView(ListOutputData listOutputData) {
        final ListTableModelFactory listTableModelFactory = new ListTableModelFactory();
        final ListTableModel tableModel = listTableModelFactory
                .createListTableModel(listOutputData.getMediaType(), listOutputData.getCollectionData());
        final ListState listState = listViewModel.getState();
        listState.setErrorMessage("");
        listState.setCurrentCollectionName(listOutputData.getCollectionName());
        listState.setAvailableCollections(listOutputData.getAvailableCollections());
        listState.setTableModel(tableModel);
        listViewModel.setState(listState);
        listViewModel.firePropertyChanged("display data");
    }

    /**
     * Prepares the failure view for the media collection display related Use Cases.
     *
     * @param listOutputData output data containing error message and a list of available collections to display
     */
    @Override
    public void prepareFailView(ListOutputData listOutputData) {
        final ListState listState = listViewModel.getState();
        listState.setErrorMessage(listOutputData.getErrorMessage());
        listState.setAvailableCollections(listOutputData.getAvailableCollections());
        listViewModel.firePropertyChanged("error");
    }

    /**
     * Prepares the failure view for the media collection display related Use Cases.
     */
    @Override
    public void prepareLogoutView() {
        listViewModel.firePropertyChanged("logout");
    }
}
