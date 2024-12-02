package use_case.filter_list.filter_strategies;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.AbstractMedia;
import entity.Movie;

/**
 * Filter strategy for movies.
 */
public class MovieFilterStrategy implements FilterStrategy {

    private final FormattingHelpers formattingHelper = new FormattingHelpers();

    // Assumes that the filter criteria are formatted as lowercase.
    @Override
    public <T extends AbstractMedia> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria) {
        boolean result = true;
        final Movie movie = (Movie) media;

        // Splits the description into a set of keywords.
        final Set<String> movieKeywords =
                formattingHelper.splitString(movie.getDescription());
        // Calls on notEmpty() to exclude empty strings from being considered as keywords,
        // then checks if the movie's keywords contain all the filter criteria keywords.
        if (formattingHelper.notEmpty(filterCriteria.get("keywords"))
                && !movieKeywords.containsAll(filterCriteria.get("keywords"))) {
            result = false;
        }

        final Set<String> movieGenres = new HashSet<>(
                formattingHelper.listToLowercase(movie.getGenres()));
        if (formattingHelper.notEmpty(filterCriteria.get("genres"))
                && !movieGenres.containsAll(filterCriteria.get("genres"))) {
            result = false;
        }

        final Set<String> movieActors = new HashSet<>();
        // Splits the actors' full names into a set of keywords.
        for (String actor : movie.getCastMembers()) {
            movieActors.addAll(formattingHelper.splitString(actor));
        }
        if (formattingHelper.notEmpty(filterCriteria.get("actors"))
                && !movieActors.containsAll(filterCriteria.get("actors"))) {
            result = false;
        }
        return result;
    }
}
