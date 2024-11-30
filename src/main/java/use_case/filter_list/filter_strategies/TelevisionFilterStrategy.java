package use_case.filter_list.filter_strategies;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.AbstractMedia;
import entity.Television;

/**
 * Filter strategy for television shows.
 */
public class TelevisionFilterStrategy implements FilterStrategy {

    private final FormattingHelpers formattingHelper = new FormattingHelpers();

    @Override
    public <T extends AbstractMedia> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria) {
        boolean result = true;
        final Television show = (Television) media;

        // Splits the description into a set of keywords.
        final Set<String> showKeywords =
                formattingHelper.splitString(show.getDescription());
        // Calls on notEmpty() to exclude empty strings from being considered as keywords,
        // then checks if the movie's keywords contain all the filter criteria keywords.
        if (formattingHelper.notEmpty(filterCriteria.get("keywords"))
                && !showKeywords.containsAll(filterCriteria.get("keywords"))) {
            result = false;
        }

        final Set<String> showGenres = new HashSet<>(
                formattingHelper.listToLowercase(show.getGenres()));
        if (formattingHelper.notEmpty(filterCriteria.get("genres"))
                && !showGenres.containsAll(filterCriteria.get("genres"))) {
            result = false;
        }

        final Set<String> showActors = new HashSet<>();
        // Splits the actors' full names into a set of keywords.
        for (String actor : show.getCastMembers()) {
            showActors.addAll(formattingHelper.splitString(actor));
        }
        if (formattingHelper.notEmpty(filterCriteria.get("actors"))
                && !showActors.containsAll(filterCriteria.get("actors"))) {
            result = false;
        }
        return result;
    }
}
