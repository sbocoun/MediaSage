package interface_adapter.filter_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

/**
 * The ViewModel for the FilterView.
 */
public class FilterState {

    private final Map<String, String> filterCriteria = new HashMap<>();
    private List<String> filteredMediaNames = new ArrayList<>();
    private String errorMessage = "";

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
     * Clears the filter criteria.
     */
    public void clearFilterCriteria() {
        filterCriteria.clear();
    }

    /**
     * Returns a list of media names meant to be passed to the list panel and
     * displayed.
     * @return The list of media names.
     */
    @Nullable
    public List<String> getFilteredMediaNames() {
        return filteredMediaNames;
    }

    /**
     * Sets the list of media names meant to be passed to the list panel and
     * displayed.
     * @param filteredMediaNames The list of media names, can be null to
     *                           signify no media should be filtered.
     */
    public void setFilteredMediaNames(List<String> filteredMediaNames) {
        if (filteredMediaNames != null) {
            this.filteredMediaNames = new ArrayList<>(filteredMediaNames);
        }
        else {
            this.filteredMediaNames = null;
        }
    }

    /**
     * Sets the error message to be displayed.
     * @param message The error message.
     */
    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    /**
     * Returns the error message to be displayed.
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
