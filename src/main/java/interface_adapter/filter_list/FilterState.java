package interface_adapter.filter_list;

import java.util.HashMap;
import java.util.Map;

/**
 * The ViewModel for the FilterView.
 */
public class FilterState {

    private final Map<String, String> filterCriteria = new HashMap<>();

    /**
     * Adds a filter to the filter map.
     *
     * @param criteriaName The name of the criteria to filter by.
     * @param criteriaValue The value(s) to filter by.
     */
    public void setFilterCriteria(String criteriaName, String criteriaValue) {
        filterCriteria.put(criteriaName, criteriaValue);
    }

    public Map<String, String> getFilterCriteria() {
        return new HashMap<>(filterCriteria);
    }

    /**
     * Resets the filter criteria.
     */
    public void resetFilterCriteria() {
        filterCriteria.clear();
    }
}
