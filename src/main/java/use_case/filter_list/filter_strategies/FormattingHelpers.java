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
     * Converts the contents of a list of strings to lowercase.
     *
     * @param list The list to convert.
     * @return The list with all strings converted to lowercase.
     */
    List<String> listToLowercase(List<String> list) {
        return list.stream().map(String::toLowerCase).toList();
    }

    /**
     * Splits the input string into a set of lowercase words.
     *
     * @param input The description to split.
     * @return The keywords.
     */
    Set<String> splitString(String input) {
        final List<String> result = Arrays.stream(input
                        .replaceAll("\\p{Punct}", "")
                        .toLowerCase()
                        .split("\\s+"))
                .toList();
        return new HashSet<>(result);
    }

    /**
     * Checks if a set is not empty by checking if it contains an empty string.
     *
     * @param set The set of strings to check
     * @return whether the set is not empty.
     */
    boolean notEmpty(Set<String> set) {
        return !set.contains("");
    }
}
