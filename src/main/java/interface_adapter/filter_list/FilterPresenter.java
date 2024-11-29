package interface_adapter.filter_list;

import use_case.filter_list.FilterListOutputBoundary;
import use_case.filter_list.FilterListOutputData;

/**
 * Presenter for the filter list.
 */
public class FilterPresenter implements FilterListOutputBoundary {
    private final FilterViewModel filterViewModel;

    public FilterPresenter(FilterViewModel filterViewModel) {
        this.filterViewModel = filterViewModel;
    }

    /**
     * Prepares the success view.
     *
     * @param filterListOutputData The output data.
     */
    public void prepareSuccessView(FilterListOutputData filterListOutputData) {
        filterViewModel.getState().setFilteredMediaNames(filterListOutputData.getFilteredMediaNames());
        filterViewModel.firePropertyChanged("filtered media");
    }

    /**
     * Prepares the fail view.
     *
     * @param message The message to display.
     */
    public void prepareFailView(String message) {
        filterViewModel.getState().setErrorMessage(message);
        filterViewModel.firePropertyChanged("error");
    }
}
