package use_case.filter_list;

import java.util.Map;
import java.util.Set;

/**
 * Input data for the "filter list" use case.
 */
public class FilterListInputData {

    private Map<String, Set<String>> filters;
    private String collectionType;
    private String collectionName;

    public void setFilters(Map<String, Set<String>> filterCriteria) {
        this.filters = filterCriteria;
    }

    public Map<String, Set<String>> getFilters() {
        return filters;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }
}
