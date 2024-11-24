package interface_adapter.list;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the ListView.
 */
public class ListViewModel extends ViewModel<ListState> {
    public ListViewModel() {
        super("list");
        setState(new ListState());
    }
}
