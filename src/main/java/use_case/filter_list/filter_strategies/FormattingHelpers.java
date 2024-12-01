package use_case.filter_list.filter_strategies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper methods for formatting strings.
 */
public class FormattingHelpers {

    /**
     * Converts a list of strings to lowercase.
     *
     * @param list The list to convert.
     * @return The list with all strings converted to lowercase.
     */
    List<String> toLowercase(List<String> list) {
        return list.stream().map(String::toLowerCase).toList();
    }

    /**
     * Splits the description into a set of lowercase words.
     *
     * @param description The description to split.
     * @return The keywords.
     */
    Set<String> splitDescription(String description) {
        final List<String> keywordArray = Arrays.stream(description
                        .replaceAll("\\p{Punct}", "")
                        .toLowerCase()
                        .split("\\s+"))
                .toList();
        return new HashSet<>(keywordArray);
    }

    /**
     * Checks if a set is not empty, empty string excluded.
     *
     * @param set The set of strings to check
     * @return whether the set is not empty.
     */
    boolean notEmpty(Set<String> set) {
        return !set.isEmpty() && (set.size() != 1 || !set.contains(""));
    }
}
