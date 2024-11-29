package use_case.filter_list.filter_strategies;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.Movie;

/**
 * Filter strategy for movies.
 */
public class MovieFilterStrategy implements FilterStrategy {

    private final FormattingHelpers formattingHelper = new FormattingHelpers();

    // Assumes that the filter criteria are formatted as lowercase.
    @Override
    public <T> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria) {
        boolean result = true;
        final Movie movie = (Movie) media;

        final Set<String> movieKeywords =
                formattingHelper.splitDescription(movie.getDescription());
        // Calls on notEmpty() to exclude empty strings from being considered as keywords.
        if (formattingHelper.notEmpty(filterCriteria.get("keywords"))
                && !movieKeywords.containsAll(filterCriteria.get("keywords"))) {
            result = false;
        }

        final Set<String> movieGenres = new HashSet<>(
                formattingHelper.toLowercase(movie.getGenres()));
        if (formattingHelper.notEmpty(filterCriteria.get("genres"))
                && !movieGenres.containsAll(filterCriteria.get("genres"))) {
            result = false;
        }

        final Set<String> movieActors = new HashSet<>(
                formattingHelper.toLowercase(movie.getCastMembers()));
        if (formattingHelper.notEmpty(filterCriteria.get("actors"))
                && !movieActors.containsAll(filterCriteria.get("actors"))) {
            result = false;
        }
        return result;
    }
}
