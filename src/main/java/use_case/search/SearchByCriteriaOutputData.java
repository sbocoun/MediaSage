package use_case.search;

/**
 * Output data class for search results.
 */
public class SearchByCriteriaOutputData {
    private String searchResults;

    public SearchByCriteriaOutputData(String searchResults) {
        this.searchResults = searchResults;
    }

    public String getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(String searchResults) {
        this.searchResults = searchResults;
    }
}
