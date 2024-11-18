package use_case.search;

import entity.AbstractMedia;
import use_case.note.DataAccessException;

/**
 * The interactor for executing a search based on input data.
 */
public class SearchInteractor implements SearchInputBoundary {

    private final SearchDataAccessInterface dataAccess;
    private final SearchOutputBoundary outputBoundary;

    /**
     * Constructs a SearchInteractor with the given data access and output boundary.
     *
     * @param dataAccess the data access object for performing search operations
     * @param outputBoundary the output boundary for returning search results
     */
    public SearchInteractor(SearchDataAccessInterface dataAccess, SearchOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SearchInputData inputData) {
        try {
            // Perform the search using the DAO
            final AbstractMedia result = dataAccess.searchByName(inputData.getQuery());

            // Check if the result was found and prepare the appropriate view
            if (result != null) {
                final SearchOutputData outputData = new SearchOutputData(result);
                outputBoundary.prepareSuccessView(outputData);
            }
            else {
                outputBoundary.prepareFailView("No results found for: " + inputData.getQuery());
            }
        }
        catch (DataAccessException e) {
            // If there was an issue with data access, send a failure message to the output
            outputBoundary.prepareFailView("An error occurred during the search: " + e.getMessage());
        }
    }
}
