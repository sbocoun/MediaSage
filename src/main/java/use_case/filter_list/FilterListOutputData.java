package use_case.filter_list;

import java.util.List;

/**
 * Output data for the filter list use case.
 */
public class FilterListOutputData {
    private final List<String> filteredMediaNames;

    public FilterListOutputData(List<String> filteredMediaNames) {
        this.filteredMediaNames = filteredMediaNames;
    }

    public List<String> getFilteredMediaNames() {
        return filteredMediaNames;
    }

}
