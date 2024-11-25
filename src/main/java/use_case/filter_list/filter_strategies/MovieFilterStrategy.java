package use_case.filter_list.filter_strategies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Movie;

/**
 * Filter strategy for movies.
 */
public class MovieFilterStrategy implements FilterStrategy {

    // Assumes that the filter criteria are formatted as lowercase.
    @Override
    public <T> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria) {
        boolean result = true;
        final Movie movie = (Movie) media;

        final Set<String> movieKeywords = splitDescription(movie.getDescription());
        // Calls on notEmpty() to exclude empty strings from being considered as keywords.
        if (notEmpty(filterCriteria.get("keywords")) && !movieKeywords.containsAll(filterCriteria.get("keywords"))) {
            result = false;
        }

        final Set<String> movieGenres = new HashSet<>(toLowercase(movie.getGenres()));
        if (notEmpty(filterCriteria.get("genres")) && !movieGenres.containsAll(filterCriteria.get("genres"))) {
            result = false;
        }

        final Set<String> movieActors = new HashSet<>(toLowercase(movie.getCastMembers()));
        if (notEmpty(filterCriteria.get("actors")) && !movieActors.containsAll(filterCriteria.get("actors"))) {
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
                .replaceAll("\\p{Punct}", "")
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
