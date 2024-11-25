package use_case.filter_list.filter_strategies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Television;

/**
 * Filter strategy for television shows.
 */
public class TelevisionFilterStrategy implements FilterStrategy {

    @Override
    public <T> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria) {
        boolean result = true;
        final Television show = (Television) media;

        final Set<String> showKeywords = splitDescription(show.getDescription());
        // Calls on notEmpty() to exclude empty strings from being considered as keywords.
        // Assumes the filter controller formats each keyword to lowercase.
        if (notEmpty(filterCriteria.get("keywords")) && !showKeywords.containsAll(filterCriteria.get("keywords"))) {
            result = false;
        }

        final Set<String> showGenres = new HashSet<>(toLowercase(show.getGenres()));
        if (notEmpty(filterCriteria.get("genres")) && !showGenres.containsAll(filterCriteria.get("genres"))) {
            result = false;
        }

        final Set<String> showActors = new HashSet<>(toLowercase(show.getCastMembers()));
        if (notEmpty(filterCriteria.get("actors")) && !showActors.containsAll(filterCriteria.get("actors"))) {
            result = false;
        }
        return result;
    }

    /**
     * Converts a list of strings to lowercase.
     * @param list The list to convert.
     * @return The list with all strings converted to lowercase.
     */
    private List<String> toLowercase(List<String> list) {
        return list.stream().map(String::toLowerCase).toList();
    }

    /**
     * Splits the description into a set of lowercase words.
     * @param description The description to split.
     * @return The keywords.
     */
    private Set<String> splitDescription(String description) {
        final List<String> keywordArray = Arrays.stream(description
                        .replaceAll("[\\p{Punct}]", "")
                        .toLowerCase()
                        .split("\\s+"))
                .toList();
        return new HashSet<>(keywordArray);
    }

    /**
     * Checks if a set is not empty, empty string excluded.
     * @param set The set of strings to check
     * @return whether the set is not empty.
     */
    private boolean notEmpty(Set<String> set) {
        return !set.isEmpty() && (set.size() != 1 || !set.contains(""));
    }
}
