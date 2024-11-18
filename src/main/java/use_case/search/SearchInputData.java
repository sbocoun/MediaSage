package use_case.search;

/**
 * Contains the input data required for executing a search.
 */
public class SearchInputData {
    private final String query;

    public SearchInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}