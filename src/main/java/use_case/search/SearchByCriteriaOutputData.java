package use_case.search;

import entity.Movie;

import java.util.List;

/**
 * Output data class for search results.
 */
public class SearchByCriteriaOutputData {
    private List<Movie> movies;

    public SearchByCriteriaOutputData(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
