package use_case.search;

import java.util.List;

/**
 * Input data class for searching by criteria.
 */
public class SearchByCriteriaInputData {
    private final String category;
    // i.e. Movie, TV Show
    private final List<String> keywords;
    private final List<String> genres;
    private List<String> cast;

    public SearchByCriteriaInputData(String category, List<String> keywords, List<String> genres, List<String> cast) {
        this.category = category;
        this.keywords = keywords;
        this.genres = genres;
        this.cast = cast;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }
}
