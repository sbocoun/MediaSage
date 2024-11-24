package use_case.filter_list.filter_strategies;

import entity.Television;

import java.util.List;
import java.util.Map;

/**
 * Interface for the filter strategies.
 */
public interface FilterStrategy {

    /**
     * Checks if the media meets the filter criteria.
     * @param media The media to check.
     * @param filterCriteria The criteria to check against.
     * @param <T> The type of media to filter.
     * @return True if the media meets the criteria, false otherwise.
     */
    <T> boolean meetsCriteria(T media, Map<String, List<String>> filterCriteria);

}
