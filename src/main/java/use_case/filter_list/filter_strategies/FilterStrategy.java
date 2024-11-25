package use_case.filter_list.filter_strategies;

import java.util.Map;
import java.util.Set;

/**
 * Interface for the filter strategies.
 */
public interface FilterStrategy {

    /**
     * Checks if the media meets the filter criteria.
     * @param media The media to check.
     * @param filterCriteria The criteria to check against.
     *                       Assumes the value sets contain lowercase strings.
     * @param <T> The type of media to filter.
     * @throws IllegalArgumentException if the media is not an instance of the expected type.
     * @return True if the media meets the criteria, false otherwise.
     */
    <T> boolean meetsCriteria(T media, Map<String, Set<String>> filterCriteria);

}
