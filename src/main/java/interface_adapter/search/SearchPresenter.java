package interface_adapter.search;

import use_case.search.SearchByCriteriaOutputData;
import use_case.search.SearchOutputBoundary;

/**
 * The presenter for the search results.
 */
public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    /**
     * Displays search results in the text area.
     * Updates the SearchViewModel and notifies the view about the results.
     *
     * @param outputData the output data containing the movie details.
     */
    @Override
    public void displaySearchResults(SearchByCriteriaOutputData outputData) {
        searchViewModel.setSearchResults(outputData.getSearchResults());
    }

    /**
     * Prepares the fail view by displaying an error message in the view model.
     * @param outputData the output data containing the error message.
     */
    @Override
    public void prepareFailView(SearchByCriteriaOutputData outputData) {
        searchViewModel.setErrorMessage(outputData.getSearchResults());
    }
}
