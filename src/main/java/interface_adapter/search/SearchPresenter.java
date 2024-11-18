package interface_adapter.search;

import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * The Presenter for the Search Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;

    /**
     * Constructs a SearchPresenter with the given ViewModel.
     *
     * @param searchViewModel the ViewModel for the SearchView
     */
    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData outputData) {
        // Update the ViewModel with search results
        searchViewModel.setSearchResults(formatMedia(outputData));
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the ViewModel with the error message
        searchViewModel.setErrorMessage(errorMessage);
    }

    /**
     * Formats the search result data for display.
     *
     * @param outputData the output data containing the media information
     * @return a formatted string representing the search results
     */
    private String formatMedia(SearchOutputData outputData) {
        final StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Title: ").append(outputData.getMedia().getName()).append("\n");
        resultBuilder.append("Genres: ").append(String.join(", ", outputData.getMedia().getGenres())).append("\n");
        resultBuilder.append("Rating: ").append(outputData.getMedia().getExternalRating().getScore()).append("\n");
        return resultBuilder.toString();
    }
}
