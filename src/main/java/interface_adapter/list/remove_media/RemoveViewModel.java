package interface_adapter.list.remove_media;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the RemoveView.
 */
public class RemoveViewModel extends ViewModel<RemoveState> {
    public RemoveViewModel() {
        super("remove");
        setState(new RemoveState());
    }
}
