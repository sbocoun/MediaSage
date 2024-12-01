package interface_adapter.filter_list;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the FilterView.
 */
public class FilterViewModel extends ViewModel<FilterState> {
    public FilterViewModel() {
        super("filter");
        setState(new FilterState());
    }
}