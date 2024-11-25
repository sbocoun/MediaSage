package interface_adapter.filter_list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        final Map<String, Set<String>> listStyleCriteria = reformatFilterCriteria(filterCriteria);
        filterListInputData.setFilters(listStyleCriteria);
        filterListInputData.setCollectionType(collectionType);
        filterListInputData.setCollectionName(collectionName);
        filterInteractor.execute(filterListInputData);
    }

    private Map<String, Set<String>> reformatFilterCriteria(Map<String, String> filterCriteria) {
        final Map<String, Set<String>> result = new HashMap<>();
        for (Map.Entry<String, String> entry : filterCriteria.entrySet()) {
            final Set<String> criteriaSet = criteriaStringToSet(entry.getValue());
            result.put(entry.getKey(), criteriaSet);
        }
        return result;
    }

    /**
     * Converts a string of criteria to a set of criteria. Removes punctuation
     * (excluding hyphens) and converts to lowercase.
     * @param criteria The comma-separated string of criteria.
     * @return The set of criteria.
     */
    private Set<String> criteriaStringToSet(String criteria) {
        return Arrays.stream(criteria
                        .replaceAll("[\\p{Punct}&&[^-]]", " ")
                        .toLowerCase()
                        .split("\\s+"))
                        .collect(Collectors.toSet());
    }
}
