package use_case.filter_list;

import java.util.List;
import java.util.Map;

/**
 * Input data for the "filter list" use case.
 */
public class FilterListInputData {

    private Map<String, List<String>> filters;
    private String collectionType;
    private String collectionName;

    public void setFilters(Map<String, List<String>> filterCriteria) {
        this.filters = filterCriteria;
    }

    public Map<String, List<String>> getFilters() {
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
