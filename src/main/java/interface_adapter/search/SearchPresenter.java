package interface_adapter.search;

import javax.swing.JTextArea;

import data_access.movies.MovieDBDataAccessException;
import use_case.search.SearchByCriteriaOutputData;
import use_case.search.SearchOutputBoundary;

public class SearchPresenter implements SearchOutputBoundary {
    private final JTextArea resultTextArea;
    private final SearchViewModel searchViewModel;

    public SearchPresenter(JTextArea resultTextArea, SearchViewModel searchViewModel) {
        this.resultTextArea = resultTextArea;
        this.searchViewModel = searchViewModel;
    }

    /**
     * Displays search results in the text area.
     * Updates the SearchViewModel and notifies the view about the results.
     *
     * @param outputData the output data containing the movie details.
     * @throws MovieDBDataAccessException if an error occurs during search result handling.
     */
    @Override
    public void displaySearchResults(SearchByCriteriaOutputData outputData) throws MovieDBDataAccessException {
        // Clear previous results
        resultTextArea.setText("");

        // Display the movie result
        final String movieDetails = outputData.getMovie();
        if (movieDetails == null || movieDetails.isEmpty()) {
            resultTextArea.setText("No results found.");
            searchViewModel.setSearchResults("No results found.");
        }
        else {
            resultTextArea.setText(movieDetails);
            searchViewModel.setSearchResults(movieDetails);
        }
        searchViewModel.firePropertyChanged("searchResults");
    }
}
