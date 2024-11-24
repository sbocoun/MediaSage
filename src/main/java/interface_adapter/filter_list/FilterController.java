package interface_adapter.filter_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import use_case.filter_list.FilterInputBoundary;
import use_case.filter_list.FilterListInputData;

/**
 * Controller for the filter panel.
 */
public class FilterController {
    private final FilterInputBoundary filterInteractor;

    public FilterController(FilterInputBoundary filterInteractor) {
        this.filterInteractor = filterInteractor;
    }

    /**
     * Formats the data for the filter related Use Cases and calls on the
     * appropriate interactor.
     * @param filterCriteria The criteria to filter by.
     *                       The key is the name of the criteria to filter by.
     * @param collectionType The type of collection to filter.
     * @param collectionName The name of the collection to filter.
     */
    public void execute(Map<String, String> filterCriteria, String collectionType, String collectionName) {
        final FilterListInputData filterListInputData = new FilterListInputData();
        final Map<String, List<String>> listStyleCriteria = reformatFilterCriteria(filterCriteria);
        filterListInputData.setFilters(listStyleCriteria);
        filterListInputData.setCollectionType(collectionType);
        filterListInputData.setCollectionName(collectionName);
        filterInteractor.execute(filterListInputData);
    }

    private Map<String, List<String>> reformatFilterCriteria(Map<String, String> filterCriteria) {
        final Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String, String> entry : filterCriteria.entrySet()) {
            final List<String> criteriaList = criteriaStringToList(entry.getValue());
            result.put(entry.getKey(), criteriaList);
        }
        return result;
    }

    /**
     * Converts a comma-separated string of criteria to a list of criteria.
     * @param criteria The comma-separated string of criteria.
     * @return The list of criteria.
     */
    private List<String> criteriaStringToList(String criteria) {
        return List.of(criteria.split(","));
    }
}
