package use_case.filter_list.filter_strategies;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.Television;

/**
 * Filter strategy for television shows.
 */
public class TelevisionFilterStrategy implements FilterStrategy {

    private final FormattingHelpers formattingHelper = new FormattingHelpers();

    @Override
    public <T> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria) {
        boolean result = true;
        final Television show = (Television) media;

        final Set<String> showKeywords =
                formattingHelper.splitDescription(show.getDescription());
        // Calls on notEmpty() to exclude empty strings from being considered as keywords.
        // Assumes the filter controller formats each keyword to lowercase.
        if (formattingHelper.notEmpty(filterCriteria.get("keywords"))
                && !showKeywords.containsAll(filterCriteria.get("keywords"))) {
            result = false;
        }

        final Set<String> showGenres = new HashSet<>(
                formattingHelper.toLowercase(show.getGenres()));
        if (formattingHelper.notEmpty(filterCriteria.get("genres"))
                && !showGenres.containsAll(filterCriteria.get("genres"))) {
            result = false;
        }

        final Set<String> showActors = new HashSet<>(
                formattingHelper.toLowercase(show.getCastMembers()));
        if (formattingHelper.notEmpty(filterCriteria.get("actors"))
                && !showActors.containsAll(filterCriteria.get("actors"))) {
            result = false;
        }
        return result;
    }
}
