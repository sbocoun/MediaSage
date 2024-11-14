package use_case.search;

/**
 * Input data for the Search Use Case.
 */
public class SearchInputData {

    private final String keyword;
    private final String genre;
    private final String cast;

    public SearchInputData(String keyword, String genre, String cast) {
        this.keyword = keyword;
        this.genre = genre;
        this.cast = cast;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getGenre() {
        return genre;
    }

    public String getCast() {
        return cast;
    }
}
