package use_case.search;

/**
 * Output data class for search results.
 */
public class SearchByCriteriaOutputData {
    private String movie;

    public SearchByCriteriaOutputData(String movie) {
        this.movie = movie;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
